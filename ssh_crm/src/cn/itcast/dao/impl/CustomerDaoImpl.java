package cn.itcast.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;



import cn.itcast.dao.CustomerDao;
import cn.itcast.domain.Customer;
import cn.itheima.utils.HibernateUtils;

/** 
 * @author 贾兵兵 
 * @date 2017年12月17日
 */
public class CustomerDaoImpl implements CustomerDao {
	
	//private Logger logger = LogManager.getLogger(CustomerDaoImpl.class);
	
	@Override
	public List<Customer> findAll() {
		Session session = HibernateUtils.getCurrentSession();
		List<Customer> list = session.createQuery("from Customer").list();
		
		return list;
	}

	@Override
	public void add(Customer customer) {
		//获取与线程同步的session
		Session session = HibernateUtils.getCurrentSession();
		Serializable save = session.save(customer);
		//logger.info(save);
	}

	@Override
	public List<Customer> findAll(DetachedCriteria dc) {
		//获得session
		Session session = HibernateUtils.getCurrentSession();
		Criteria c = dc.getExecutableCriteria(session);
		List<Customer> list = c.list();
		//logger.info(list);
		return list;
	}

}
