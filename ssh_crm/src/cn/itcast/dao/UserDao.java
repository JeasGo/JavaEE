package cn.itcast.dao;

import cn.itcast.domain.User;

/** 
 * @author 贾兵兵 
 * @date 2017年12月19日
 */
public interface UserDao {

	/**
	 * 根据用户名查询此用户是否存在
	 * @param user_name
	 * @return
	 * User
	 */
	User getByUser(String user_name);

}
