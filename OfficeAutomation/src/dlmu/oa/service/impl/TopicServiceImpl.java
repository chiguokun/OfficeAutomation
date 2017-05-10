package dlmu.oa.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import dlmu.oa.base.BaseDaoImpl;
import dlmu.oa.cfg.Configuration;
import dlmu.oa.domain.Forum;
import dlmu.oa.domain.PageBean;
import dlmu.oa.domain.Topic;
import dlmu.oa.service.TopicService;
import dlmu.oa.util.HqlHelper;

@Service
@SuppressWarnings("unchecked")
public class TopicServiceImpl extends BaseDaoImpl<Topic> implements TopicService {

	
	@Override
	@Deprecated
	public List<Topic> findByForum(Forum forum) {
		// 排序：所有置顶帖都在最上面，然后把最新状态的主题显示到前面
		return getSession().createQuery(//
				"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();
	}
	
	@Override
	public void save(Topic topic) {
		// 1，设置属性并保存
		// topic.setType(Topic.TYPE_NORMAL); // 默认为“普通”
		// topic.setReplyCount(0); // 默认为0
		// topic.setLastReply(null); // 默认为null
		topic.setLastUpdateTime(topic.getPostTime()); // 默认为主题的发表时间
		getSession().save(topic); // 保存

		// 2，维护相关的信息
		Forum forum = topic.getForum();
		forum.setTopicCount(forum.getTopicCount() + 1); // 主题数
		forum.setArticleCount(forum.getArticleCount() + 1); // 文章数（主题+回复）
		forum.setLastTopic(topic); // 最后发表的主题
		getSession().update(forum); // 更新
	}
	
	/**
	 * parameters顺序为：
	 * forum,viewType,orderBy,Type_DESC
	 */
	@Override
	@Deprecated
	public PageBean getPageBean(int pageNum, Map<Object,Object> parameters ) {
		//每页大小
			int pageSize = Configuration.getPageSize();
			//当前页
			int currentPage = pageNum;
			String queryListHQL = null;
		//数据处理 (viewType ,orderBy, Type_DESC)
			//查看主题类型viewType
			if((Integer)parameters.get("viewType")==0){ //viewType=0,查看所有的主题
				queryListHQL = "FROM Topic t WHERE t.forum=? ORDER BY ";
			}else{ //viewType=1 只看精华帖
				queryListHQL = "FROM Topic t WHERE t.forum=? AND t.type=1 ORDER BY ";
			}
			//排序方式参数OrderBy
			String orderByType = null;
			int orderBy = (Integer)parameters.get("orderBy");
			switch (orderBy) {      
			case 0: //默认排序(所有置顶帖在前面，并按最后更新时间降序排列)      
				orderByType = "(CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime ";
				break;	      
			case 1://1：只按最后更新时间排序                        
				orderByType = "t.lastUpdateTime ";
				break;
			case 2: //2： 只按主题发表时间排序                           
				orderByType = "t.postTime ";
				break;
			case 3: //3：只按回复数量排序                 
				orderByType = "t.replyCount ";
				break;
			}
			
			//正序反序参数
			int Type_DESC = (Integer)parameters.get("Type_DESC");
			String typeDESCString = null;
			if(Type_DESC == 0){
				typeDESCString = "DESC";
			}else{
				typeDESCString = "ASC";
			}
			
	//开始查询：
			//TODO sys
			System.out.println("----------------"+queryListHQL + orderByType + typeDESCString);
			List recordList = getSession().createQuery(//
					queryListHQL + orderByType + typeDESCString)//
					.setParameter(0,(Forum)parameters.get("forum"))//
					.setFirstResult((pageNum-1)*pageSize)//
					.setMaxResults(pageSize)//
					.list();
			//查询总记录数
			Long recordCount = (Long)getSession().createQuery(//
					"SELECT COUNT(*) "+queryListHQL + orderByType + typeDESCString)//
					.setParameter(0,(Forum)parameters.get("forum"))//
					.uniqueResult();
			
			return new PageBean(currentPage, pageSize, recordList, recordCount.intValue());
	}

	
	

	
}
