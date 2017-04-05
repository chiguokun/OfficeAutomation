package dlmu.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import dlmu.oa.service.DepartmentService;
import dlmu.oa.service.RoleService;
import dlmu.oa.service.UserService;


public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	@Resource
	protected UserService userService;
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;
	
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

}
