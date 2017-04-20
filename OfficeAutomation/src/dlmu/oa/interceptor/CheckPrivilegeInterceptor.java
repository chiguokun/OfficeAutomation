package dlmu.oa.interceptor;

import org.aopalliance.intercept.Invocation;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import dlmu.oa.domain.User;

public class CheckPrivilegeInterceptor implements Interceptor {


	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		User user = (User) ActionContext.getContext().getSession().get("user");
		
		//TODO :这里加上nameSpace是不是多余了，直接用actionName判读是不是更好
		// 获取当前访问的URL，并去掉当前应用程序的前缀（也就是 namespaceName + actionName ）
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String privilegeUrl = null;
		
		if (namespace.endsWith("/")) {
			privilegeUrl = namespace + actionName;
		} else {
			privilegeUrl = namespace + "/" + actionName;
		}
		
		String [] urlStrings =privilegeUrl.split("/");
		privilegeUrl =urlStrings[urlStrings.length-1];
		
		//如果未登录
		if(user==null){
			//如果是去登陆，就放行
			if(privilegeUrl.startsWith("userAction_login")){
				//System.out.println("-----放行");
				return invocation.invoke();
			}
			else{
				//如果不是去登陆，就跳转到登陆页面
				return "loginUI";
			}
		}
		else{
		//如果已登陆用户，就判断权限
			if(user.hasPrivilegeByUrl(privilegeUrl)){
				//如果有权限就放行
				return invocation.invoke();
			}else{
				//如果没有权限，就跳转到提示页面
				return "noPrivilege";
			}
		}
		
	}
	

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

}
