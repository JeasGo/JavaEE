package cn.itcast.bos.service.take_delivery;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.itcast.bos.domain.take_delivery.Order;

//订单接口
@Path("/orders")
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})//消费的mime类型
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})//生产者的mime类型（响应回去的表述格式）
public interface OrderService {

	/**
	 * 
	 * 说明：保存订单
	 * @param order
	 * @author 传智.BoBo老师
	 * @time：2017年10月27日 下午5:07:01
	 */
	@POST
	void saveOrder(Order order);

}
