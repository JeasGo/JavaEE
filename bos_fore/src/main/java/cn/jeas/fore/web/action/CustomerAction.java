package cn.jeas.fore.web.action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.utils.Constants;
import cn.jeas.crm.domain.Customer;


@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {

	private static final long serialVersionUID = 815772801115457376L;
//  注入Service
//	@Autowired
//	private CustomerService customerService;
	
	//注入redisTemplate  redis数据库缓存
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	//注入jmsTemplate
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	
	//属性驱动封装邮件随机激活码
	private String activecode;
	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}
	//邮箱激活
	@Action("customer_activeMail")
	public String activeMail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		String activecodeRedis = redisTemplate.opsForValue().get(model.getTelephone());
		if (StringUtils.isBlank(activecodeRedis)||!activecodeRedis.equals(activecode)) {
			try {
				response.getWriter().println("对不起,邮箱连接已失效");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return NONE;
		}
		WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/type")
		.path("/"+model.getTelephone())//需要判断
		.path("/1")//1代表激活
		.type(MediaType.APPLICATION_JSON)
		.put(null);
		try {
			response.getWriter().println("恭喜，邮件激活成功，请返回主页");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//最后清除redis中的邮件激活码
		redisTemplate.delete(model.getTelephone());
		
		return NONE;

	}

	//客户注册:客户邮箱注册  客户手机验证
	@Action(value="customer_regist",results={
			@Result(type = REDIRECT,location="/signup-success.html"),
			@Result(name = INPUT,type=REDIRECT,location="/signup.html")
	})
	public String regist(){
		System.err.println("======================客户信息保存=========================");
		String checkcodeSession = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
		ServletActionContext.getRequest().getSession().removeAttribute(model.getTelephone());
		if (StringUtils.isBlank(checkcode)||!checkcode.equals(checkcodeSession)) {
			return INPUT;
		}
		Response post = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers").type(MediaType.APPLICATION_JSON).post(model);
		System.out.println("================================================"+post);
		
		final String subject = "JEAS速运快递的注册激活邮件";
		String activeUrl = Constants.BOS_FORE_URL+"/customer_activeMail.action";
		
		String activeCode= RandomStringUtils.randomNumeric(32);
		//拼接邮件地址
		
		String urlAddress = activeUrl+"?activecode="+activeCode+"&telephone="+model.getTelephone();
		final String content ="尊敬的<h5>"+model.getTelephone()+"</h5>用户,欢迎注册速运物流会员服务,请点击以下地址激活您的邮箱:<a href="+urlAddress+">"+urlAddress+"<br>24小时以内有效,请尽快注册!!!";
		
		final String to =model.getEmail();
		
		jmsTemplate.send("bos.email.normal.subject",new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("subject", subject);
				message.setString("content", content);
				message.setString("to", to);
				return message;
			}
		});
		
		//MailUtils.sendMail(subject, content, to);
		redisTemplate.opsForValue().set(model.getTelephone(),activeCode,24,TimeUnit.HOURS);
		return SUCCESS;
	}
	
	@Action("customer_sendCheckcode")
	public String sendCheckcode(){
		
		//生成验证码
		final String checkcode = RandomStringUtils.randomNumeric(6);
		
		System.err.println("====================================================================");
		System.err.println(checkcode);
		System.err.println("=====================================================================");
		String telephone = model.getTelephone();
		
		//SMSUtils.sendMsg(telephone, checkcode);
		
		jmsTemplate.send("bos.sms.normal.checkcode",new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("checkcode", checkcode);
				message.setString("telephone", model.getTelephone());
				return message;
			}
		});
		
		ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(),checkcode);
		
		return NONE;
		
	}
}
