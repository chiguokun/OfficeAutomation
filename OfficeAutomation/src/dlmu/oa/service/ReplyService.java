package dlmu.oa.service;

import java.util.List;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.Reply;
import dlmu.oa.domain.Topic;

public interface ReplyService extends BaseDao<Reply> {

	List<Reply> findByTopic(Topic topic);

}
