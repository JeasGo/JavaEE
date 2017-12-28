package cn.itcast.service.impl;

import java.util.List;



import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.dao.CustomerDao;
import cn.itcast.dao.impl.CustomerDaoImpl;
import cn.itcast.domain.Customer;
import cn.itcast.service.CustomerService;
import cn.itheima.utils.HibernateUtils;

/** 
 * @author 贾兵兵 
 * @date 2017年12月17日
 */
public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;
	@Override
	public List<Customer> findAllCustomer() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Customer> list  = customerDao.findAll();
		tx.commit();
		return list;
	}
	@Override
	public void add(Customer customer) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		customerDao.add(customer);
		tx.commit();
	}
	@Override
	public List<Customer> findAllCustomer(DetachedCriteria dc) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Customer> list  = customerDao.findAll(dc);
		tx.commit();
		return list;
	}
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	
}
