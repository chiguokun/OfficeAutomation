package dlmu.oa.base;

import java.util.List;

import dlmu.oa.domain.PageBean;
import dlmu.oa.util.HqlHelper;

public interface BaseDao<T> {
	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * 删除指定id的实体
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 更新单个实体
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 查询单个实体
	 * @param id
	 * @return
	 */
	public T getById(Long id);
	
	/**
	 * 查询实体集合
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(Long[] ids);
	
	/**
	 * 查询所有实体
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 公共查询分页信息的方法
	 * @param pageNum
	 * @param queryListHQL
	 * 			查询数据列表的HQL ,如果前面加上"SELECT COUNT(*) "就变成了查询总数量的HQL
	 * 
	 * @param parameters
	 * 	      参数类别，要求与HQL中”？“的顺序一一对应。
	 * @return
	 */
	public PageBean getPageBean(int pageNum, HqlHelper hqlHelper);

}
