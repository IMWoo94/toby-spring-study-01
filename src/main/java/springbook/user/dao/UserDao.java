package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {

        Connection conn = dataSource.getConnection();

        PreparedStatement ps = conn.prepareStatement("insert into users (id, name, password) values(?,?,?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();

    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        Connection conn = dataSource.getConnection();

        PreparedStatement ps = conn.prepareStatement("select * from users where id = ?");

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;
        if(rs.next()){
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        conn.close();

        if(user == null) throw new EmptyResultDataAccessException(1);

        return user;

    }

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Connection conn = null;
        return conn;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {

        Connection conn = dataSource.getConnection();

        PreparedStatement ps = conn.prepareStatement("delete from users");

        ps.executeUpdate();

        ps.close();
        conn.close();

    }

    public int getCount() throws ClassNotFoundException, SQLException {
        Connection conn = dataSource.getConnection();

        PreparedStatement ps = conn.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        ps.close();
        conn.close();

        return count;
    }
}


