package dlmu.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.BaseAction;
import dlmu.oa.domain.Application;
import dlmu.oa.domain.ApplicationTemplate;
import dlmu.oa.domain.ApproveInfo;
import dlmu.oa.domain.TaskView;
import dlmu.oa.util.HqlHelper;


@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class FlowAction extends BaseAction {
	
	private File upload;//申请单上传
	private Long applicationTemplateId; //申请模板ID
	private String applicationStatus;
	private int pageNum =1;
	private String taskId;
	private String outcome; //选择的下一步处理流程的对象
	private InputStream inputStream;
	//ApproveInfo中的属性，因为没有继承ModelDiven接口，所以自己接收参数
		private String comment; 
		private Long applicationId;
		private boolean approval;
	
	
	// ================================== 申请人有关
	/** 起草申请（模板列表）**/
	public String applicationTemplateList() throws Exception{
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList",applicationTemplateList);
		return "applicationTemplateList";
	}
	
	/** 提交申请页面 */
	public String submitUI() throws Exception {
		return "submitUI";
	}
	
	/** 提交申请 */
	public String submit() throws Exception {
		//封装申请信息
		Application application = new Application();
		
		application.setApplicationTemplate(applicationTemplateService.getById(applicationTemplateId));
		application.setApplicant(getCurrentUser());
		application.setPath(saveUploadFile(upload));
		//以下属性在service中封装提交
		//application.setApproveInfos(approveInfos);
		//application.setStatus(status);
		//application.setTitle(title);
		//application.setApplyTime(new Date()); 在submit方法中处理
		
		applicationService.submit(application);
		return "toMyApplicationList"; // 成功后转到"我的申请查询"
	}
	
	/** 我的申请查询 */
	public String myApplicationList() throws Exception {
		//构建查询条件，并准备分页信息
		new HqlHelper(Application.class, "a")//
			.addCondition("a.applicant=?", getCurrentUser())//
			.addCondition(applicationTemplateId!=null, "a.applicationTemplate.id=?", applicationTemplateId)//
			.addCondition(StringUtils.isNotBlank(applicationStatus),"a.status=?",applicationStatus)//
			.addOrder("a.applyTime", 0)//
			.buildPageBeanForStruts2(pageNum,applicationService);

		// 准备数据
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList", applicationTemplateList);
			
		return "myApplicationList";
	}
	
	// ================================== 审批人有关
	/** 待我审批（我的任务列表） */
	public String myTaskList() throws Exception {
		List<TaskView> taskViewList = applicationService.getMyTaskViewList(getCurrentUser());
		ActionContext.getContext().put("taskViewList",taskViewList);
		return "myTaskList";
	}
	
	/** 审批处理页面 */
	public String approveUI() throws Exception {
		Set<String> outcomes = applicationService.getOutcomesByTaskId(taskId);
		ActionContext.getContext().put("outcomes", outcomes);
		return "approveUI";
	}
	
	/**申请的文档下载**/
	 public String download() throws Exception {
		 Application application = applicationService.getById(applicationId);
		 inputStream = new FileInputStream(application.getPath());
		 
		String fileName = URLEncoder.encode(application.getTitle(), "utf-8"); // 方法一
			// String fileName = new String(applicationTemplate.getName().getBytes("gbk"), "iso8859-1"); // 方法二
		ActionContext.getContext().put("fileName", fileName);
		return "download";
	 }
	

	/** 审批处理 */
	public String approve() throws Exception {
		// 封装
		ApproveInfo approveInfo = new ApproveInfo();

		approveInfo.setComment(comment);
		approveInfo.setApproval(approval);
		approveInfo.setApplication(applicationService.getById(applicationId));

		approveInfo.setApprover(getCurrentUser()); // 审批人，当前登录用户
		approveInfo.setApproveTime(new Date()); // 当前时间

		// 调用用业务方法（保存本次审批信息，并办理完任务，并维护申请的状态）
		applicationService.approve(approveInfo, taskId, outcome);

		return "toMyTaskList"; // 成功后转到待我审批页面
	}

	/** 查看流转记录 */
	public String approveHistory() throws Exception {
		Application application = applicationService.getById(applicationId);
		ActionContext.getContext().put("approveInfos", application.getApproveInfos());
		return "approveHistory";
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Long getApplicationTemplateId() {
		return applicationTemplateId;
	}

	public void setApplicationTemplateId(Long applicationTemplateId) {
		this.applicationTemplateId = applicationTemplateId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public String getOutcome() {
		return outcome;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	

	
}