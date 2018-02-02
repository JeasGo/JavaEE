package cn.jeas.bos.action.base;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
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
import cn.jeas.bos.domain.base.Courier;
import cn.jeas.bos.domain.base.Standard;
import cn.jeas.bos.serivce.base.CourierService;


@Controller
@Scope("prototype")
//@Namespace("/")
//@ParentPackage("json-default")
public class CourierAction extends BaseAction<Courier> {

	private static final long serialVersionUID = -8334808389025531752L;
	/*//声明一个数据模型对象
	private Courier courier = new Courier();
	@Override
	public Courier getModel() {
		return courier;
	}*/
	
	//属性驱动接受页面发来的页面参数
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}


	@Autowired
	//注入业务层
	private CourierService courierService;
	//courier_findnoassociation.action
	@Action("courier_findnoassociation")
	public String findnoassociation(){
		List<Courier> courierList = courierService.findCourierListNoDeltag();
		ActionContext.getContext().getValueStack().push(courierList);
		return JSON;
	}
	
	//方法
	//@Action(value="courier_add",results={@Result(name="success",type="redirect",location="pages/base/courier.html")})
	@Action(value="courier_add",results={@Result(type=REDIRECT,location="pages/base/courier.html")})
	public String add(){
		courierService.saveCourier(model);
		return SUCCESS;
	}
	@Action(value="courier_findAll")
	public String findAll (){
		List<Courier> c_list =   courierService.finAll();
		pushPageDataToValuaestackBoot((Page<Courier>) c_list);
		
		return JSON;
	}
	
	
	@Action("courier_deleteBatch")
	public String deleteBatch(){
		
		try {
			courierService.deleteCourierBatch(ids);
			pushJsonDataToValuaestackBoot(true);
		} catch (Exception e) {
			e.printStackTrace();
			pushJsonDataToValuaestackBoot(false);
		}
		return JSON;
	}
	
//	@Action(value="courier_listPage",results={@Result(name="json",type="json")})
	@Action("courier_listPage")
	public String listPage(){
		//1.请求分页的bean
		Pageable pageable = new PageRequest(page-1, rows);
		//2.业务规范条件对象
		Specification<Courier> spec = new Specification<Courier>() {
			
			@Override
			//参数1: 主查询对象
			//参数2:简单查询条件构造对象
			//参数3:复杂查询条件构造对象
			//返回值:predicate,最终的条件对象
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//存放条件的集合
				List<Predicate> andPredicate = new ArrayList<>();
				List<Predicate> orPredicate = new ArrayList<>();
				if (StringUtils.isNotBlank(model.getCourierNum())) {
					//类似与hibernate:Restrictions.eq(属性名字,值)("courierNum",model.getCourierNum)
					//参数1:属性的封装对象  参数2:值
					//返回值对象:相当于sql:courierNum=?
					Predicate p = cb.equal(root.get("courierNum").as(String.class),(model.getCourierNum()).toString());
					andPredicate.add(p);
				}
				//所属单位
				if(StringUtils.isNotBlank(model.getCompany())){
					//参数1：要指定属性的类型
					Predicate p = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					andPredicate.add(p);
				}
				//类型
				if(StringUtils.isNotBlank(model.getType())){
					Predicate p = cb.like(root.get("type").as(String.class), "%"+model.getType()+"%");
					andPredicate.add(p);
				}
				//多表
				if(model.getStandard()!=null){
					//对象连接
					//参数1：连接的属性
					//参数2：连接类型
					Join<Courier, Standard> standardJoin = root.join(root.getModel().getSingularAttribute("standard", Standard.class), JoinType.INNER);
//					Join<Object, Object> join = root.join("standard", JoinType.INNER);
//					Join<Object, Object> join2 = root.join("standard");//默认是内连接
					//收派标准的名字
					if(model.getStandard().getId() != null){
						//name是连接对象的属性
//						Predicate p = cb.like(standardJoin.get("name").as(String.class)
//								, "%"+model.getStandard().getName()+"%");
						Predicate p = cb.equal(standardJoin.get("id").as(String.class), model.getStandard().getId());
						andPredicate.add(p);
					}
				}
				
				Predicate andP = cb.and(andPredicate.toArray(new Predicate[0]));
				return andP;
			}
		};
		
		
		Page<Courier> plist = courierService.findCourierListPage(spec,pageable);
		
//		Map<String, Object> resultMap = new HashMap<>();
//		resultMap.put("total", plist.getTotalElements());
//		resultMap.put("rows", plist.getContent());
//		ActionContext.getContext().getValueStack().push(resultMap);
		
		pushPageDataToValuaestackBoot(plist);
		return JSON;
		
	}
	
}
