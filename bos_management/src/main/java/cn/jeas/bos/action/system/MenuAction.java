package cn.jeas.bos.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.system.Menu;
import cn.jeas.bos.domain.system.User;
import cn.jeas.bos.serivce.system.MenuService;

@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {

	private static final long serialVersionUID = 1L;
	
	//注入service
	@Autowired
	private MenuService menuService;
	
	
	@Action("menu_listByUser")
	public String listByUser(){
		//获取当前登录用户
		User user=(User) SecurityUtils.getSubject().getPrincipal();
		List<Menu> menuList=menuService.findMenuByUser(user);
		ActionContext.getContext().getValueStack().push(menuList);
		return JSON;
	}

	
	
	
	@Action(value="menu_add",results={
			@Result(type=REDIRECT,location="/pages/system/menu.html")
	})
	public String add(){
		menuService.saveMenu(model);
		
		return SUCCESS;
		
	}
	
	
	@Action("menu_list")
	public String list(){
		List<Menu> menuList= menuService.findMenuList();
		ActionContext.getContext().getValueStack().push(menuList);
		return JSON;
	}


}
