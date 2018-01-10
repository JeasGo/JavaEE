package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.web.action.common.BaseAction;
//角色权限的action
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	//注入service
	@Autowired
	private RoleService roleService;
	
	//列出所有角色
	@Action("role_list")
	public String list(){
		List<Role> roleList= roleService.findRoleList();
		ActionContext.getContext().getValueStack().push(roleList);
		return JSON;
	}
	
	//属性驱动获取功能权限
	private String[] permissionIds;
	public void setPermissionIds(String[] permissionIds) {
		this.permissionIds = permissionIds;
	}
	private String menuIds;
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}



	//添加角色并关联权限和菜单
	@Action(value="role_add",results={
			@Result(type=REDIRECT,location="/pages/system/role.html")
	})
	public String add(){
		roleService.saveRole(model,permissionIds,menuIds);
		return SUCCESS;
	}
}
