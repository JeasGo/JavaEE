package cn.itcast.service.impl;

import cn.itcast.dao.CustomerDao;
import cn.itcast.dao.LinkManDao;
import cn.itcast.dao.impl.LinkManDaoImpl;
import cn.itcast.service.LinkManService;

/** 
 * @author jeas 
 * @date 2017年12月22日
 */
public class LinkManServiceImpl implements LinkManService {
	private LinkManDao linkManDao;
	private CustomerDao customerDao;
	
	
	
	
	public void setLinkManDao(LinkManDao linkManDao) {
		this.linkManDao = linkManDao;
	}
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	
	
	
}
