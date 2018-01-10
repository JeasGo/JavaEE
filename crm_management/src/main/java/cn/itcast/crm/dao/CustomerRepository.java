package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;
//客户操作的dao
import java.lang.String;
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	//查询定区编号为空的客户列表
	public List<Customer> findByFixedAreaIdIsNull();
	//根据定区编号来查询的客户列表
	public List<Customer> findByFixedAreaId(String fixedAreaId);
	
	//根据手机号码更新类型
	@Query("update Customer set type=?2 where mobilePhone =?1")
	@Modifying
	public void updateTypeByMobilePhone(String mobilePhone,Integer type);
	
	//根据地址精确匹配查询客户
	public List<Customer> findByAddress(String address);
}
