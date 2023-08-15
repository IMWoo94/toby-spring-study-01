package springbook.user.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;
import springbook.user.exception.DuplicateUserIdException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJdbc implements UserDao{

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };


    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(User user){
//    public void add(User user) throws DuplicateKeyException {
//        try {
            this.jdbcTemplate.update("insert into users (id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
//        }catch(DuplicateKeyException e){
//            //throw new DuplicateUserIdException(e);
//            throw e;
//        }
    }

    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",new Object[] {id}, this.userMapper);

    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        return conn;
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
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
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException{
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


