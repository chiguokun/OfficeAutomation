package dlmu.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import dlmu.oa.base.BaseDaoImpl;
import dlmu.oa.domain.Forum;
import dlmu.oa.domain.Topic;
import dlmu.oa.service.TopicService;

@Service
@SuppressWarnings("unchecked")
public class TopicServiceImpl extends BaseDaoImpl<Topic> implements TopicService {

	
	@Override
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

	
}
