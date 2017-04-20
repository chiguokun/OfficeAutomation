package dlmu.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.Privilege;
import dlmu.oa.domain.Role;

public interface PrivilegeService extends BaseDao<Privilege>{

	List<Privilege> getTopPrivilege();

	List<Privilege> getAllPrivilegeUrl();
	
	
}
