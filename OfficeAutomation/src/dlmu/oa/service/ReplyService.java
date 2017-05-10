package dlmu.oa.service;

import java.util.List;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.PageBean;
import dlmu.oa.domain.Reply;
import dlmu.oa.domain.Topic;

public interface ReplyService extends BaseDao<Reply> {

	@Deprecated
	List<Reply> findByTopic(Topic topic);
	
	//获取分页后的记录和数据
	@Deprecated
	PageBean getPageBean(int pageNum, Topic topic);

}
