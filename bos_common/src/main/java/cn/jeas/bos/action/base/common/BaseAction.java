package cn.jeas.bos.action.base.common;

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

@Namespace("/")
@ParentPackage("json-default")
@Results({@Result(name=BaseAction.JSON,type=BaseAction.JSON)})
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	
	private static final long serialVersionUID = 7636299422935954926L;
	
	//父类中定义常量
	public static final String JSON = "json";
	public static final String REDIRECT = "redirect";
	
	Map<String, Object> resultMap = new HashMap<>();
	//声明一个数据模型变量   变量的类型为泛型
	protected T model;
	@Override
	public T getModel() {
		return model;
	}

	//默认构造器
	public BaseAction(){
	//在默认构造器中初始化数据模型对象
		Type superType = this.getClass().getGenericSuperclass();
		//强转为泛型化类型
		ParameterizedType parameterizedType = (ParameterizedType) superType;
		//获取T泛型的具体类型
		@SuppressWarnings("unchecked")
		Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		
		
		try {
			model = modelClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//将分页查询代码重新组装,通过json插件转换为json,压入栈顶
	public void pushPageDataToValuaestackBoot(Page<T> pageResponse){
		resultMap.put("total", pageResponse.getTotalElements());
		resultMap.put("rows", pageResponse.getContent());
		
		ActionContext.getContext().getValueStack().push(resultMap);
	}
	public void pushJsonDataToValuaestackBoot(Object obj){
		resultMap.put("result", obj);
		ActionContext.getContext().getValueStack().push(resultMap);
	}
}
