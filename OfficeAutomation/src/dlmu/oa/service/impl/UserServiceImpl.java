package dlmu.oa.service.impl;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import dlmu.oa.base.BaseDaoImpl;
import dlmu.oa.domain.User;
import dlmu.oa.service.UserService;

@Service
public class UserServiceImpl extends BaseDaoImpl<User> implements UserService {

	@Override
	public User getByNameAndPassword(String name, String password) {
		
		return (User) getSession().createQuery(//
				"FROM User WHERE loginName=? AND password=?")//
				.setParameter(0, name)//
				.setParameter(1, DigestUtils.md5Hex(password))//
				.uniqueResult();
	}
	
}
