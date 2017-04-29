package dlmu.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.BaseAction;
import dlmu.oa.domain.Forum;
import dlmu.oa.domain.Topic;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum>{
	
	//版块列表
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	//显示单个版块(主题列表)
	public String show() throws Exception {
		//数据准备 forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum",forum);
		
		List<Topic> topicList= topicService.findByForum(forum);
		ActionContext.getContext().put("topicList", topicList);
		return "show";
	}

}
