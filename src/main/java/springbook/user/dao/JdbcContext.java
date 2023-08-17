package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = dataSource.getConnection();

			ps = stmt.makePreparedStatement(conn);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}
	}

	public void executeSql(final String sql) throws SQLException {
		workWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps;
				ps = conn.prepareStatement(sql);
				return ps;
			}
		});
	}

	public void executeSqlValues(final String sql, Object... values) throws SQLException {
		workWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);

				for (int i = 0; i < values.length; i++) {
					ps.setObject(i + 1, values[i]);
				}
				//                ps.setString(1, user.getId());
				//                ps.setString(2, user.getName());
				//                ps.setString(3, user.getPassword());

				return ps;
			}
		});
	}

}
