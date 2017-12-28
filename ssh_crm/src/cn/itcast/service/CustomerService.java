package cn.itcast.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.domain.Customer;

/** 
 * @author 贾兵兵 
 * @date 2017年12月17日
 */
public interface CustomerService {

	List<Customer> findAllCustomer();

	void add(Customer customer);
	/**
	 * 根据条件查询客户
	 * @param dc
	 * @return
	 * List<Customer>
	 */
	List<Customer> findAllCustomer(DetachedCriteria dc);



}
