package dlmu.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import dlmu.oa.base.BaseDaoImpl;
import dlmu.oa.domain.Privilege;
import dlmu.oa.domain.Role;
import dlmu.oa.service.PrivilegeService;

@Service
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl extends BaseDaoImpl<Privilege> implements PrivilegeService{

	
	@Override
	public List<Privilege> getTopPrivilege() {
		return getSession().createQuery(//
				"FROM Privilege p WHERE p.parent IS NULL")//
				.list();
	}
	/**
	 * 获取数据库Privilege实体中所有的权限的Url集合。
	 */
	@Override
	public List<Privilege> getAllPrivilegeUrl() {
		return getSession().createQuery(//
				"SELECT DISTINCT p.url FROM Privilege p WHERE p.url IS NOT NULL ")//
				.list();
	}

	
}
