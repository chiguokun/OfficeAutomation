package dlmu.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.ModelDrivenBaseAction;
import dlmu.oa.domain.Forum;
import dlmu.oa.domain.Topic;
import dlmu.oa.util.HqlHelper;

@Controller
@Scope("prototype")
public class ForumAction extends ModelDrivenBaseAction<Forum>{
	
	/**
	 * 0：表示查看所有主题
	 * 1：表示只看精华帖
	 */
	private int viewType =0;
	
	/**
	 * 0： 默认排序(所有置顶帖在前面，并按最后更新时间降序排列)      
	 * 1：只按最后更新时间排序                       
	 * 2： 只按主题发表时间排序
	 * 3：只按回复数量排序
	 */                  
	private int orderBy =0;
	
	/**
	 * false:降序 DESC
	 * true:升序 ASC
	 */
	private int ASC = 0;
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
		
		/*List<Topic> topicList= topicService.findByForum(forum);
		ActionContext.getContext().put("topicList", topicList);*/
		
		//准备分页处理过的数据
		HqlHelper hqlHelper = new HqlHelper(Topic.class,"t");
		hqlHelper.addCondition("t.forum=?", forum)
				.addCondition(viewType == 1, "t.type=?", Topic.TYPE_BEST)
				.addOrder(orderBy==0, "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", ASC)
				.addOrder(orderBy==0, "t.lastUpdateTime", ASC)
				.addOrder(orderBy==1, "t.lastUpdateTime", ASC)
				.addOrder(orderBy==2, "t.postTime", ASC)
				.addOrder(orderBy==3, "t.replyCount", ASC)   //以上选项用户生成Hql
				.buildPageBeanForStruts2(pageNum,topicService); //该选项用于分页
		
		
		
		/*//准备分页处理过的数据
		String queryListHQL = "FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC";
		PageBean pageBean = topicService.getPageBean(pageNum, queryListHQL, new Object[]{forum});
		ActionContext.getContext().getValueStack().push(pageBean);*/
		
		return "show";
	}
	
	private int pageNum =1;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public int getASC() {
		return ASC;
	}

	public void setASC(int aSC) {
		ASC = aSC;
	}

	
	

	

	
}
