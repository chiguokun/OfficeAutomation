package dlmu.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import dlmu.oa.domain.User;
import dlmu.oa.service.DepartmentService;
import dlmu.oa.service.ForumService;
import dlmu.oa.service.PrivilegeService;
import dlmu.oa.service.ReplyService;
import dlmu.oa.service.RoleService;
import dlmu.oa.service.TopicService;
import dlmu.oa.service.UserService;


public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	@Resource
	protected UserService userService;
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected ForumService forumService;
	@Resource
	protected TopicService topicService;
	@Resource
	protected ReplyService replyService;
	
	protected T model;
	
	public BaseAction(){
		
		//通过反射生成model实例
		try {
			ParameterizedType pType = (ParameterizedType)this.getClass().getGenericSuperclass();
			Class clazz = (Class)pType.getActualTypeArguments()[0];
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public T getModel() {
		return model;
	}
	
	/**
	 * 获取当前登录的用户
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get("user");
	}


}
