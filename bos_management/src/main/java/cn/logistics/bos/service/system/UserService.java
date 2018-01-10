package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.User;

//用户业务接口
public interface UserService {

	List<User> findUserList();

	void saveUser(User user, String[] roleIds);

	/**
	 * 
	 * 说明：根据激活时间锁定用户的状态
	 * @author 传智.BoBo老师
	 * @time：2017年11月7日 下午3:09:06
	 */
	void updateUserStatusByActivetimeForLock();

}
