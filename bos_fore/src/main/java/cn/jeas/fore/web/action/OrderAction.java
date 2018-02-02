package cn.jeas.fore.web.action;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.base.Area;
import cn.jeas.bos.domain.base.Order;
import cn.jeas.bos.utils.Constants;

@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

	private static final long serialVersionUID = -2406709837388460088L;

	
	//属性驱动获得地址
	private String sendAreaInfo;
	private String recAreaInfo;
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}
	
	@Action(value="order_add",results={
		@Result(type=REDIRECT,location="index.html")
	})
	public String add(){
		//寄件人省市区
		String[] sendAreaInfoArr = sendAreaInfo.split("/");
		Area sendArea = new Area();
		sendArea.setProvince(sendAreaInfoArr[0]);
		sendArea.setCity(sendAreaInfoArr[1]);
		sendArea.setDistrict(sendAreaInfoArr[2]);
		model.setSendArea(sendArea);
		
		//收件人省市区
		String[] recAreaInfoArr = recAreaInfo.split("/");
		Area recArea = new Area();
		recArea.setProvince(recAreaInfoArr[0]);
		recArea.setCity(recAreaInfoArr[1]);
		recArea.setDistrict(recAreaInfoArr[2]);
		model.setRecArea(recArea);
		
		Response response = WebClient.create(Constants.BOS_MANAGEMENT_URL+"/services/orderservice/orders/save")
				.type(MediaType.APPLICATION_JSON)
				.post(model);

		System.out.println("保存后的状态码："+response.getStatus());
		
		
		return  SUCCESS;
		
	}
	
	
	
}	
