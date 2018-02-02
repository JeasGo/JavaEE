package cn.jeas.bos.serivce.system;

import java.util.List;

import cn.jeas.bos.domain.system.Permission;

public interface PermissionService {

	/**
	 * 
	 * 功能:列表查询
	 * Author:jeas
	 * @return
	 * Time:2018年1月31日 下午9:00:50
	 */
	List<Permission> findPermissionList();

	/**
	 * 
	 * 功能:添加权限
	 * Author:jeas
	 * @param model
	 * Time:2018年1月31日 下午9:20:47
	 */
	void addPermission(Permission permission);

}
