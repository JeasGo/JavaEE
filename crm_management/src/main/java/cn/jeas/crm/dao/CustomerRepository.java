package cn.jeas.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.jeas.crm.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	List<Customer> findByFixedAreaIdIsNull();

	List<Customer> findByFixedAreaId(String fixedAreaId);

	Customer findOne(int id);

	Customer findByTelephone(String telephone);
	
	List<Customer> findByAddress(String address);

	//根据地址查询定区编号
	@Query("select fixedAreaId from  Customer where address =?")
	String findFixedAreaIdByAddress(String address);


}
