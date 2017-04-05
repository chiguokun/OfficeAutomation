package dlmu.oa.view.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import dlmu.oa.domain.Role;
import dlmu.oa.service.RoleService;

@Controller
@Scope("prototype")
public class RoleAction extends ActionSupport implements ModelDriven<Role>{
	
	@Resource
	private RoleService roleService;
	
	/*private Long id;
	private String name;
	private String description;*/
	Role model = new Role();
	
	@Override
	public Role getModel() {
		return model;
	}
	
	public String list() throws Exception {	
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "list";
		
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {	
		roleService.delete(model.getId());
		return "toList";
		
	}
	/**
	 * 添加页面
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {			
		return "saveUI";
		
	}
	
	/**
	 * 添加
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {	
		/*Role role = new Role();
		role.setName(name);
		role.setDescription(description);*/
		
		roleService.save(model);
		
		return "toList";
		
	}
	
	/**
	 * 修改页面
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {	
		//根据id获取实体
		Role role =roleService.getById(model.getId());
		/*
		//给实体赋值
		setName(role.getName());
		setDescription(role.getDescription());*/
		
		//直接放到对象栈栈顶，直接获取栈顶元素
		ActionContext.getContext().getValueStack().push(role);
		return "saveUI";
		
	}
	public String edit() throws Exception {	
		Role role = roleService.getById(model.getId());
		/*Role role = new Role(); //这样写role中其他属性的关联关系就没了
		role.setId(id);*/
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		roleService.update(role);
		return "toList";
		
	}
	/*public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}*/


}
