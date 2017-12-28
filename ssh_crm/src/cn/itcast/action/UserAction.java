package cn.itcast.action;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.domain.User;
import cn.itcast.service.CustomerService;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

/** 
 * @author 贾兵兵 
 * @date 2017年12月19日
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{

	private static final long serialVersionUID = -866231108963657606L;

	private User user = new User();
	
	//private UserService userService = new UserServiceImpl();
	
	public String login() throws Exception {
		//获得servletContext对象类
		ServletContext sc = ServletActionContext.getServletContext();
		//从ServletContext中获取ac容器
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		UserService userService = (UserService) ac.getBean("userService");
				
//	===========================================
		
		System.out.println(user);
		//调用Service执行登录操作
		User loginUser = userService.login(user);
		//将返回的User对象存入session域作为登录标识
		ActionContext.getContext().getSession().put("loginUser", loginUser);
		//重定向到项目的首页
		return "toHome";
	}
	
	
	@Override
	public User getModel() {
		return user;
	}

	
	
}
