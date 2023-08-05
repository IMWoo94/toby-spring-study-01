package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker{

    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        // D사의 독자적인 방식의 CONNECTION 연결 방식 구현
        Class.forName("com.mysql.cj.jdbc.Driver");

        String jdbcUrl = "jdbc:mysql://localhost:3306/tobyspring?" +
                "useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul";
        Connection conn = DriverManager.getConnection(jdbcUrl, "toby", "spring");
        return conn;
    }
}
