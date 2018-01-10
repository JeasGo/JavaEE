package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.itcast.crm.domain.Customer;

//客户SEI接口
@Path("/customers")
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})//消费的mime类型
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})//生产者的mime类型（响应回去的表述格式）
public interface CustomerService {
	
	/**
	 * 
	 * 说明：查询没有关联定区的客户列表
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月22日 下午4:56:23
	 */
	@Path("/nofixedarea")
	@GET
	public List<Customer> findCustomerListNoFixedAreaId();
	
	/**
	 * 
	 * 说明：根据定区编号来查询客户列表
	 * @param fixedAreaId
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月22日 下午4:57:03
	 */
	@Path("/fixedAreaId/{fixedAreaId}")
	@GET
	public List<Customer> findCustomerListByFixedAreaId(@PathParam("fixedAreaId")String fixedAreaId );
	
	
	/**
	 * 
	 * 说明：根据客户编号列表来批量更新定区编号
	 * @param fixedAreaId 定区编号
	 * @param customerIds 客户编号列表，多个客户用英文逗号分割
	 * @author 传智.BoBo老师
	 * @time：2017年10月22日 下午4:58:58
	 */
	@Path("/fixedAreaId/{fixedAreaId}/{customerIds}")
	@PUT
	public void updateFixedAreaIdByCustomerIds
	(@PathParam("fixedAreaId")String fixedAreaId,@PathParam("customerIds")String customerIds);
	
	/**
	 * 
	 * 说明：保存一个客户
	 * @param customer
	 * @author 传智.BoBo老师
	 * @time：2017年10月24日 下午4:09:07
	 */
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})//消费的mime类型
	public void saveCustomer(Customer customer);
	
	/**
	 * 
	 * 说明：更新类型通过手机号码
	 * @param mobilePhone
	 * @param type
	 * @author 传智.BoBo老师
	 * @time：2017年10月24日 下午6:29:38
	 */
	@Path("/type/{mobilePhone}/{type}")
	@PUT
	public void updateTypeByMobilePhone(@PathParam("mobilePhone")String mobilePhone,@PathParam("type")Integer type);
	
	/**
	 * 
	 * 说明：根据客户地址查询定区编号，结果封装到customer对象中
	 * @param address
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2017年10月29日 下午3:15:24
	 */
	@Path("/fixedAreaId/address/{address}")
	@GET
	public Customer findCustomerByAddressForFixedAreaId(@PathParam("address")String address);
	
	
}
