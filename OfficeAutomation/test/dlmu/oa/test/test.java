package dlmu.oa.test;

public class test {

	/*@Override
	public int tableJudge(String userSolution, String extraString) {
		int iCode = 0;
		Statement stmt = null;
		Savepoint sp = null;
		Integer score = 0;
		boolean exist = false;
		Connection conn = null;

		// 该题目的extra信息 判分规则
		JSONObject jsonObj = JSON.parseObject(extraString);
		String extraType = jsonObj.getString("type");
		String tableAttribute = jsonObj.getString("attribute");
		String tableName = jsonObj.getString("tableName");

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);// 通知数据库开启事务(start transaction)
			stmt = conn.createStatement();
			exist = isTableOrViewExist(conn, tableName, "TABLE");
			System.out.println("before execute----" + exist);
			// 设置保存点，下面执行的在检查完成后都将回滚到此处的状态从而不改变数据库中的数据
			sp = conn.setSavepoint();
			// 根据具体不同的那一道题目进行不同的处理
			if (extraType.equals("create")) {
				iCode = stmt.executeUpdate(userSolution);// 执行创建语句
				exist = isTableOrViewExist(conn, tableName, "TABLE");
				if (exist) {
					score = isColumnExist(conn, tableName, tableAttribute) ? 10
							: 5;
					// System.out.println(score+"");
				} else {
					score = 0;
				}
			} else if (extraType.equals("alter")) {
				iCode = stmt.executeUpdate(userSolution);// 执行创建语句
				score = isColumnAccord(conn, tableName, tableAttribute) ? 10
						: 0;
			}

		} catch (java.sql.SQLException e) {
			System.out.println("语法不正确");
			score = 0;
			e.printStackTrace();

		} finally {
			try {
				conn.rollback(sp);
				exist = isTableOrViewExist(conn, tableName, "TABLE");
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return score;
	}*/
}
