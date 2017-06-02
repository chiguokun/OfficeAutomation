package dlmu.oa.service;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.User;

public interface UserService extends BaseDao<User>{

	User getByNameAndPassword(String name, String password);

	void updatePassword(User user);
	

}
