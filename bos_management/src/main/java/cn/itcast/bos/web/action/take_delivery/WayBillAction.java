package cn.itcast.bos.web.action.take_delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.take_delivery.WayBillService;
import cn.itcast.bos.web.action.common.BaseAction;

//运单的Action
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill>{
	
	//注入service
	@Autowired
	private WayBillService wayBillService;

	//添加运单
	@Action("wayBill_add")
	public String add(){
		Map<String, Object> resultMap=new HashMap<>();
		try {
			wayBillService.saveWayBillQuick(model);
			resultMap.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
		}
		
		ActionContext.getContext().getValueStack().push(resultMap);
		
		return JSON;
	}
	
	//属性驱动封装查询参数
	private String fieldName;
	private String fieldValue;
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}


	//分页列表查询
	@Action("wayBill_listPage")
	public String listPage(){
		//请求分页对象
//		Pageable pageable=new PageRequest(page-1, rows);
		//分页排序
		Pageable pageable=new PageRequest(page-1, rows, new Sort(Direction.DESC, "id"));
		
		//响应分页对象
		Page<WayBill> pageResponse=null;
		//判断
		if(StringUtils.isBlank(fieldValue)){
			//++++++没有查询条件：直接查询数据库
			//查询所有的分页
			pageResponse= wayBillService.findWayBillListPage(pageable);
		}else{
			//++++++有查询条件。走es检索查询
			//查询所有的分页
			pageResponse= wayBillService.findWayBillListPage(pageable,fieldName,fieldValue);
		}
		
		
		//将分页结果压入root栈顶
		pushPageDataToValuestackRoot(pageResponse);
		
		return JSON;
	}
	
	
}
