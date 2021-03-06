package dlmu.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.ModelDrivenBaseAction;
import dlmu.oa.domain.ApplicationTemplate;
/**
 * 申请模板
 * @author CGK
 *
 */
@Controller
@Scope("prototype")
public class ApplicationTemplateAction extends ModelDrivenBaseAction<ApplicationTemplate>{
	
	private File upload; //上传用的
	private InputStream inputStream; //下载用的
	
	/**列表**/ 
	public String list() throws Exception{
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList",applicationTemplateList);
		return "list";
	}
	
	/**删除**/
	public String delete() throws Exception{
		applicationTemplateService.delete(model.getId());
		return "toList";
	}
	
	/**添加页面**/
	public String addUI() throws Exception{
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "saveUI";
	}
	
	/**添加**/
	public String add() throws Exception{
		String path = saveUploadFile(upload);
		model.setPath(path);
		applicationTemplateService.save(model);
		return "toList";
	}

	
	
	/**修改页面**/
	public String editUI() throws Exception{
		//准备数据
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		
		//准备回显数据
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(applicationTemplate);
		
		return "saveUI";
	}
	
	/**修改**/
	public String edit() throws Exception{
		//获取原数据
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		//修改数据
		applicationTemplate.setName(model.getName());
		applicationTemplate.setProcessDefinitionKey(model.getProcessDefinitionKey());
		if(upload != null){  //说明用户修改了文件的上传地址
			//删除老文件
			File file = new File(applicationTemplate.getPath());
			if(file.exists()){
				
				file.delete();
			}
			//获取新修改的文件地址
			String path = saveUploadFile(upload); 
			applicationTemplate.setPath(path);
		}
		
		//更新到数据库
		applicationTemplateService.save(applicationTemplate);
		return "toList";
	}
	
	/**下载**/
	public String download() throws Exception{
		// 准备下载的资源
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		inputStream = new FileInputStream(applicationTemplate.getPath());

		// 准备文件名（解决乱码问题）
		String fileName = URLEncoder.encode(applicationTemplate.getName(), "utf-8"); // 方法一
		// String fileName = new String(applicationTemplate.getName().getBytes("gbk"), "iso8859-1"); // 方法二
		ActionContext.getContext().put("fileName", fileName);

		return "download";
		
	}
	
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	

}
