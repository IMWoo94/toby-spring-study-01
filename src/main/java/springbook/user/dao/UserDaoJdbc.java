package springbook.user.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.sqlservice.SqlService;

public class UserDaoJdbc implements UserDao {

	private SqlService sqlService;

	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}

	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	};

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void add(User user) {
		//    public void add(User user) throws DuplicateKeyException {
		//        try {
		this.jdbcTemplate.update(
			this.sqlService.getSql("userAdd"),
			user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(),
			user.getRecommend(), user.getEmail());
		//        }catch(DuplicateKeyException e){
		//            //throw new DuplicateUserIdException(e);
		//            throw e;
		//        }
	}

	@Override
	public User get(String id) {
		return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"), new Object[] {id}, this.userMapper);

	}

	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"), this.userMapper);
	}

	@Override
	public void deleteAll() {
		this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
	}

	@Override
	public int getCount() {
		//        return this.jdbcTemplate.query(new PreparedStatementCreator() {
		//            @Override
		//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		//                return con.prepareStatement("select count(*) from users");
		//            }
		//        }, new ResultSetExtractor<Integer>() {
		//            @Override
		//            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
		//                rs.next();
		//                return rs.getInt(1);
		//            }
		//        });
		return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGetCount"), Integer.class);
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(this.sqlService.getSql("userUpdate"),
			user.getName(), user.getPassword(), user.getLevel().intValue(),
			user.getLogin(), user.getRecommend(), user.getEmail(), user.getId());
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		return conn;
	}

	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		//        Connection c = null;
		//        PreparedStatement ps = null;
		//
		//        try{
		//            c = dataSource.getConnection();
		//
		//            ps = stmt.makePreparedStatement(c);
		//
		//            ps.executeUpdate();
		//        } catch (SQLException e) {
		//            throw e;
		//        } finally {
		//
		//            if (ps != null) {
		//                try {
		//                    ps.close();
		//                } catch (SQLException e) {
		//
		//                }
		//            }
		//            if (c != null) {
		//                try {
		//                    c.close();
		//                } catch (SQLException e) {
		//
		//                }
		//            }
		//        }
	}
}


