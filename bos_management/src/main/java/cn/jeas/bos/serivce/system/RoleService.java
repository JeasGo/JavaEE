package cn.jeas.bos.serivce.system;

import java.util.List;

import cn.jeas.bos.domain.system.Role;

public interface RoleService {

	/**
	 * 
	 * 功能:查询所有角色功能
	 * Author:jeas
	 * @return
	 * Time:2018年1月31日 下午9:49:40
	 */
	List<Role> findAllRole();

	/**
	 * 
	 * 功能:保存角色并关联权限和菜单
	 * Author:jeas
	 * @param model
	 * @param permissionIds
	 * @param menuIds
	 * Time:2018年2月1日 下午8:27:43
	 */
	void saveRole(Role model, Integer[] permissionIds, String menuIds);

}
