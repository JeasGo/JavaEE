package cn.jeas.bos.action.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.base.Area;
import cn.jeas.bos.domain.base.Order;
import cn.jeas.bos.serivce.base.OrderService;


@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

	private static final long serialVersionUID = 5534122255563759214L;

	@Autowired
	private OrderService orderService;
	
	//属性驱动获取区域信息:格式:省/市/区
	private String sendAreaInfo;
	private String recAreaInfo;
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}
	
	//添加新的订单
	@Action(value="order_add",results={
			@Result(type=REDIRECT,location="/pages/take_delivery/order.html")
	})
	public String add(){
		String[] sendAreaInfoArray = StringUtils.split(sendAreaInfo,"/");
		String[] recAreaInfoArray = StringUtils.split(recAreaInfo, "/");
		//封装省市县
		
		//寄件人
		Area sendArea = new Area();
		sendArea.setProvince(sendAreaInfoArray[0]);
		sendArea.setCity(sendAreaInfoArray[1]);
		sendArea.setDistrict(sendAreaInfoArray[2]);
		
		//收件人
		Area recArea = new Area();
		recArea.setProvince(recAreaInfoArray[0]);
		recArea.setCity(recAreaInfoArray[1]);
		recArea.setDistrict(recAreaInfoArray[2]);
		
		model.setRecArea(recArea);
		model.setSendArea(sendArea);
		
		orderService.saveOrder(model);
		return SUCCESS;
	}
	
	
	
	
}
