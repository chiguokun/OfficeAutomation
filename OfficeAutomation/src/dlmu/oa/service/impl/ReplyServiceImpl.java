package dlmu.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import dlmu.oa.base.BaseDaoImpl;
import dlmu.oa.domain.Forum;
import dlmu.oa.domain.Reply;
import dlmu.oa.domain.Topic;
import dlmu.oa.service.ReplyService;

@Service
@SuppressWarnings("unchecked")
public class ReplyServiceImpl extends BaseDaoImpl<Reply> implements ReplyService {

	
	@Override
	public List<Reply> findByTopic(Topic topic) {
		return getSession().createQuery(//
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.list();
	}
	
	@Override
	public void save(Reply reply) {
		//保存DB
		getSession().save(reply);
		
		//维护相关信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();
		
		forum.setArticleCount(forum.getArticleCount() + 1); //版块文章数加一（主题+回复）
		topic.setReplyCount(topic.getReplyCount() + 1);//主题回复数+1
		topic.setLastReply(reply);
		topic.setLastUpdateTime(reply.getPostTime());
		
		getSession().update(forum);
		getSession().update(topic);
		
	}

}
