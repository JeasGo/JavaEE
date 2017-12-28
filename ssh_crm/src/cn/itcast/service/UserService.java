package cn.itcast.service;

import cn.itcast.domain.User;

/** 
 * @author 贾兵兵 
 * @date 2017年12月19日
 */
public interface UserService {
	/**
	 * 验证登录信息执行登录操作
	 * @param user
	 * @return
	 * User
	 */
	User login(User user);

}
