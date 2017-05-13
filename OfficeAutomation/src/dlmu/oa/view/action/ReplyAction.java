package dlmu.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.ModelDrivenBaseAction;
import dlmu.oa.domain.Reply;
import dlmu.oa.domain.Topic;

@Controller
@Scope("prototype")
public class ReplyAction extends ModelDrivenBaseAction<Reply>{
	
	private Long topicId;
	//发表新回复页面
	public String addUI() throws Exception {
		//准备数据 topic
		Topic topic = topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}
	
	//发表新回复
	public String add() throws Exception {
		//封装（已经封装了title，content，faceInco）
		/*model.setContent(content);
		model.setTitle(title);
		model.setFaceIcon(faceIcon);*/
		
		model.setTopic(topicService.getById(topicId));
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		//保存
		replyService.save(model);
		
		return "toTopicShow"; //转到新回复所属主题的显示页面
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

}
