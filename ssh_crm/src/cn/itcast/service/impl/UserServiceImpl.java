package cn.itcast.service.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itheima.utils.HibernateUtils;

/**
 * @author 贾兵兵
 * @date 2017年12月19日
 */
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Override
	public User login(User user) {

		// 开启事物
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		// 1.调用Dao层根据登录名查询User对象
		User existuser = userDao.getByUser(user.getUser_code());
		tx.commit();
		if (existuser == null) {
			// 获取不到=>抛出异常提示用户不存在
			throw new RuntimeException("用户名不存在!");
		}
		// 2.对比密码是否一致
		if (!existuser.getUser_password().equals(user.getUser_password())) {
			// 不一致=>抛出异常提示密码错误
			throw new RuntimeException("密码错误!请重新输入...");
		}
		// 将数据库查询到的User返回
		return existuser;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
}
