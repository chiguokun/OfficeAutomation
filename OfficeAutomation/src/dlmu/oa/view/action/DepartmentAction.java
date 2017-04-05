package dlmu.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.BaseAction;
import dlmu.oa.domain.Department;
import dlmu.oa.util.DepartmentUtil;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{
	
	private Long parentId;

	/* 列表 */
	public String list() throws Exception {
		//获取顶层
		List<Department> departmentList = null ;
		if(parentId == null){
			departmentList= departmentService.getTopList();
		}else{
			departmentList = departmentService.getChildList(parentId);
			Department parent = departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}
	/* 删除*/
	public String delete() throws Exception {
		departmentService.delete(model.getId());
		return "toList";
	}
	/* 添加页面 */
	public String addUI() throws Exception {
		//准备数据，让部门列表树状显示
		List<Department> topList = departmentService.getTopList();
		List<Department> departmentList= DepartmentUtil.getAllDepartmentsByTree(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}
	/* 添加 */
	public String add() throws Exception {
		model.setParent(departmentService.getById(parentId));
		departmentService.save(model);
		return "toList";
	}
	/* 修改页面 */
	public String editUI() throws Exception {
		//部门树状结构
		List<Department> topList = departmentService.getTopList();
		List<Department> departmentList= DepartmentUtil.getAllDepartmentsByTree(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		
		//数据回显
		Department department = departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		//回显上级部门
		if(department.getParent()!= null){
			parentId = department.getParent().getId();
		}
		return "saveUI";
	}
	/* 修改 */
	public String edit() throws Exception {
		Department department = departmentService.getById(model.getId());
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		departmentService.update(department);
		return "toList";
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	

}
