package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao extends UserDaoJdbc {

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		// N사의 DBMS의 Driver를 로드
		Class.forName("com.mysql.cj.jdbc.Driver");

		String jdbcUrl = "jdbc:mysql://localhost:3306/tobyspring?"
			+ "useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul";
		Connection conn = DriverManager.getConnection(jdbcUrl, "toby", "spring");

		return conn;
	}
}
