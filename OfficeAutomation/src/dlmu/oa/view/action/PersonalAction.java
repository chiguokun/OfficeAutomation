package dlmu.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.ModelDrivenBaseAction;
import dlmu.oa.domain.User;

/**
 * 个人信息设置
 * @author CGK
 *
 */

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class PersonalAction extends ModelDrivenBaseAction<User>{
	
	private File headImageUpload;
	private String imagePath; //真正用于页面显示的链接路径
	private String oldPassword;
	private String newPassword;//新密码
	
	public String editImageUI() throws Exception{
		System.out.println(ServletActionContext.getServletContext().getRealPath("/"));
		User user = userService.getById(getCurrentUser().getId());
		ActionContext.getContext().getValueStack().push(user);
		return "editImageUI";
	}
	
	public String editImage() throws Exception {
		User user = userService.getById(getCurrentUser().getId());
		String subPath = saveHeadPicture(headImageUpload,"/headImage/");
		user.setUploadPath(ServletActionContext.getServletContext().getRealPath("/")+subPath);
			String showSrcPath = ServletActionContext.getRequest().getScheme()//http:
					+"://"+ServletActionContext.getRequest().getServerName()+":"//http://localhost:
					+ServletActionContext.getRequest().getServerPort()//http://localhost:8080
					+ServletActionContext.getRequest().getContextPath() //http://localhost:8080/OfficeAutomation
					+subPath;////http://localhost:8080/OfficeAutomation/headImage/xxx.gif
		user.setShowSrcPath(showSrcPath);
		userService.update(user);
		
		return "toEditImageUI";
	}
	
	public String editPasswordUI() throws Exception{
		
		return "editPasswordUI";
	}
	
	public String editPassword() throws Exception {
		User user = userService.getByNameAndPassword(getCurrentUser().getLoginName(), oldPassword);
		
		if(user != null){//如果user不为空，说明原先密码输入正确
			user.setPassword(newPassword);
			System.out.println(oldPassword+"----------->"+newPassword+"-----》"+user.getPassword()+"---->"+user.getLoginName());
			userService.updatePassword(user);
		}else {
			addFieldError("oldPassword", "原密码输入不正确");
		}
		
		return "editPasswordUI";
	}
	

	public File getHeadImageUpload() {
		return headImageUpload;
	}

	public void setHeadImageUpload(File headImageUpload) {
		this.headImageUpload = headImageUpload;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	


	
	
	

}
