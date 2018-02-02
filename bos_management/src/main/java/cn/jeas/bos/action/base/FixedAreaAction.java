package cn.jeas.bos.action.base;

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

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.base.FixedArea;
import cn.jeas.bos.serivce.base.FixedAreaService;
import cn.jeas.bos.utils.Constants;
import cn.jeas.crm.domain.Customer;

@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

	private static final long serialVersionUID = 3225792432453322115L;

	// 注入service层
	@Autowired
	private FixedAreaService fixedAreaService;

	// 属性驱动接受页面发来的页面参数
	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	private Integer courierId;
	private Integer takeTimeId;

	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	private String[] customerIds;

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	// 定区关联快递员
	@Action(value = "fixedArea_associationCourierToFixedArea", results = {
			@Result(type = REDIRECT, location = "/pages/base/fixed_area.html") })
	public String associationCourierToFixedArea() {

		fixedAreaService.associationCourierToFixedArea(model, courierId, takeTimeId);

		return SUCCESS;
	}

	// 没有关联定区的客户列表
	@Action("fixedArea_listCustomerListNoFixedAreaId")
	public String listCustomerListNoFixedAreaId() {
		// Webservice调用
		Collection<? extends Customer> collection = WebClient
				.create("http://localhost:9002/crm_management/services/customerservice/customers")
				.path("/nofixedareaid")// 底层字符串拼接
				.accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		// 压入root栈顶
		ActionContext.getContext().getValueStack().push(collection);
		return JSON;
	}

	// 已经关联指定定区的客户列表
	@Action("fixedArea_listCustomerListByFixedAreaId")
	public String listCustomerListByFixedAreaId() {
		// Webservice调用
		// System.err.println("=============================================================================进来了!!!!!!!!!!!!!!");
		Collection<? extends Customer> collection = WebClient
				.create("http://localhost:9002/crm_management/services/customerservice/customers").path("/fixedareaid") /// crm_management/services/customerservice/customers/fixedareaid
				.path("/" + model.getId()).accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		// 压入root栈顶
		ActionContext.getContext().getValueStack().push(collection);
		return JSON;
	}

	@Action(value = "fixedArea_associationCustomersToFixedArea", results = {
			@Result(type = REDIRECT, location = "/pages/base/fixed_area.html") })
	public String associationCustomersToFixedArea() {

		// customerIds = customerIds.replaceAll(" ", "");

		// 将客户id数组转换为ids，逗号分割
		System.err.println("==================================================================" + customerIds);
		String cIds = StringUtils.join(customerIds, ",");
		System.err.println("=====================================================================" + cIds);
		// cIds=cIds.replaceAll(" ", "");
		// Webservice调用
		WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers").path("/fixedareaid")
				.path("/" + model.getId()).path("/" + cIds).type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).put(null);
		// 压入root栈顶
		return SUCCESS;
	}

	// 组合条件分页查询
	@Action("fixedArea_listPage")
	public String listPage() {
		// 第一步：封装参数
		// 请求的分页bean
		Pageable pageable = new PageRequest(page - 1, rows);
		// 请求的业务条件
		Specification<FixedArea> spec = new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				// 提供
				Predicate andPredicate = cb.conjunction();// and关系
				Predicate disjunction = cb.disjunction();// or的关系

				// ===单表
				// 定区编码
				if (StringUtils.isNotBlank(model.getId())) {
					andPredicate.getExpressions().add(cb.equal(root.get("id").as(String.class), model.getId()));
				}
				// 所属单位
				if (StringUtils.isNotBlank(model.getCompany())) {
					andPredicate.getExpressions()
							.add(cb.like(root.get("company").as(String.class), "%" + model.getCompany() + "%"));
				}

				return andPredicate;
			}
		};

		// 调用业务层
		Page<FixedArea> pageResponse = fixedAreaService.findFixedAreaListPage(spec, pageable);
		// 将结果重新组装压入root栈顶
		pushPageDataToValuaestackBoot(pageResponse);

		return JSON;
	}

	// 添加
	@Action(value = "fixedArea_add", results = { @Result(type = REDIRECT, location = "/pages/base/fixed_area.html") })
	public String add() {
		fixedAreaService.saveFixedArea(model);
		return SUCCESS;
	}

}
