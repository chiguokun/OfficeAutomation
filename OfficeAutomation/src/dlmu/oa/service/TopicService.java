package dlmu.oa.service;

import java.util.List;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.Forum;
import dlmu.oa.domain.Topic;

public interface TopicService extends BaseDao<Topic> {

	List<Topic> findByForum(Forum forum);

}
