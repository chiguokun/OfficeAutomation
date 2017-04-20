package dlmu.oa.view.action;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import dlmu.oa.base.BaseAction;
import dlmu.oa.domain.Privilege;
import dlmu.oa.domain.Role;
import dlmu.oa.service.PrivilegeService;
import dlmu.oa.service.RoleService;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	
	//选择数据存储，和已选数据回显
	private Long[] privilegeIds;
	
	/**
	 * 岗位列表
	 * @return
	 * @throws Exception
	 */
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
	
		roleService.save(model);
		
		return "toList";
		
	}
	
	/**
	 * 修改岗位页面
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
	
	/**
	 * 修改岗位信息
	 * @return String
	 * @throws Exception
	 */
	public String edit() throws Exception {	
		Role role = roleService.getById(model.getId());
		/*Role role = new Role(); //这样写role中其他属性的关联关系就没了
		role.setId(id);*/
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		roleService.update(role);
		return "toList";
		
	}

	/**
	 * 设置权限页面
	 * @return
	 * @throws Exception
	 */
	public String setPrivilegeUI() throws Exception {	
		//数据准备，获取顶层权限
		List<Privilege> topPrivilegeList = privilegeService.getTopPrivilege();
		//直接放到对象栈中的Map
		ActionContext.getContext().put("topPrivilegeList", topPrivilegeList);
		
		Role role = roleService.getById(model.getId());
		//放入对象栈的栈顶
		ActionContext.getContext().getValueStack().push(role);
		
		//初始化privilegeIds ，并回显已选择的权限
		privilegeIds = new Long[role.getPrivileges().size()];
		int index =0;
		for(Privilege privilege : role.getPrivileges())
		{
			privilegeIds[index++] = privilege.getId();
		}
		
		return "setPrivilegeUI";
		
	}
	
	/**
	 * 修改岗位信息
	 * @return String
	 * @throws Exception
	 */
	public String setPrivilege() throws Exception {	
		//获取选中的权限对象
		Role role = roleService.getById(model.getId());
		List<Privilege> privilegeList =privilegeService.getByIds(privilegeIds);
		//保存新选择的权限信息
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		
		//更新
		roleService.update(role);
		
		return "toList";
		
	}
	
	
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	


}
