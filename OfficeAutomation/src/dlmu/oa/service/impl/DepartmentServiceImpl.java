package dlmu.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dlmu.oa.base.BaseDaoImpl;
import dlmu.oa.domain.Department;
import dlmu.oa.service.DepartmentService;

@Service
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends BaseDaoImpl<Department> implements DepartmentService{

	@Override
	public List<Department> getTopList() {
		
		return getSession().createQuery(//
				"FROM Department d WHERE d.parent IS NULL ")//
				.list();
	}

	
	@Override
	public List<Department> getChildList(Long parentId) {
		return getSession().createQuery(//
				"FROM Department d WHERE d.parent.id=?")//
				.setParameter(0,parentId)//
				.list();
	}
	
	

}
