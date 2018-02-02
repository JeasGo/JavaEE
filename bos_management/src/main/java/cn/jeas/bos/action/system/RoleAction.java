package cn.jeas.bos.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.system.Role;
import cn.jeas.bos.serivce.system.RoleService;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RoleService roleService;
	
	private Integer[] permissionIds;//功能权限
	private String menuIds;//菜单ids
	public void setPermissionIds(Integer[] permissionIds) {
		this.permissionIds = permissionIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	@Action(value="role_add",results={
			@Result(type=REDIRECT,location="/pages/system/role.html")
	})
	public String add(){
		roleService.saveRole(model,permissionIds,menuIds);
		
		return SUCCESS;
	}
	
	@Action("role_list")
	public String list(){
		List<Role> roleList = roleService.findAllRole();
		
		ActionContext.getContext().getValueStack().push(roleList);
		
		return JSON;
	}
}
