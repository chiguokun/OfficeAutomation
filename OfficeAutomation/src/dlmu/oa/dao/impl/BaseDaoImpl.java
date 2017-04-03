package dlmu.oa.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dlmu.oa.dao.BaseDao;

@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T>{
	
	@Resource
	private SessionFactory sessionFactory;
	protected Class<T> clazz;
	
	
	public BaseDaoImpl(){
	//Generic paradic 泛型
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz = (Class)pt.getActualTypeArguments()[0];
		System.out.println("clazz ==" +clazz.getSimpleName());
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
		// TODO Auto-generated method stub
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
		
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		getSession().update(entity);
		
	}

	@Override
	public T getById(Long id) {
		// TODO Auto-generated method stub
		return (T)getSession().get(clazz,id);
	}

	@Override
	public List<T> getByIds(Long[] ids) {
		// TODO Auto-generated method stub
		if(ids == null || ids.length ==0)
		{
			return null;
		}
		return getSession().createQuery(//
				"FROM "+clazz.getSimpleName()+ " WHERE id IN(:ids)")//
				.setParameterList("ids", ids).list();
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
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
