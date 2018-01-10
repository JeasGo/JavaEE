package cn.itcast.bos.web.action.base;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.utils.Constants;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;

//定区action
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {
	//注入service
	@Autowired
	private FixedAreaService fixedAreaService;
	
	//添加定区
	@Action(value="fixedArea_add",results={
			@Result(type=REDIRECT,location="/pages/base/fixed_area.html")
	})
	public String add(){
		fixedAreaService.saveFixedArea(model);
		return SUCCESS;
	}
	
	//组合条件分页列表查询
	@Action("fixedArea_listPage")
	public String listPage(){
		//1，封装条件
		//1)请求的分页bean对象
		Pageable pageable=new PageRequest(page-1, rows);
		//2)请求的业务规范对象
		Specification<FixedArea> spec=new Specification<FixedArea>() {
			
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				//获取and或or的p的对象，自带p集合，只要放入进去小p，自动就具备了and或or
				Predicate conjunctionPredicate = cb.conjunction();//and
				Predicate disjunctionPredicate = cb.disjunction();//or
				
				
				//单表
				//定区编码
				if(StringUtils.isNotBlank(model.getId())){
					conjunctionPredicate.getExpressions().add(
					cb.equal(root.get("id").as(String.class), model.getId())
					)
					;
				}
				//所属单位
				if(StringUtils.isNotBlank(model.getCompany())){
					conjunctionPredicate.getExpressions().add(
					cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%")
					)
					;
				}
				
				//多表
				
				
				return conjunctionPredicate;
			}
		};
		
		//2.调用业务层
		Page<FixedArea> pageResponse = fixedAreaService.findFixedAreaListPage(spec, pageable);
		//将分页数据重新组装压入root栈顶
		pushPageDataToValuestackRoot(pageResponse);
		
		return JSON;
	}
	
	//没有关联任何定区的客户列表
	@Action("fixedArea_listCustomerListNoFixedAreaId")
	public String listCustomerListNoFixedAreaId(){
		//远程调用RESTful接口
		Collection<? extends Customer> customerList = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
//		Collection<? extends Customer> customerList = WebClient.create("http://localhost:9002/crm_management/services/customerservice/customers")
		.path("/nofixedarea")
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		
		//压入root栈顶
		ActionContext.getContext().getValueStack().push(customerList);
		
		return JSON;
	}
	
	//关联选中定区的客户列表
	@Action("fixedArea_listCustomerListByFixedAreaId")
	public String listCustomerListByFixedAreaId(){
		//远程调用RESTful接口
//		Collection<? extends Customer> customerList = WebClient.create("http://localhost:9002/crm_management/services/customerservice/customers")
		Collection<? extends Customer> customerList = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/fixedAreaId")
		.path("/"+model.getId())
		.type(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		
		//压入root栈顶
		ActionContext.getContext().getValueStack().push(customerList);
		
		return JSON;
	}
	
	//属性驱动封装客户编号
	private String[] customerIds;
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	//关联客户和定区
	@Action(value="fixedArea_associationCustomersToFixedArea",results={
			@Result(type=REDIRECT,location="/pages/base/fixed_area.html")
	})
	public String associationCustomersToFixedArea(){
		//将客户数组重新组装为逗号分割
		String cIds = StringUtils.join(customerIds, ",");
		
		//远程调用RESTful接口
//		WebClient.create("http://localhost:9002/crm_management/services/customerservice/customers")
		 WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
			.path("/fixedAreaId")
			.path("/"+model.getId())
			.path("/"+cIds)//如果全部取消那么客户编号是"null"
			.type(MediaType.APPLICATION_JSON)
			.put(null);
		
		return SUCCESS;
	}
	
	//属性驱动封装两个参数
	private Integer courierId;//快递员id
	private Integer takeTimeId;//取派时间id
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	//定区关联快递员
	@Action(value="fixedArea_associationCourierToFixedArea",results={
			@Result(type=REDIRECT,location="/pages/base/fixed_area.html")
	})
	public String associationCourierToFixedArea(){
		fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
		return SUCCESS;
	}
	
}
