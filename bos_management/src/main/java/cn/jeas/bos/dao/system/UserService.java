package cn.jeas.bos.dao.system;

import java.util.List;

import cn.jeas.bos.domain.system.User;

public interface UserService {

	/**
	 * 
	 * 功能:添加新的用户角色
	 * Author:jeas
	 * @param model
	 * @param roleIds
	 * Time:2018年2月1日 下午9:00:19
	 */
	void saveUser(User model, Integer[] roleIds);
	
	/**
	 * 功能:查询所有用户信息
	 * Author:jeas
	 * @return
	 * Time:2018年2月1日 下午9:09:04
	 */
	List<User> findUserList();

}
