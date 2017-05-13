package dlmu.oa.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dlmu.oa.domain.User;
import dlmu.oa.service.ApplicationService;
import dlmu.oa.service.ApplicationTemplateService;
import dlmu.oa.service.DepartmentService;
import dlmu.oa.service.ForumService;
import dlmu.oa.service.PrivilegeService;
import dlmu.oa.service.ProcessDefinitionService;
import dlmu.oa.service.ReplyService;
import dlmu.oa.service.RoleService;
import dlmu.oa.service.TopicService;
import dlmu.oa.service.UserService;

public class BaseAction extends ActionSupport {
	
	@Resource
	protected UserService userService;
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected ForumService forumService;
	@Resource
	protected TopicService topicService;
	@Resource
	protected ReplyService replyService;
	@Resource
	protected ProcessDefinitionService processDefinitionService;
	@Resource
	protected ApplicationTemplateService applicationTemplateService;
	@Resource
	protected ApplicationService applicationService;

	
	/**
	 * 获取当前登录的用户
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get("user");
	}

	protected String saveUploadFile(File uploadTemp) {
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
		String basePath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload_file");
		String subPath = sdf.format(new Date());
		//>>如果文件不存在则创建
		File dir = new File(basePath + subPath);
		if(!dir.exists()){
			dir.mkdirs(); //递归创建所有不存在的文件夹
		}
		String path = basePath + subPath + UUID.randomUUID().toString();
		
		uploadTemp.renameTo(new File(path)); //移动文件从临时区到指定目录下
		return path;
	}
}
