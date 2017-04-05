package dlmu.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dlmu.oa.domain.Department;

public class DepartmentUtil {
	
	public static List<Department> getAllDepartmentsByTree(List<Department> topList)
	{
		List<Department> departmentList = new ArrayList<Department>();
		createDepartmentTree(topList, "┣", departmentList);
		return departmentList;
	}
	
	private static void createDepartmentTree(Collection<Department> topList,String prefix,List<Department> departmentList){
		for(Department top : topList)
		{
			Department copy = new Department();
			copy.setId(top.getId());
			copy.setName( prefix +top.getName());
			departmentList.add(copy);
			createDepartmentTree(top.getChildren(), "　"+prefix, departmentList);
		}
	}
}
