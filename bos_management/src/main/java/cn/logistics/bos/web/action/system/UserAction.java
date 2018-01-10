package cn.itcast.bos.web.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;
import cn.itcast.bos.utils.MD5Utils;
import cn.itcast.bos.web.action.common.BaseAction;

//用户相关action
public class UserAction extends BaseAction<User>{
	
	//登录（认证过程）
	/*@Action(value="user_login",results={
			@Result(type=REDIRECT,location="/index.html")
			,@Result(name=INPUT,type=REDIRECT,location="/login.html")
	})
	public String login(){
		//逻辑：操作shiro的subject即可。
		
		//获取subject
		Subject subject = SecurityUtils.getSubject();
		
		//创建用户令牌
		UsernamePasswordToken token=new UsernamePasswordToken
				(model.getUsername(), model.getPassword());
		try {
			//认证（登录）
			//参数：令牌token
			subject.login(token);
//		AuthenticationToken
			//登录成功
			return SUCCESS;
		} catch(UnknownAccountException e){
			e.printStackTrace();
			//登录失败:用户名不存在
			return INPUT;//重回登录
		}catch (LockedAccountException e) {
			e.printStackTrace();
			//登录失败:帐号被锁定
			return INPUT;//重回登录
		}catch(IncorrectCredentialsException e){
			e.printStackTrace();
			//登录失败:密码不正确
			return INPUT;//重回登录
		}catch (AuthenticationException e) {
			e.printStackTrace();
			//登录失败
			return INPUT;//重回登录
		}
		
	}*/
	
	@Action("user_login")
	public String login(){
		//逻辑：操作shiro的subject即可。
		
		//获取subject
		Subject subject = SecurityUtils.getSubject();
		
		//创建用户令牌
		UsernamePasswordToken token=new UsernamePasswordToken
				(model.getUsername(), model.getPassword());
//		UsernamePasswordToken token=new UsernamePasswordToken
//				(model.getUsername(), MD5Utils.md5(model.getPassword()));
		
		//结果map
		Map<String, Object> resultMap=new HashMap<>();
		
		try {
			//认证（登录）
			//参数：令牌token
			subject.login(token);
//		AuthenticationToken
			//登录成功
			resultMap.put("result", true);
			
		} catch(UnknownAccountException e){
			e.printStackTrace();
			//登录失败:用户名不存在
			resultMap.put("result", false);
			resultMap.put("message", "用户名不存在！");
		}catch (LockedAccountException e) {
			e.printStackTrace();
			//登录失败:帐号被锁定
			resultMap.put("result", false);
			resultMap.put("message", "帐号被锁定，请联系管理员，交钱。");
		}catch(IncorrectCredentialsException e){
			e.printStackTrace();
			//登录失败:密码不正确
			resultMap.put("result", false);
			resultMap.put("message", "密码不正确。");
		}catch (AuthenticationException e) {
			e.printStackTrace();
			//登录失败
			resultMap.put("result", false);
			resultMap.put("message", "登录失败，请联系管理员");
		}
		
		ActionContext.getContext().getValueStack().push(resultMap);
		
		return JSON;
		
	}
	
	//注销用户
	@Action(value="user_logout",results={@Result(type=REDIRECT,location="/login.html")})
	public String logout(){
		
		//shiro注销：释放了很多资源
		SecurityUtils.getSubject().logout();
		/*
		 * shiro的底层原理：也将用户对象放入“session”
		 * 该session不是httpsession，而是org.apache.shiro.session.Session
		 * Shiro可以用在任何的环境下，普通的java程序或web都可以
		 * session规则：
		 * 如果web应用，shiro的session会封装httpsession，或理解为两者等同。用户对象确实放入了httpsession
		 * 如果不是web应用，shiro的session就和httpsession没什么关系了。
		 * 但对于开发来说，不用管。
		 */
		
		return SUCCESS;
	}
	
	//注入用户service
	@Autowired
	private UserService userService;
	
	//用户列表
	@Action("user_list")
	public String list(){
		List<User> userList= userService.findUserList();
		ActionContext.getContext().getValueStack().push(userList);
		return JSON;
	}
	
	//注入选中的角色ids
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	//添加用户
	@Action(value="user_add",results={
			@Result(type=REDIRECT,location="/pages/system/userlist.html")
	})
	public String add(){
		userService.saveUser(model,roleIds);
		return SUCCESS;
	}

}
