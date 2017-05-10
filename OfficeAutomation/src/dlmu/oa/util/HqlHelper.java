package dlmu.oa.util;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;

import dlmu.oa.base.BaseDao;
import dlmu.oa.domain.PageBean;

public class HqlHelper {
	
	private String fromClause; //From子句，必须
	private String whereClause = ""; //Where子句，可选
	private String orderByClause = ""; //OrderBy子句，可选
	
	private List<Object> parameters = new ArrayList<Object>();
	
	/**
	 * 生产from子句，默认别名为'o';
	 * @param clazz
	 */
	
	public HqlHelper(Class clazz){
		this.fromClause = "FROM " + clazz.getSimpleName() + " o";
	}
	
	/**
	 * 生产from子句，用给定的参数作为别名
	 * @param clazz
	 * @param alias 指定别名的参数
	 */
	public HqlHelper(Class clazz, String alias){
		this.fromClause = "FROM " + clazz.getSimpleName() + " " +alias;
	}
	
	/**
	 * 拼接WHERE子句
	 * @param condition 判断条件
	 * @param params 判断参数与'?'对应
	 * @return
	 */
	
	public HqlHelper addCondition(String condition, Object... params){
		if(whereClause.length() == 0){
			whereClause = " WHERE " + condition;  
		}else{
			whereClause +=" AND " + condition;
		}
		
		if(params !=null && params.length>0){
			for(Object obj: params){
				parameters.add(obj);
			}
		}
		return this;
	}
	
	/**
	 * 如果满足第一个条件，则拼接WHERE子句
	 * @param append
	 * @param condition
	 * @param params
	 * @return
	 */
	
	public HqlHelper addCondition(boolean append,String condition, Object... params){
		if(append){
			addCondition(condition, params);
		}
		return this;
	}
	
	/**
	 * 拼接OrderBy子句
	 * 
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(String propertyName, int isASC){
		if(orderByClause.length() == 0){
			orderByClause = " ORDER BY " + propertyName + (isASC ==1 ? " ASC":" DESC");
		}else {
			orderByClause += ", " + propertyName + (isASC==1? " ASC":" DESC");
		}
		return this;
	}
	
	/**
	 * 如果第1个参数为true，则 拼接OrderBy子句
	 * @param append
	 * @param propertyName
	 * @param isASC
	 * @return
	 */
	public HqlHelper addOrder(boolean append,String propertyName, int isASC){
		if(append){
			addOrder(propertyName, isASC);
		}
		return this;
	}
	
	/**
	 * 获取生成的查询数据列表的HQL语句
	 * @return
	 */
	
	public String getQueryListHql(){
		return fromClause + whereClause + orderByClause;
	}
	
	/**
	 * 获取生成的查询数据总条数的HQL语句
	 * @return
	 */
	public String getQueryCountHql(){
		return "SELECT COUNT(*) "+ fromClause + whereClause ;
	}

	/**
	 * 查询分页信息，并将其放入栈顶
	 * @param pageNum
	 * @param service
	 * @return
	 */
	public HqlHelper buildPageBeanForStruts2(int pageNum, BaseDao<?> service) {
		PageBean pageBean = service.getPageBean(pageNum, this);
		ActionContext.getContext().getValueStack().push(pageBean);
		return this;
	}
	
	public String getFromClause() {
		return fromClause;
	}

	public void setFromClause(String fromClause) {
		this.fromClause = fromClause;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}
	
	

	
}
