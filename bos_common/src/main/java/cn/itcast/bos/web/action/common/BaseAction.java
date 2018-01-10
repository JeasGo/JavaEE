package cn.itcast.bos.web.action.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

//通用action
@Namespace("/")//命名空间，访问路径
//@ParentPackage("struts-default")//父包
@ParentPackage("json-default")//父包
@Results({
	//json类型的结果集
	@Result(name=BaseAction.JSON,type=BaseAction.JSON)
})
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	//声明一个数据模型对象
	protected T model;
	@Override
	public T getModel() {
		return model;
	}
	
	//默认构造器
	public BaseAction() {
		//在默认构造器中初始化数据模型对象
		//获取子类的父类BaseAction<T>的类型
		//Type是类型的祖宗，class和ParameterizedType都是其子类型
		Type superType = this.getClass().getGenericSuperclass();
		//强转为泛型化类型
		ParameterizedType parameterizedType=(ParameterizedType)superType;
		//获取T泛型的具体类型
		Class<T> modelClass= (Class<T>) parameterizedType.getActualTypeArguments()[0];
		
		//实例化数据模型
		try {
			model=modelClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	//====常量
	//json常量
	public static final String JSON = "json";
	//重定向常量
	public static final String REDIRECT = "redirect";

	
	//====分页查询相关
	//属性驱动接收分页参数
	protected int page;
	protected int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//将分页结果重新组装，通过json插件转换为json，压入root栈顶
	public void pushPageDataToValuestackRoot(Page<T> pageResponse){
		//将数据重新组装
		//json对象对应的java的常用数据结构：map
		Map<String, Object> resultMap=new HashMap<>();
		//总记录数
		resultMap.put("total", pageResponse.getTotalElements());
		
		//当前页的数据列表
		resultMap.put("rows", pageResponse.getContent());
		
		//下面要利用struts2的json插件，将map转换为json对象，并写入响应
		//将map压入root栈顶
		ActionContext.getContext().getValueStack().push(resultMap);
	}
}
