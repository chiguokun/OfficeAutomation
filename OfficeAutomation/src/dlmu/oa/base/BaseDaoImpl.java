package dlmu.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dlmu.oa.cfg.Configuration;
import dlmu.oa.domain.PageBean;
import dlmu.oa.util.HqlHelper;

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
	
	@Override
	public PageBean getPageBean(int pageNum, HqlHelper hqlHelper) {
		//每页大小
		int pageSize = Configuration.getPageSize();
		//当前页
		int currentPage = pageNum;
		Query queryList = getSession().createQuery(hqlHelper.getQueryListHql());
		
		if(hqlHelper.getParameters() != null && hqlHelper.getParameters().size() > 0) {
			for(int i=0;i<hqlHelper.getParameters().size();i++){
				Object obj = hqlHelper.getParameters().get(i);
				if(obj instanceof Collection<?>&&hqlHelper.getParamName()!=null){ //如果参数为集合，说明会用到IN(:parametName);
					queryList.setParameterList(hqlHelper.getParamName(), (Collection<?>)obj);  
                }else if(obj instanceof Object[]&&hqlHelper.getParamName()!=null){  
                	queryList.setParameterList(hqlHelper.getParamName(), (Collection<?>)obj);  
                }else{  
                	queryList.setParameter(i, obj);  
                }  
				//queryList.setParameter(i, hqlHelper.getParameters().get(i));
			}
		}
		queryList.setFirstResult((pageNum-1) * pageSize); //开始位置
		queryList.setMaxResults(pageSize); //每页大小
		List recordList = queryList.list();
		
		Query queryCount = getSession().createQuery(hqlHelper.getQueryCountHql());
		if(hqlHelper.getParameters() != null && hqlHelper.getParameters().size() > 0) {
			for(int i=0;i<hqlHelper.getParameters().size();i++){
				Object obj = hqlHelper.getParameters().get(i);
				if(obj instanceof Collection<?>&&hqlHelper.getParamName()!=null){  
					queryCount.setParameterList(hqlHelper.getParamName(), (Collection<?>)obj);  
                }else if(obj instanceof Object[]&&hqlHelper.getParamName()!=null){  
                	queryCount.setParameterList(hqlHelper.getParamName(), (Collection<?>)obj);  
                }else{  
                	queryCount.setParameter(i, obj);  
                }  
				//queryCount.setParameter(i, hqlHelper.getParameters().get(i));
			}
		}
		Long recordCount = (Long)queryCount.uniqueResult();
		
		return new PageBean(currentPage, pageSize, recordList, recordCount.intValue());
	}

}
