package dlmu.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.BaseAction;

@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends BaseAction{
	
	private String id ; //流程定义的 id
	private String key; //流程定义的key(name)
	private InputStream inputStream; //下载用的
	private File upload; //上传用的
	
	/*列表*/
	public String list() throws Exception {
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "list";
	}
	
	/**删除**/
	public String delete() throws Exception {
		key = URLDecoder.decode(key,"utf-8");
		processDefinitionService.deleteByKey(key);
		return "toList";
	}
	
	/**部署页面**/
	public String addUI() throws Exception {
		return "addUI";
	}
	
	/**部署**/
	public String add() throws Exception {
		
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(upload));
		try {
			processDefinitionService.deploy(zipInputStream);
		} finally{
			zipInputStream.close();
		}
		return "toList";
	}
	
	/**查看流程图**/
	public String downloadProcessImage() throws Exception {
		id = URLDecoder.decode(id,"utf-8");
		inputStream = processDefinitionService.getProcessImageResourceAsStream(id);
		return "downloadProcessImage";
	}
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
