package cn.jeas.bos.action.base;





import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.jeas.bos.action.base.common.BaseAction;
import cn.jeas.bos.domain.base.Standard;
import cn.jeas.bos.serivce.base.StandardService;



@Controller //bean组件
@Scope("prototype")  //多例化实现
//@Namespace("/")// 命名空间,访问路径
////@ParentPackage("struts-default")  //struts父包
//@ParentPackage("json-default") //json父包  继承了stryts-default
public class StandardAction extends BaseAction<Standard>{

	private static final long serialVersionUID = 2896931249756542481L;
	
	//声明一个数据模型对象
	/*private Standard standard =  new Standard();
	@Override
	public Standard getModel() {
		// TODO Auto-generated method stub
		return standard;
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

	//注入业务层
	@Autowired
	private StandardService standardService;
	
	
	//添加方法
	@Action(value="standard_add"//action访问的名字
			,results={
					@Result(type=REDIRECT ,location="pages/base/standard.html")
			}	
				)
	public String add(){
		//调用service保存standard
		standardService.saveStandard(model);
		//返回 跳转到页面
		return SUCCESS;
	}
	
	
	//分页查询列表
	@Action("standard_listPage")
	public String listPage(){
		//选择SpringDatajpa
		//创建一个请求的页面bean对象,用来封装分页参数;
		//参数1:当前页码  ,zero-based page index,页面是从0开始检索的,所以页码必须-1
		//参数2 ,每页的最大记录数
		Pageable pageable = new PageRequest(page-1, rows);
		//调用业务层查询分页结果,,返回值类型page类型
		Page<Standard> pageResponse = standardService.findStandardListPage(pageable);
		
		//从结果中封装数据,得到想要的结果
		//json对象对应的java的常用数据结构:map
//		Map<String,Object> resultMap = new HashMap<>();
//		resultMap.put("total",pageResponse.getTotalElements());
//		resultMap.put("rows", pageResponse.getContent());
//		//下面要利用struts2的json插件，将map转换为json对象，并写入响应
//		//将map压入root栈顶
//		ActionContext.getContext().getValueStack().push(resultMap);
		pushPageDataToValuaestackBoot(pageResponse);
		//返回json类型的结果集上
		return JSON;
	}
	
	
	@Action("standard_list")
	//显示手牌标准的下拉列表 
	public String list(){
		//调用业务层
		List<Standard> standardList = standardService.findStandardList();
		ActionContext.getContext().getValueStack().push(standardList);
		return JSON;
	}
}
