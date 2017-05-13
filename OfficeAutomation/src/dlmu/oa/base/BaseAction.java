package dlmu.oa.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
	
	/**
	 * 上传头像图片，并返回(部分路径)
	 * @param uploadTemp
	 * @param dirPath //图片上传的文件夹名称
	 * @return
	 */
	
	protected String saveHeadPicture(File uploadTemp,String dirPath) {
		//如果该用户已经传过头像图片文件，则先删除文件
		if(StringUtils.isNotBlank(getCurrentUser().getUploadPath())){
			User user = userService.getById(getCurrentUser().getId());
			String imagePathString = user.getUploadPath();  //因为getCurrentUser存储在Session中，内容得不到及时更新，不能直接用作为User对象获取Path。
			
			File gifFile = new File(imagePathString);
			if(gifFile.exists()&&gifFile.isFile()){ //如果已经存在则先删除
				gifFile.delete();
			}
		}
		//对即将上传的新图片文件进行处理
		String basePath = ServletActionContext.getServletContext().getRealPath("/");
		//>>如果文件不存在则创建
		File dir = new File(basePath + dirPath );
		if(!dir.exists()){
			dir.mkdirs(); //递归创建所有不存在的文件夹
		}
		String subPath = dirPath +UUID.randomUUID().toString()+".gif";
		String path = basePath + subPath ;  //文件真实存储的路径
		
		uploadTemp.renameTo(new File(path)); //移动文件从临时区到指定目录下
		
		return subPath;
	}
}
