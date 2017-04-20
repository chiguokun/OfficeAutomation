package dlmu.oa.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户实体
 * 
 * @author CGK
 * 
 */
public class User implements Serializable{

	/**
	 * 用户编号
	 */
	private Long id;

	/**
	 * 与之关联的部门实体
	 */
	private Department department;

	/**
	 * 与之关联的多个岗位
	 */
	private Set<Role> roles = new HashSet<Role>();

	/**
	 * 用户名称
	 */
	private String name;

	/**
	 * 登陆名
	 */
	private String loginName;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话号码
	 */
	private String phoneNumber;

	/**
	 * 备注
	 */
	private String description;

	/**
	 * 判读本用户是否有指定名称的权限
	 * 
	 * @param privilegeName
	 * @return
	 */
	public boolean hasPrivilegeByName(String privilegeName) {
		// 特殊（超级）用户
		if (isAdmin()) {
			return true;
		}
		// 其他用户
		for (Role role : roles) {
			for (Privilege privilege : role.getPrivileges()) {
				if (privilege.getName().equals(privilegeName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判读本用户是否有指定url的权限
	 * 
	 * @param privilegeName
	 * @return
	 */
	public boolean hasPrivilegeByUrl(String privilegeUrl) {
		// 特殊（超级）用户
		if (isAdmin()) {
			return true;
		}
		
		// 如果以UI后缀结尾，就去掉UI后缀，以得到对应的权限（例如：addUI与add是同一个权限）
		if(privilegeUrl.endsWith("UI")){
			privilegeUrl = privilegeUrl.substring(0,privilegeUrl.length()-2);
		}
		
		// 其他用户
		//如果输入的url在数据库中没有,
		//则为不受控制的公共功能，所用用户都能使用，如"userAction_logout,homeAction_index"
		List<String> allPrivilegeUrl = (List<String>) ActionContext.getContext().getApplication().get("allPrivilegeUrl");
		if(!allPrivilegeUrl.contains(privilegeUrl)){
			return true;
		}
		else{
			// 如果是要控制的功能，则有权限才能使用
			for (Role role : roles) {
				for (Privilege privilege : role.getPrivileges()) {
					if (privilegeUrl.equals(privilege.getUrl())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//是否是超级用户
	private boolean isAdmin() {
		return "admin".equals(loginName);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
