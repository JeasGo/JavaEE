package cn.itcast.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

//sei的实现类
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService{
	
	//注入dao
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findCustomerListNoFixedAreaId() {
		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findCustomerListByFixedAreaId(String fixedAreaId) {
		return customerRepository.findByFixedAreaId(fixedAreaId);
	}

	@Override
	public void updateFixedAreaIdByCustomerIds(String fixedAreaId, String customerIds) {
		//先去掉原来的所有关联，再关联新的关联
		//技术方案：推荐使用hibernate的快照更新
		//1先去掉原来的所有关联
		//查询所有关联某定区的客户列表
		List<Customer> customerOldList = customerRepository.findByFixedAreaId(fixedAreaId);
		//将定区编号置空
		for (Customer customer : customerOldList) {
			//快照更新
			customer.setFixedAreaId(null);
			//等flush
		}
		//2再关联新的关联
		if(StringUtils.isNotBlank(customerIds)&&!customerIds.equals("null")){
			String[] ids = customerIds.split(",");
			for (String id : ids) {
				//先查询
				Customer customer = customerRepository.findOne(Integer.parseInt(id));
				//快照更新
				customer.setFixedAreaId(fixedAreaId);
				//等flush
			}
			
		}
		
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public void updateTypeByMobilePhone(String mobilePhone, Integer type) {
		customerRepository.updateTypeByMobilePhone(mobilePhone, type);
	}

	@Override
	public Customer findCustomerByAddressForFixedAreaId(String address) {
		List<Customer> list = customerRepository.findByAddress(address);
		//如果一个都没有返回空，否则返回第一个元素
//		return list.isEmpty()?null:list.get(0);
		if(list!=null && !list.isEmpty()){
			Customer c=new Customer();
			c.setFixedAreaId(list.get(0).getFixedAreaId());
			return c;
		}else{
			return null;
		}
	}

}
