package dlmu.oa.dao;

import java.util.List;

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

}
