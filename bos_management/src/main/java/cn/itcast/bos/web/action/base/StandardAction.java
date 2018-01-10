package cn.itcast.bos.web.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.components.ActionComponent;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import cn.itcast.bos.web.action.common.BaseAction;
//收派标准
@Controller//bean组件
@Scope("prototype")//多例
//@Namespace("/")//命名空间，访问路径
////@ParentPackage("struts-default")//父包
//@ParentPackage("json-default")//父包
public class StandardAction extends BaseAction<Standard>{
	
	//声明一个数据模型对象
//	private Standard standard=new Standard();
//	@Override
//	public Standard getModel() {
//		return standard;
//	}
	//注入业务层
	@Autowired
	private StandardService standardService;

	//添加
	@Action(value="standard_add"//action访问的名字
		,results={
//				@Result(name="success",type="redirect",location="/pages/base/standard.html")
//				@Result(name=SUCCESS,type="redirect",location="/pages/base/standard.html")
//				@Result(type="redirect",location="/pages/base/standard.html")
				@Result(type=REDIRECT,location="/pages/base/standard.html")
		}	
			)
	public String add(){
		//调用业务层保存
		standardService.saveStandard(model);
		
		//代码级别的权限控制-伪代码
		Subject subject = SecurityUtils.getSubject();
		//==认证
		if(subject.isAuthenticated()){
			//认证才能执行
			System.out.println("我是代码。。。。");
		}
		
		//授权
		//布尔值判断
		//是否有某角色权限
		if(subject.hasRole("aa")){
			System.out.println("我是代码。。。。");
		}
		//功能权限
		if(subject.isPermitted("bb")){
			System.out.println("我是代码。。。。");
		}
		//异常判断
		//角色权限
		try {
			subject.checkRole("aa");
			System.out.println("我是代码。。。。");
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		//功能权限
		try {
			subject.checkPermission("bb");;
			System.out.println("我是代码。。。。");
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		
		
		//跳转到列表页面
		return SUCCESS;
	}
	
	
	//属性驱动接收分页参数
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}



	//分页列表查询
//	@Action(value="standard_listPage",results={
//			@Result(name="json",type="json")
//			@Result(name=JSON,type=JSON)
//	})
	@Action("standard_listPage")
	public String listPage(){
		//这里选择技术方案SpringDatajpa
		
		//创建一个请求的分页bean对象：用来封装分页参数
		//参数1：当前页码,zero-based page index,页面是从0开始的索引。必须页码-1
		//参数2：每页最大记录数
		Pageable pageable=new PageRequest(page-1, rows);
		//调用业务层查询分页结果：响应的分页bean对象-结果
		Page<Standard> pageResponse= standardService.findStandardListPage(pageable);
		
		//从结果中重新封装数据，得到想要的结果
		//json对象对应的java的常用数据结构：map
		Map<String, Object> resultMap=new HashMap<>();
		//总记录数
		resultMap.put("total", pageResponse.getTotalElements());
		
		//当前页的数据列表
		resultMap.put("rows", pageResponse.getContent());
		
		//下面要利用struts2的json插件，将map转换为json对象，并写入响应
		//将map压入root栈顶
		ActionContext.getContext().getValueStack().push(resultMap);
		
		//返回json类型的结果集上
//		return "json";
		return JSON;
		
	}
	
	//列出所有的标准列表
	/*@Action(value="standard_list",results={
//			@Result(name="json",type="json")
			@Result(name=JSON,type=JSON)
	})*/
	@Action("standard_list")
	public String list(){
		//调用业务层
		List<Standard> standardList= standardService.findStandardList();
		//目标：要json数组--java一般使用list来转
		//将list压入root栈顶
		ActionContext.getContext().getValueStack().push(standardList);
		//返回json类型的结果集上
//		return "json";
		return JSON;
	}

}
