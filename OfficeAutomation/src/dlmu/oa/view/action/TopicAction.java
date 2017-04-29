package dlmu.oa.view.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.BaseAction;
import dlmu.oa.domain.Forum;
import dlmu.oa.domain.Reply;
import dlmu.oa.domain.Topic;

@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic>{
	
	private long forumId;
	
	//显示单个主题（主帖 + 回帖）
	public String show() throws Exception {
		//准备数据
		Topic topic = topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		
		//准备数据（回帖）reply
		List<Reply> replyList = replyService.findByTopic(topic);
		ActionContext.getContext().put("replyList", replyList);
		
		return "show";
	}
	
	//发表新主题页面
	public String addUI() throws Exception {
		//准备数据 forum'
		Forum forum = forumService.getById(forumId); 
		ActionContext.getContext().put("forum", forum);
		return "addUI";
	}
	
	//发表新主题
	public String add() throws Exception {
		// 封装
		// >> 表单中的字段, 已经封装了 title, content, faceIcon
		//》》获取表单中没有体现出的字段
		model.setForum(forumService.getById(forumId));
		//》》当前可以直接获取的信息
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new java.util.Date());
		
		// >> 应放到业务方法中的一个其他设置
		// model.setType(type);
		// model.setReplyCount(replyCount);
		// model.setLastReply(lastReply);
		// model.setLastUpdateTime(lastUpdateTime);

		// 保存
		topicService.save(model);
		return "toShow";
	}

	public long getForumId() {
		return forumId;
	}

	public void setForumId(long forumId) {
		this.forumId = forumId;
	}
}
