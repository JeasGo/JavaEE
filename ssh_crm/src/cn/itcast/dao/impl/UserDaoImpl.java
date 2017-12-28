package cn.itcast.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itheima.utils.HibernateUtils;

/** 
 * @author 贾兵兵 
 * @date 2017年12月19日
 */
public class UserDaoImpl implements UserDao {

	@Override
	public User getByUser(String user_code) {
		//HQL查询
		
		//1.获得session
		
		Session session = HibernateUtils.getCurrentSession();
		
		//2.书写HQL语句
		String hql = "from User where user_code = ?";
		//3.创建查询对象
		Query query = session.createQuery(hql);
		//4.设置参数
		query.setParameter(0, user_code);
		//5.执行查询
		User result = (User) query.uniqueResult();
		System.out.println(result);
		return result;
	}

}
