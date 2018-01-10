package cn.itcast.bos.web.action.base;

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
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.CourierService;
import cn.itcast.bos.web.action.common.BaseAction;

//快递员的action
@Controller
@Scope("prototype")
//@Namespace("/")//命名空间，访问路径
//@ParentPackage("json-default")//父包
//public class CourierAction extends ActionSupport implements ModelDriven<Courier>{
public class CourierAction extends BaseAction<Courier>{

	//声明数据模型
//	private Courier courier=new Courier();
//	@Override
//	public Courier getModel() {
//		return courier;
//	}
	
	//注入service
	@Autowired
	private CourierService courierService;
	
	//添加
	@Action(value="courier_add",results={
			@Result(name="success",type="redirect",location="/pages/base/courier.html")
	})
	public String add(){
		//调用业务层
		courierService.saveCourier(model);
		
		return SUCCESS;
	}
	
//	//属性驱动接收分页参数
//	private int page;
//	private int rows;
//	public void setPage(int page) {
//		this.page = page;
//	}
//	public void setRows(int rows) {
//		this.rows = rows;
//	}
	//组合条件分页查询\
	@Action("courier_listPage")
	public String listPage(){
		
		//Specification的方案（组合条件分页查询）
		
		//1）请求的分页bean
		Pageable pageable=new PageRequest(page-1, rows);
		//2)业务（规范）条件对象
		Specification<Courier> spec=new Specification<Courier>() {
			
			@Override
			//参数1：根（主）查询对象，DetachedCriteria.forClass(Courier.class),对于sql，主表，对于面向对象，根对象
			//参数2：简单查询条件构造对象（jpa的Criteria）
			//参数3：复杂查询条件构造对象（jpa的Criteria）
			//返回值：Predicate，最终的条件对象
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//存放条件的集合
				List<Predicate> andPredicate=new ArrayList<>();//and
				List<Predicate> orPredicate=new ArrayList<>();//or
				
				
				//目标：拼接业务条件对象
				//---单表
				//工号
				if(StringUtils.isNotBlank(model.getCourierNum())){
					//类似于hibernate:Restrictions.eq(属性名字，值)("courierNum",model.getCourierNum())
					//参数1：属性的封装对象
					//参数2：值
					//返回对象：相当于sql：courierNum=?
//					Predicate p = cb.equal(root.get("courierNum"), model.getCourierNum());
					Predicate p = cb.equal(root.get("courierNum").as(String.class), model.getCourierNum());
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
				
				//---多表
				//收派标准
				if(model.getStandard()!=null){
					//对象连接
					//参数1：连接的属性
					//参数2：连接类型
					Join<Courier, Standard> standardJoin = root.join(root.getModel().getSingularAttribute("standard", Standard.class), JoinType.INNER);
//					Join<Object, Object> join = root.join("standard", JoinType.INNER);
//					Join<Object, Object> join2 = root.join("standard");//默认是内连接
				
					//收派标准的名字
					if(StringUtils.isNotBlank(model.getStandard().getName())){
						//name是连接对象的属性
						Predicate p = cb.like(standardJoin.get("name").as(String.class)
								, "%"+model.getStandard().getName()+"%");
						andPredicate.add(p);
					}
				}
				
				//原则：积木编程：代码逻辑像积木，先制造积木，最后拼接到一起
				
				//将所有条件组合到一起：and或or
//				cb.and(restrictions);
//				cb.or(p1,p2)
				/*
				 * 像 toArray() 方法一样，此方法充当基于数组的 API 与基于 collection 的 API 之间的桥梁。
				 * 更进一步说，此方法允许对输出数组的运行时类型进行精确控制，在某些情况下，可以用来节省分配开销。 
				 */
				Predicate andP = cb.and(andPredicate.toArray(new Predicate[0]));
				
				//（p1 and p2） or (p3 or p4)
//				p11=cb.and(p1,p2);
//				p22=cb.or(p3,p4);
//				p=cb.or(p11,p22);
				
				return andP;
			}
		};
		//调用业务层
		Page<Courier> pageResponse = courierService.findCourierListPage(spec, pageable);
		//将数据重新组装
		//json对象对应的java的常用数据结构：map
//		Map<String, Object> resultMap=new HashMap<>();
//		//总记录数
//		resultMap.put("total", pageResponse.getTotalElements());
//		
//		//当前页的数据列表
//		resultMap.put("rows", pageResponse.getContent());
//		
//		//下面要利用struts2的json插件，将map转换为json对象，并写入响应
//		//将map压入root栈顶
//		ActionContext.getContext().getValueStack().push(resultMap);
		
		pushPageDataToValuestackRoot(pageResponse);
		
		return JSON;
		
	}
	
	//属性驱动封装
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}

	//作废快递员
	@Action("courier_deleteBatch")
	public String deleteBatch(){
		Map<String, Object> resultMap=new HashMap<>();
		
		try {
			//调用业务层
			courierService.deleteCourierBatch(ids);
			//成功
			resultMap.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			//失败
			resultMap.put("result", false);
		}
		
		//将结果压入root栈顶
		ActionContext.getContext().getValueStack().push(resultMap);
		
		return JSON;
	}
	
	//列出没有删除标记的快递员
	@Action("courier_listNoDeltag")
	public String listNoDeltag(){
		
		List<Courier> courierList= courierService.findCourierListNoDeltag();
		ActionContext.getContext().getValueStack().push(courierList);
		
		return JSON;
	}

}
