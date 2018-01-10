package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Role;

//角色业务接口
public interface RoleService {

	/**
	 * 
	 * 说明：列出所有的角色
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年11月5日 下午6:08:08
	 */
	List<Role> findRoleList();

	/**
	 * 
	 * 说明：添加角色并关联权限和菜单
	 * @param role
	 * @param permissionIds
	 * @param menuIds
	 * @author 传智.BoBo老师
	 * @time：2017年11月5日 下午6:48:37
	 */
	void saveRole(Role role, String[] permissionIds, String menuIds);

}
