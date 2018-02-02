package cn.jeas.bos.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.system.Permission;
import cn.jeas.bos.serivce.system.PermissionService;

@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission>{

	private static final long serialVersionUID = 1L;

	@Autowired
	private PermissionService permissionService;
	
	@Action(value="permission_add",results={
			@Result(type=REDIRECT,location="/pages/system/permission.html")
	})
	public String add(){
		
		permissionService.addPermission(model);
		return SUCCESS;
		
	}
	
	
	@Action("permission_list")
	public String list(){
		List<Permission> permissionList= permissionService.findPermissionList();
		ActionContext.getContext().getValueStack().push(permissionList);
		return JSON;
	}
}
