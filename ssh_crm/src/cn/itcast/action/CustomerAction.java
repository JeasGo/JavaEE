package cn.itcast.action;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


import cn.itcast.domain.Customer;
import cn.itcast.service.CustomerService;
import cn.itcast.service.impl.CustomerServiceImpl;

/** 
 * @author 贾兵兵 
 * @date 2017年12月17日
 */
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	
	private static final long serialVersionUID = 5498306645165120231L;
	/**
	 * 私有化service层 
	 */
	//private CustomerService customerService = new CustomerServiceImpl();
	/**
	 * 模型对象
	 */
	private Customer customer = new Customer();
	

	public String list() {
		//获得servletContext对象类
		ServletContext sc = ServletActionContext.getServletContext();
		//从ServletContext中获取ac容器
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		 CustomerService customerService = (CustomerService) ac.getBean("customerService");
		
//		===========================================
		//接受参数
		String cust_name = ServletActionContext.getRequest().getParameter("cust_name");
		//创建离线查询对象
		DetachedCriteria dc= DetachedCriteria.forClass(Customer.class);
		//判断参数拼接条件
		if (StringUtils.isNotBlank(cust_name)) {
			DetachedCriteria criteria = dc.add(Restrictions.like("cust_name","%"+cust_name+"%"));
			System.out.println(criteria);
		}
		//调用service将离线对象传递
		List<Customer>  customers = customerService.findAllCustomer(dc);
		//将返回的list放入request域.转发到list.jsp
		//Map<String,Object> request = (Map<String, Object>) ActionContext.getContext().get("request");
		//request.put("customers", customers);
		//ServletActionContext.getRequest().setAttribute("customers", customers);
		
		
		ActionContext.getContext().put("customers", customers); //第二种方法
		
		return "toList";
	}
	public String add() {
		//获得servletContext对象类
		ServletContext sc = ServletActionContext.getServletContext();
		//从ServletContext中获取ac容器
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		 CustomerService customerService = (CustomerService) ac.getBean("customerService");
		
//		===========================================
		
		customerService.add(customer);
		return "toAdd";
	}
	@Override
	public Customer getModel() {
		return customer;
	}

}
