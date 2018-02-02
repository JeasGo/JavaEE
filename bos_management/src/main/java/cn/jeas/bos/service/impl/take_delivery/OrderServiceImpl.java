package cn.jeas.bos.service.impl.take_delivery;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jeas.bos.dao.base.AreaRepository;
import cn.jeas.bos.dao.base.FixedAreaRepository;
import cn.jeas.bos.dao.base.OrderRepository;
import cn.jeas.bos.dao.base.WorkBillRepository;
import cn.jeas.bos.domain.base.Area;
import cn.jeas.bos.domain.base.Courier;
import cn.jeas.bos.domain.base.FixedArea;
import cn.jeas.bos.domain.base.Order;
import cn.jeas.bos.domain.base.SubArea;
import cn.jeas.bos.domain.base.WorkBill;
import cn.jeas.bos.serivce.base.OrderService;
import cn.jeas.bos.utils.Constants;
import cn.jeas.crm.domain.Customer;

@Service("orderServiceTake")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	// 注入repository
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	// 注入工单的dao
	@Autowired
	private WorkBillRepository workBillRepository;

	@Override
	public void saveOrder(Order order) {
		// 将区域对象变成持久态
		// 寄件
		Area sendAreaPersist = areaRepository.findByProvinceAndCityAndDistrict(order.getSendArea().getProvince(),
				order.getSendArea().getCity(), order.getSendArea().getDistrict());
		// 收件
		Area recAreaPersist = areaRepository.findByProvinceAndCityAndDistrict(order.getRecArea().getProvince(),
				order.getRecArea().getCity(), order.getRecArea().getDistrict());

		// 放进去
		order.setSendArea(sendAreaPersist);
		order.setRecArea(recAreaPersist);
		// 订单ID
		order.setOrderNum(UUID.randomUUID().toString());
		// 订单时间
		order.setOrderTime(new Date());
		// 订单中给客户发送短信的内容
		order.setSendMobileMsg("您的订单已经收到");
		// 订单状态
		order.setStatus("待取件");
		// 分单类型
		order.setOrderType("人工分单");
		orderRepository.save(order);

		String address = sendAreaPersist.getProvince() + sendAreaPersist.getCity() + sendAreaPersist.getDistrict()
				+ order.getSendAddress();
		Customer customer = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
				.path("/fixedareaid/address")
				.path("/"+address)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.get(Customer.class);
		if(null!=customer){
			//获取定区id
			String fixedAreaId = customer.getFixedAreaId();
			//判断定区id是否存在
			if(StringUtils.isNotBlank(fixedAreaId)){
				//存在,根据id查询定区
				FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
				//判断是否找到定区
				if(null!=fixedArea){
					//找到定区，找到快递员集合
					Set<Courier> courierSet = fixedArea.getCouriers();
					/*
					 * 业务扩展描述：
					 * 找到快递员集合后，根据更多的纬度来寻找最终的那个快递员。
					 * 比如可以根据收派时间（上下班时间）、收派标准（运送能力）、替班
					 */
					//简化业务，让一个定区中就有一个快递员。
					if( null!=courierSet&&!courierSet.isEmpty()){
						//找到快递员了！！
						Courier courier = courierSet.iterator().next();
						
						//订单状态
						order.setOrderType("自动分单");
						//订单关联快递员
						order.setCourier(courier);
						
						//下工单-给快递员看的
						saveWayBill(order, courier);
						return;
					}
				}
				
			}
		}
		
		if(sendAreaPersist!=null){
			Set<SubArea> subareas = sendAreaPersist.getSubareas();
			if(subareas!=null&&!subareas.isEmpty()){
				for (SubArea subArea : subareas) {
					if (order.getSendAddress().contains(subArea.getAssistKeyWords())) {
						FixedArea fixedArea = subArea.getFixedArea();
						if(null!=fixedArea){
							//找到定区，找到快递员集合
							Set<Courier> courierSet = fixedArea.getCouriers();
							/*
							 * 业务扩展描述：
							 * 找到快递员集合后，根据更多的纬度来寻找最终的那个快递员。
							 * 比如可以根据收派时间（上下班时间）、收派标准（运送能力）、替班
							 */
							//简化业务，让一个定区中就有一个快递员。
							if( null!=courierSet&&!courierSet.isEmpty()){
								//找到快递员了！！
								Courier courier = courierSet.iterator().next();
								
								//订单状态
								order.setOrderType("自动分单");
								//订单关联快递员
								order.setCourier(courier);
								
								saveWayBill(order, courier);
								
								return;
							}
						}
						
						
						break;
					}
				}
			}
		}
	}

	private void saveWayBill(Order order, Courier courier) {
		WorkBill workBill=new WorkBill();
//						workBill.setId(id);//oid
		workBill.setType("新");//工单类型，默认是新单，如果被追单了就是追，如果要销单了，就是销
		workBill.setAttachbilltimes(0);//追单次数，没追一次+1
		workBill.setPickstate("新单");// 取件状态，该状态根据快递员的取件情况进行变化
		workBill.setBuildtime(new Date());//工单生成时间
		workBill.setOrder(order);//工单关联订单
		workBill.setCourier(courier);//工单关联快递员
		workBill.setRemark(order.getRemark());//备注：给快递员捎话，表的冗余设计。
		workBill.setSmsNumber("1111");//给快递员下发的短信的序号
		
		workBillRepository.save(workBill);
	}
	
}
