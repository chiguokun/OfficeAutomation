package dlmu.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dlmu.oa.domain.Role;


public interface RoleService {
	
	/**
	 *查询岗位列表
	 * @return
	 */
	List<Role> findAll();

	void delete(Long id);

	void save(Role role);

	Role getById(Long id);

	void update(Role role);
	
	

}
