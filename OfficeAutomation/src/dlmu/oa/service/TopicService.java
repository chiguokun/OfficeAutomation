package dlmu.oa.service;

import java.util.List;
import java.util.Map;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.Forum;
import dlmu.oa.domain.PageBean;
import dlmu.oa.domain.Topic;
import dlmu.oa.util.HqlHelper;

public interface TopicService extends BaseDao<Topic> {
	
	@Deprecated
	List<Topic> findByForum(Forum forum);

	/*获取分页处理后的数据*/
	@Deprecated
	PageBean getPageBean(int pageNum, Map<Object,Object> paraments);
	
	PageBean getPageBean(int pageNum, HqlHelper hqlHelper);

}
