package cn.jeas.bos.serivce.base;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.jeas.bos.domain.base.Order;

@Path("/orders")
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})//消费者
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})//生产者
public interface OrderService {

	/**
	 * 功能:保存订单
	 * Author:jeas
	 * @param model
	 * Time:2018年1月18日 下午7:56:52
	 */
	@POST
	void saveOrder(Order order);

}
