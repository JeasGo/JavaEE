package cn.jeas.bos.action.base;



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

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.base.WayBill;
import cn.jeas.bos.serivce.base.WayBillService;

@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {

	private static final long serialVersionUID = -4386569875450620788L;

	// 注入Service
	@Autowired
	private WayBillService wayBillService;

	// 属性驱动接受页面发来的页面参数
	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	//属性驱动封装
	private String fieldName;
	private String fieldValue;
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	
	
	@Action("wayBill_addquick")
	public String quickadd(){
		try{
		wayBillService.saveWayBillQuick(model);
		pushJsonDataToValuaestackBoot(true);
		}catch(Exception e){
			e.printStackTrace();
			pushJsonDataToValuaestackBoot(false);
		}
		
		return JSON;
	}
	

	@Action("wayBill_listPage")
	public String listPage() {
		Pageable pageable = new PageRequest(page - 1, rows,new Sort(Direction.DESC,"wayBillNum"));
		
		//响应的分页结果对象
		Page<WayBill> pageResponse=null;
		//判断
		if(StringUtils.isBlank(fieldValue)){
			//用户没有输入值，就查询所有
			pageResponse= wayBillService.findByListPage(pageable);
		}else{
			//用户有输入查询关键字，要根据条件查询
			pageResponse= wayBillService.findWayBillListPage(pageable,fieldName,fieldValue);
		}
//		Page<WayBill> pageResponse=wayBillService.findByListPage(pageable);
		
		
		pushPageDataToValuaestackBoot(pageResponse);
		return JSON;
	}

}
