package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {

	@Override
	public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
		PreparedStatement ps;
		ps = conn.prepareStatement("delete from users");
		return ps;
	}
}
