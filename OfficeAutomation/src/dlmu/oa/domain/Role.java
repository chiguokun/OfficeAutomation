package dlmu.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 岗位
 * @author CGK
 *
 */
public class Role {
	
    /**
     * 岗位编号
     */
    private Long id;

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 备注
     */
    private String description;

    /**
     * 与之关联的多个用户
     */
    private Set<User> users = new HashSet<User>();

	public Long getId() {
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
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	
    
    

}
