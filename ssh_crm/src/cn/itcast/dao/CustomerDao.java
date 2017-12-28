package cn.itcast.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.domain.Customer;

/** 
 * @author 贾兵兵 
 * @date 2017年12月17日
 */
public interface CustomerDao {
	
	/**
	 * 根据条件查询所有
	 * @param dc
	 * @return
	 * List<Customer>
	 */
	List<Customer> findAll(DetachedCriteria dc);

	void add(Customer customer); 

	List<Customer> findAll();

}
