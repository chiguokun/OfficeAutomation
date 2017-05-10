package dlmu.oa.test;

import org.junit.Test;

import dlmu.oa.domain.Forum;
import dlmu.oa.domain.Topic;
import dlmu.oa.util.HqlHelper;

public class testHqlHelper {
	

	/**
	 * 0：表示查看所有主题
	 * 1：表示只看精华帖
	 */
	private int viewType =1;
	
	/**
	 * 0： 默认排序(所有置顶帖在前面，并按最后更新时间降序排列)      
	 * 1：只按最后更新时间排序                       
	 * 2： 只按主题发表时间排序
	 * 3：只按回复数量排序
	 */                  
	private int orderBy =3;
	
	
	/**
	 * false:降序 DESC
	 * true:升序 ASC
	 */
	private int isASC = 0;
	
	private Forum forum = new Forum();
	
	@Test
	public void testHql(){
		
		HqlHelper hqlHelper = new HqlHelper(Topic.class, "t")
		.addCondition("t.forum=?", forum)
		.addCondition(viewType == 1, "t.type=?", Topic.TYPE_BEST)
		.addOrder(orderBy==0, " (CASE t.type WHEN 2 THEN 2 ELSE 0 END)", isASC)
		.addOrder(orderBy==0, " t.lastUpdateTime", isASC)
		.addOrder(orderBy==1, " t.lastUpdateTime", isASC)
		.addOrder(orderBy==2, " t.postTime", isASC)
		.addOrder(orderBy==3, " t.replyCount", isASC);
		
		System.out.println(hqlHelper.getQueryListHql());
		System.out.println(hqlHelper.getQueryCountHql());
		System.out.println(hqlHelper.getParameters().size());
	}

}
