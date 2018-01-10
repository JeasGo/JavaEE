package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Permission;

//功能权限的业务层接口
public interface PermissionService {

	/**
	 * 
	 * 说明：查询所有的功能权限列表
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年11月5日 下午5:53:26
	 */
	List<Permission> findPermissionList();

	/**
	 * 
	 * 说明：功能权限
	 * @param permission
	 * @author 传智.BoBo老师
	 * @time：2017年11月5日 下午5:59:54
	 */
	void savePermission(Permission permission);

}
