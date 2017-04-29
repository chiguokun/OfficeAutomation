package dlmu.oa.service;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.Forum;

public interface ForumService extends BaseDao<Forum>{

	void moveUp(long id);

	void moveDown(long id);
	
	
}
