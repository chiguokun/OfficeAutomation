package dlmu.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 部门
 * @author CGK
 *
 */
public class Department {
	
	/**
     * 部门编号
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;
    /**
     * 备注
     */
    private String description;
    
    /**
     * 与部门关联的多个用户实体
     */
    private Set<User> users = new HashSet<User>();

    /**
     * 与部门自关联的父（出）节点
     */
    private Department parent;

    /**
     * 与部门自关联的多个子（入）节点
     */
    private Set<Department> children = new HashSet<Department>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
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
	}

}
