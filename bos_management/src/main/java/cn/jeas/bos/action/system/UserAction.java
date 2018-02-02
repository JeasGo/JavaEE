package cn.jeas.bos.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.dao.system.UserService;
import cn.jeas.bos.domain.system.User;

@Scope("prototype")
@Controller
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = 6393387397221021519L;
	
	@Autowired
	private UserService userService;
	//属性注入
	private Integer[] roleIds;
	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
	
	
	@Action("user_list")
	public String list(){
		List<User> userList= userService.findUserList();
		ActionContext.getContext().getValueStack().push(userList);
		return JSON;
	}
	
	@Action(value="user_add",results={
			@Result(type=REDIRECT,location="/pages/system/userlist.html")
	})
	public String add(){
		userService.saveUser(model,roleIds);
		return SUCCESS;
	}


	//注销
	@Action(value="user_logout",results={
			@Result(type=REDIRECT,location="/login.html")
	})
	public String logout(){
		//shiro
		SecurityUtils.getSubject().logout();
		return SUCCESS;
	}

	
	
	@Action(value="user_login",results={
			@Result(type=REDIRECT,location="/index.html"),//主页
			@Result(name=INPUT,type=REDIRECT,location="/login.html")//输入表单
	})
	public String login(){
		//之前写法！！！！
//		userService.login(model);
		//今天shiro的登录方式
		
		//获取subject
		Subject subject = SecurityUtils.getSubject();
		
		//创建用户名密码令牌
		UsernamePasswordToken token=new UsernamePasswordToken(model.getUsername(), model.getPassword());
		
		try {
			//认证（登录）操作,参数是令牌对象
			subject.login(token);
			//登录成功
			return SUCCESS;
		} 
		catch (UnknownAccountException e) {
			e.printStackTrace();
			System.err.println("=====================用户名错误");
			//登录失败,用户名不存在
			return INPUT;
		}catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			//登录失败,密码不正确
			System.err.println("=====================密码错误");
			return INPUT;
		}catch (AuthenticationException e) {
			e.printStackTrace();
			//登录失败,程序代码问题
			System.err.println("=====================登录错误");
			return INPUT;
		}
		
		
	}

	
	
}
