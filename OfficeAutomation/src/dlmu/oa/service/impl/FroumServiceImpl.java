package dlmu.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import dlmu.oa.base.BaseDaoImpl;
import dlmu.oa.domain.Forum;
import dlmu.oa.service.ForumService;

@Service
@SuppressWarnings("unchecked")
public class FroumServiceImpl extends BaseDaoImpl<Forum> implements ForumService{
	
	
	@Override
	public List<Forum> findAll() {
		return getSession().createQuery(//
				"FROM Forum f ORDER BY f.position ASC")//
				.list();
	}
	
	@Override
	public void save(Forum forum) {
		//保存DB，会生成Id值
		getSession().save(forum);
		
		forum.setPosition(forum.getId().intValue());

		// 因为是持久化状态，所以不需要调用update()方法
	}
	
	@Override
	public void moveUp(long id) {
		//先获取排序后，当前要移位记录的上一个记录的position信息
		Forum forum = getById(id);
		Forum other = (Forum)getSession().createQuery(//
				"FROM Forum f WHERE f.position<? ORDER BY f.position DESC")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();
		//如果是最顶上的记录，就不能上移
		if(other == null){
			return ;
		}
		
		//交换position值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);

		// 更新到数据库中
		// 因为是持久化状态，所以不需要调用update()方法。
	}
	

	@Override
	public void moveDown(long id) {
		//先获取排序后，当前要移位记录的上一个记录的position信息
				Forum forum = getById(id);
				Forum other = (Forum)getSession().createQuery(//
						"FROM Forum f WHERE f.position>? ORDER BY f.position ASC")//
						.setParameter(0, forum.getPosition())//
						.setFirstResult(0)//
						.setMaxResults(1)//
						.uniqueResult();
				//如果是最顶上的记录，就不能上移
				if(other == null){
					return ;
				}
				
				//交换position值
				int temp = forum.getPosition();
				forum.setPosition(other.getPosition());
				other.setPosition(temp);

				// 更新到数据库中
				// 因为是持久化状态，所以不需要调用update()方法。
	}

}
