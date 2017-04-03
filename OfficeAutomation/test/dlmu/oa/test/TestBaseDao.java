package dlmu.oa.test;

import org.junit.Test;

import dlmu.oa.dao.RoleDao;
import dlmu.oa.dao.UserDao;
import dlmu.oa.dao.impl.RoleDaoImpl;
import dlmu.oa.dao.impl.UserDaoImpl;

public class TestBaseDao {

	@Test
	public void test() {
		
		UserDao userDao = new UserDaoImpl();
		RoleDao roleDao = new RoleDaoImpl();
		
	}

}
