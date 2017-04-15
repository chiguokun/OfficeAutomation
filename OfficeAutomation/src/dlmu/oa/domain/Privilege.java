package dlmu.oa.domain;

import java.util.*;

/**
 * 权限实体类
 */
public class Privilege {

    private Long id;

    private Set<Role> roles;

    private Privilege parent;

    private Set<Privilege> children;
   
    private String name;

    private String url; //功能请求链接
    
    private String icon; //图片链接
    
    public Privilege(){
    	
    }
    
    public Privilege(String name, String url, String icon,
			Privilege parent) {
    	this.name = name;
    	this.url = url;
    	this.icon = icon;
    	this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public Privilege getParent() {
		return parent;
	}

	public Set<Privilege> getChildren() {
		return children;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	




}