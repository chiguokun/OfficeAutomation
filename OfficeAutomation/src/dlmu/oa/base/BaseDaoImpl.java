package dlmu.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

@Repository
@Transactional //可以继承给子类
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T>{
	
	@Resource
	private SessionFactory sessionFactory;
	protected Class<T> clazz;
	
	
	public BaseDaoImpl(){
	//Generic paradic 泛型
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz = (Class)pt.getActualTypeArguments()[0];
		//System.out.println("clazz ==" +clazz.getSimpleName());
	}

	@Override
	public void save(T entity) {
		
		getSession().save(entity);
	}

	/**
	 * 先获取实体对象，再删除实体对象
	 */
	@Override
	public void delete(Long id) {
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
		
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
		
	}

	@Override
	public T getById(Long id) {
		if(id==null){
			return null;
		}
		return (T)getSession().get(clazz,id);
	}

	@Override
	public List<T> getByIds(Long[] ids) {
		if(ids == null || ids.length ==0)
		{
			return java.util.Collections.EMPTY_LIST;
		}
		return getSession().createQuery(//
				"FROM "+clazz.getSimpleName()+ " WHERE id IN(:ids)")//
				.setParameterList("ids", ids).list();
	}

	@Override
	public List<T> findAll() {
		return getSession().createQuery(//
				"FROM "+clazz.getSimpleName())//
				.list();
	}
	
	/**
	 * 获取当前Session
	 * @return
	 */
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

}
