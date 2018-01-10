package cn.itcast.bos.service.impl.take_delivery;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.take_delivery.OrderRepository;
import cn.itcast.bos.dao.take_delivery.WorkBillRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.service.take_delivery.OrderService;
import cn.itcast.bos.utils.Constants;
import cn.itcast.crm.domain.Customer;
//订单业务实现
@Service("orderService")//bean的名字
@Transactional
public class OrderServiceImpl implements OrderService {
	//注入dao
	@Autowired
	private OrderRepository orderRepository;
	//注入区域dao
	@Autowired
	private AreaRepository areaRepository;
	
	//注入定区dao
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	//注入工单dao
	@Autowired
	private WorkBillRepository workBillRepository;

	@Override
	public void saveOrder(Order order) {
		
		//为了测试，打印order
		System.out.println(order);
		if(1==1){
			return;
		}
		
		
		//将区域（两个）变成持久态
		//寄件人区域
		Area sendArea = areaRepository.findByProvinceAndCityAndDistrict(
				order.getSendArea().getProvince()
				,order.getSendArea().getCity()
				,order.getSendArea().getDistrict()
				);
		//收件人区域
		Area recArea = areaRepository.findByProvinceAndCityAndDistrict(
				order.getRecArea().getProvince()
				,order.getRecArea().getCity()
				,order.getRecArea().getDistrict()
				);
		order.setSendArea(sendArea);
		order.setRecArea(recArea);
		
		//处理没有值的字段
		//订单号(实际也中，该号码是需要一定规则去生成的)
		order.setOrderNum(UUID.randomUUID().toString());
		//下单时间
		order.setOrderTime(new Date());
		//分单类型(默认)
		order.setOrderType("人工分单");
		//订单状态
		order.setStatus("待取件");
		
		//保存订单
		orderRepository.save(order);
		
		//=========自动分单=====================
		String sendAddress = order.getSendAddress();//详细地址
		//完整地址：北京市北京市东城区欢乐校区1号楼
		String address=order.getSendArea().getProvince()
				+order.getSendArea().getCity()
				+order.getSendArea().getDistrict()
				+sendAddress;
		
		//规则1：客户地址完全匹配已经下过单的客户的地址
		Customer customer=null;
		try {
			customer = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
			.path("/fixedAreaId/address")
			.path("/"+address)
			.type(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.get(Customer.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("bos系统调用crm系统根据地址查询定区失败！");
		}
		
		//逻辑判断
		if(customer!=null){
			//判断定区id是否存在
			if(StringUtils.isNotBlank(customer.getFixedAreaId())){
				//存在，查询定区
				FixedArea fixedArea = fixedAreaRepository.findOne(customer.getFixedAreaId());
				//判断是否找到定区
				if(fixedArea!=null){
					//找到定区,判断该定区有没有关联快递员
					if(!fixedArea.getCouriers().isEmpty()){
						//有关联的快递员
						Set<Courier> couriers = fixedArea.getCouriers();
						//到底派谁去？涉及到排班（上下班时间，要根据tabketime表和当前下单时间对比）
						//和替班（根据排班规则找到快递员，再去看该快递员有没有被替班，如果有，则替班的派出去）
						//这里直接随便选一个派出去了。
						//找到唯一的快递员！！
						Courier courier = couriers.iterator().next();
						
						//订单有些值
						order.setOrderType("自动分单");//分单类型
						order.setCourier(courier);//订单关联快递员（）
						
						//自动生成工单（插入操作）给快递员
						saveWorkBill(order, courier);
						
						//如果分单成功，无需再分单
						return;
					}
				}
			}
			
		}
		
		//规则2：客户地址关键字匹配分区
		//首先要找到区域（上面的代码sendArea）
		//通过区域找到所有下面的分区（导航查询）
		Set<SubArea> subareas = sendArea.getSubareas();
		//遍历循环分区
		for (SubArea subArea : subareas) {
			//根据分区中关键字匹配详细地址，找需要的分区
			String keyWords = subArea.getKeyWords();
			//判断是否找到
			if(sendAddress.contains(keyWords)){
				//如果找到
				//根据分区找定区
				FixedArea fixedArea = subArea.getFixedArea();
				//判断是否找到定区
				if(fixedArea!=null){
					//找到定区,判断该定区有没有关联快递员
					if(!fixedArea.getCouriers().isEmpty()){
						//有关联的快递员
						Set<Courier> couriers = fixedArea.getCouriers();
						//到底派谁去？涉及到排班（上下班时间，要根据tabketime表和当前下单时间对比）
						//和替班（根据排班规则找到快递员，再去看该快递员有没有被替班，如果有，则替班的派出去）
						//这里直接随便选一个派出去了。
						//找到唯一的快递员！！
						Courier courier = couriers.iterator().next();
						
						//订单有些值
						order.setOrderType("自动分单");//分单类型
						order.setCourier(courier);//订单关联快递员（）
						//自动生成工单（插入操作）给快递员
						saveWorkBill(order, courier);
						
						//如果分单成功，无需再分单
						return;
					}
				}
				
				break;//不找了
			}
		}
		
		
		
		
		
	}

	//保存工单代码:改派还能用
	public void saveWorkBill(Order order, Courier courier) {
		//发送短信通知（异步消息mq）
		//保存工单
		WorkBill workBill=new WorkBill();
//						workBill.setId(id);//主键，自动
		workBill.setBuildtime(new Date());//工单生成时间,当前时间
		workBill.setCourier(courier);//该工单绑定某快递员，外键
		workBill.setOrder(order);//该工单关联订单，外键
		workBill.setType("新");//工单类型 新,追,销
		workBill.setAttachbilltimes(0);//追单次数，默认是0，每追一次+1
		/*
		 * 快递员来操作的状态。
		 * 新单:没有确认货物状态的 已通知:自动下单下发短信 已确认:接到短信,回复收信确认信息 已取件:已经取件成功,发回确认信息 生成工作单
		 * 已取消:销单
		 */
		workBill.setPickstate("新单");
		//在下工单之前，要发出一条短信，短信主键给这里复制
		workBill.setSmsNumber("100000");//短信序号,短信表的主键。短信和工单绑定
		//冗余设计好处，查询的时候，无需多表关联查询。
		workBill.setRemark(order.getRemark());//备注！冗余设计：工单和订单中都有同样的备注（客户针对邮寄订单注意事项）
		
		//保存动作
		workBillRepository.save(workBill);
	}

}
