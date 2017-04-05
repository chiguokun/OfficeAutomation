package dlmu.oa.service;

import java.util.List;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.Department;

public interface DepartmentService extends BaseDao<Department>{

	List<Department> getTopList();

	List<Department> getChildList(Long parentId);

	
}
