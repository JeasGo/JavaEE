package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

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

import cn.itcast.bos.utils.Constants;
import cn.itcast.bos.utils.MailUtils;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;

//前台客户的action
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer>{
	
	//注入JmsTemplate
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsQueueTemplate;
	
	//发送验证码
	@Action("customer_sendCheckcode")
	public String sendCheckcode(){
		//生成四位随机数字
		final String checkcode = RandomStringUtils.randomNumeric(4);
		
		//将验证码放入session
		ServletActionContext.getRequest().getSession().setAttribute
		(model.getMobilePhone(), checkcode);
		
		//调用阿里大鱼发送验证码的短信
//		SmsUtils.sendCheckCode(checkcode, model.getMobilePhone());
//		System.out.println("手机号是："+model.getMobilePhone()+"的验证码："+checkcode);
		//调用MQ，生产短信消息，发送给mq
		jmsQueueTemplate.send("bos.sms.normal.checkcode", new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//构造map消息
				MapMessage mapMessage = session.createMapMessage();
				//手机号码
				mapMessage.setString("mobilePhone", model.getMobilePhone());
				//消息内容验证码
				mapMessage.setString("checkcode", checkcode);
				
				return mapMessage;
			}
		});
		

		
		return NONE;
	}
	
	//属性驱动获取验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	//注入redistemplate
	@Autowired
	private RedisTemplate redistemplate;

	//客户注册
	@Action(value="customer_regist",results={
			@Result(type=REDIRECT,location="/signup-success.html")
			,@Result(name=INPUT,type=REDIRECT,location="/signup.html")
	})
	public String regist(){
		//第一步：验证码校验
		//获取session中验证码
		String checkcodeSession = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getMobilePhone());
		//清除session验证码
		ServletActionContext.getRequest().getSession().removeAttribute(model.getMobilePhone());
		//获取表单的验证码,属性驱动
		//对比
		if(StringUtils.isBlank(checkcode)||!checkcodeSession.equals(checkcode)){
			//验证码校验失败
			return INPUT;
		}
		//第二步：用户数据保存（远程）
//		WebClient.create("http://localhost:9002/crm_management/services/customerservice/customers")
		WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
			.type(MediaType.APPLICATION_JSON)
			.post(model);
		
		
		//第三步：发送邮件并将邮件激活码存入redis
		
		String subject="速运快递的注册的激活邮件";//主题
//		String activeUrl = "http://localhost:9003/bos_fore/customer_activeMail";//激活地址(servlet)
		String activeUrl = Constants.BOS_FORE_URL+"/customer_activeMail";//激活地址(servlet)
		String activecode=RandomStringUtils.randomNumeric(32);//邮件验证码
		String content="<h3>请点击地址激活:<a href=" + activeUrl
				+ "?activecode=" + activecode +"&mobilePhone="+model.getMobilePhone()+ ">" + activeUrl
				+ "</a></h3>";
		String to=model.getEmail();//邮件地址
		//发送邮件
		MailUtils.sendMail(subject, content, to);
		
		//将验证码存放到redis中
		redistemplate.opsForValue().set(model.getMobilePhone(), activecode,24, TimeUnit.HOURS);
		
		
		
		return SUCCESS;
	}
	
	//属性驱动封装邮件激活码
	private String activecode;
	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}

	//激活用户
	@Action("customer_activeMail")
	public String activeMail() throws Exception{
		//判断
		if(StringUtils.isNotBlank(activecode)&&StringUtils.isNotBlank(model.getMobilePhone())){
			//可以激活
			
			//redis激活码
			String activecodeRedis = (String) redistemplate.opsForValue().get(model.getMobilePhone());
			
			
			
			//redis中存放激活码和传过来的激活码一样，则允许激活
			if(StringUtils.isNotBlank(activecodeRedis)&&activecode.equals(activecodeRedis)){
				//远程调用crm系统，更新type字段
//				WebClient.create("http://localhost:9002/crm_management/services/customerservice/customers")
				WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
				.path("/type")
				.path("/"+model.getMobilePhone())
				.path("/"+1)
				.type(MediaType.APPLICATION_JSON)
				.put(null);
				System.out.println("用户激活成功！"+model.getMobilePhone());
				
				//清除redis激活码
				redistemplate.delete(model.getMobilePhone());
				
				//响应页面
				ServletActionContext.getResponse().setContentType("text/html; charset=UTF-8");
				ServletActionContext.getResponse().getWriter().print("恭喜激活成功！请从主页登录");
			}else{
				System.out.println("用户激活失败！1"+model.getMobilePhone());
				//响应页面
				ServletActionContext.getResponse().setContentType("text/html; charset=UTF-8");
				ServletActionContext.getResponse().getWriter().print("激活失败！请重新发送激活邮件");
				//1）缺少重新发送邮件页面
				//2)删除注册失败的用户，重新注册
			}
			
			
		}else{
			//激活失败
			System.out.println("用户激活失败！2"+model.getMobilePhone());
			//响应页面
			ServletActionContext.getResponse().setContentType("text/html; charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print("激活失败！请重新注册");
		}
		
		return NONE;
	}
	
}
