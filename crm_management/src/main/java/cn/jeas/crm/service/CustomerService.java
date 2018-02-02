package cn.jeas.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.jeas.crm.domain.Customer;


//SET接口:暴露方法
@Path("/customers")
//消费者
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})


//生产者
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public interface CustomerService {
	@Path("/nofixedareaid")//小写URI
	@GET
	public List<Customer> findCustomerListNoFixedAreaId();

	@Path("/fixedareaid/{fixedAreaId}")//小写URI
	@GET
	public List<Customer> findCustomerListByFixedAreaId(@PathParam("fixedAreaId")String fixedAreaId);

	
	@Path("/fixedareaid/{fixedAreaId}/{customerIds}")
	@PUT //更新
	public void updateFixedAreaIdByCustomerIds(@PathParam("fixedAreaId")String fixedAreaId,@PathParam("customerIds")String customerIds);

	
	@POST
	/**
	 * 保存客户操作
	 * @param customer
	 */
	public void saveCustmer(Customer customer);
	
	@Path("/type/{telephone}/{type}")
	@PUT
	/**
	 * 功能：客户邮件激活账户
	 * Author:jeas
	 * @param telephone
	 * @param type
	 * Time:2018年1月19日 上午10:09:58
	 */
	public void updateTypeByTelephone(@PathParam("telephone")String telephone,@PathParam("type")String type);

	/**
	 * 功能：系统提供根据地址查询定区编号的接口
	 * Author:jeas
	 * @param address
	 * @return
	 * Time:2018年1月19日 上午10:26:21
	 */
	@Path("/fixedareaid/address/{address}")
	@GET
	public Customer findFixedAreaIdByAddress(@PathParam("address")String address);
}
