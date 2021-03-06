package dlmu.oa.view.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.ModelDrivenBaseAction;
import dlmu.oa.domain.Department;
import dlmu.oa.domain.Role;
import dlmu.oa.domain.User;
import dlmu.oa.util.DepartmentUtil;
import dlmu.oa.util.HqlHelper;

@Controller
@Scope("prototype")
public class UserAction extends ModelDrivenBaseAction<User>{
	
	private Long departmentId;
	private Long[] roleIds;
	private int pageNum = 1;
	
	/**用户管理列表**/
	public String list() throws Exception {
		/*List<User>  userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);*/
		if(getCurrentUser().getLoginName().equals("admin")){ //如果为管理员，则获取所有部门用户
			HqlHelper hqlHelper = new HqlHelper(User.class);
			hqlHelper.buildPageBeanForStruts2(pageNum, userService);
		}
		else{ //如果不是管理员，只获取其所在部门及下属部门的用户信息
			User user = userService.getById(getCurrentUser().getId());
			Department department = user.getDepartment();
			//System.out.println("--------------"+department.getName()+"-----"+department.getChildren());
			Set<Department> deSet = new HashSet<Department>();
			//添加本部门，以及添加该用户所有下属部门。
			deSet.add(department);
			deSet.addAll(department.getChildren());
			HqlHelper hqlHelper = new HqlHelper(User.class,"u");
			hqlHelper.addCondition("deSet", "u.department IN (:deSet)", deSet)
					.buildPageBeanForStruts2(pageNum, userService);
			
		}
		
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
		
		//给用户添加上默认头像图片地址
		String basePath = ServletActionContext.getRequest().getContextPath(); //得到路径 /OfficeAutomation
		String path = basePath + "/style/images/defaultAvatar.gif"; //默认图片为ultAvatar.gif
		model.setShowSrcPath(path);
		
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
	/* 初始化密码 1234*/
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
	
	//登陆页面
	public String loginUI() throws Exception {	
		return "loginUI";
		
	}
	
	//
	public String login() throws Exception {	
		User user = userService.getByNameAndPassword(model.getLoginName(),model.getPassword());
		if(user == null)
		{
			addFieldError("login", "用户名或密码不正确");
			return "loginUI";
		}
		else{
			//正确就登陆用户
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
		
	}
	
	//注销用户
	public String logout() throws Exception {	
		ActionContext.getContext().getSession().remove("user");
		return "logout";
		
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
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	/* 列表 */

}
 