package dlmu.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.BaseAction;
import dlmu.oa.domain.Department;
import dlmu.oa.domain.Role;
import dlmu.oa.domain.User;
import dlmu.oa.util.DepartmentUtil;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	
	private Long departmentId;
	private Long[] roleIds;

	/* 列表 */
	public String list() throws Exception {
		List<User>  userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		return "list";
	}
	/* 删除*/
	public String delete() throws Exception {
		userService.delete(model.getId());
		return "toList";
	}
	/* 添加页面 */
	public String addUI() throws Exception {
		//准备数据departmentList
		List<Department> topList = departmentService.getTopList();
		List<Department> departmentList= DepartmentUtil.getAllDepartmentsByTree(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		//准备数据 roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "saveUI";
	}
	/* 添加 */
	public String add() throws Exception {
		//1.新建对象并设值
		Department department = departmentService.getById(departmentId);
		model.setDepartment(department);
		
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roleList));
		
		userService.save(model);
		return "toList";
	}
	/* 修改页面 */
	public String editUI() throws Exception {
		List<Department> topList = departmentService.getTopList();
		List<Department> departmentList= DepartmentUtil.getAllDepartmentsByTree(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		//准备数据 roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		
		//回显已选择的部门和岗位信息
		if (user.getDepartment() != null) {
			departmentId = user.getDepartment().getId();
		}
		if (user.getRoles().size() > 0) {
			roleIds = new Long[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}
		}
		
		return "saveUI";
	}
	/* 修改 */
	public String edit() throws Exception {
		
		User user = userService.getById(model.getId());
		
		user.setDescription(model.getDescription());
		user.setEmail(model.getEmail());
		user.setGender(model.getGender());
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setPhoneNumber(model.getPhoneNumber());
		//所属部门
		Department department = departmentService.getById(departmentId);
		user.setDepartment(department);
		//关联的岗位
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<Role>(roleList));
		//更新
		userService.update(user);
	
		return "toList";
	}
	/* 初始化密码 */
	public String initPassword() throws Exception {
		// 1，从数据库中取出原对象
		User user = userService.getById(model.getId());

		// 2，设置要修改的属性（要使用MD5摘要）
		String passwdMD5 = DigestUtils.md5Hex("1234");
		user.setPassword(passwdMD5);

		// 3，更新到数据库
		userService.update(user);

		return "toList";
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
	
	

}
 