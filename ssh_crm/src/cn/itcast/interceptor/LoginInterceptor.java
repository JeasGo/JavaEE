package cn.itcast.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/** 
 * @author jeas 
 * @date 2017年12月20日
 */
public class LoginInterceptor extends MethodFilterInterceptor{

	private static final long serialVersionUID = 7747352441094981152L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 获得session
				Map<String, Object> session = ActionContext.getContext().getSession();
				// 获得登录标识
				Object object = session.get("loginUser");
				
				// 判断登录标识是否存在
				if (object == null) {
					//若不存在,则没登录,重定向 到登录页面
					return "toLogin";
				}else {
					//存在 ,则放行
					return invocation.invoke();
				}
	}

	
}
