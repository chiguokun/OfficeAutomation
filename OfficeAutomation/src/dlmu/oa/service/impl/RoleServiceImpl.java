package dlmu.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dlmu.oa.dao.RoleDao;
import dlmu.oa.domain.Role;
import dlmu.oa.service.RoleService;
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleDao roleDao;
	/**
	 * 列表，查询所有
	 */
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}
	@Override
	public void delete(Long id) {
		roleDao.delete(id);
	}
	@Override
	public void save(Role role) {
		roleDao.save(role);
	}
	@Override
	public Role getById(Long id) {
		return roleDao.getById(id);
	}
	@Override
	public void update(Role role) {
		roleDao.update(role);
	}
	
	

}
