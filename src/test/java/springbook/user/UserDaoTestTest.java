package springbook.user;

import com.mysql.cj.jdbc.exceptions.SQLError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.dao.UserDao;
import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.User;
import springbook.user.exception.DuplicateUserIdException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
class UserDaoTestTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserDao dao;

    @Autowired
    private DataSource dataSource;

    private User user1 = null;
    private User user2 = null;
    private User user3 = null;

    @BeforeEach
    public void setUp(){
        //ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(this.context);
        System.out.println(this);
        //dao = context.getBean("userDao", UserDao.class);

/*        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:3306/tobyspring?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul","toby","spring",true);
        dao.setDataSource(dataSource);*/
        user1 = new User("gyumee","박성철","springno1");
        user2 = new User("leegw700","이길원","springno2");
        user3 = new User("bumjin","박범진","springno3");
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException{

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));dao.deleteAll();

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User searchUser1 = new User();
        searchUser1 = dao.get(user1.getId());

        assertThat(searchUser1.getName(), is(user1.getName()));
        assertThat(searchUser1.getPassword(), is(user1.getPassword()));

        User searchUser2 = new User();
        searchUser2 = dao.get(user2.getId());

        assertThat(searchUser2.getName(), is(user2.getName()));
        assertThat(searchUser2.getPassword(), is(user2.getPassword()));

    }

    @Test
    public void count() throws SQLException, ClassNotFoundException{

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));


    }

    @Test
    public void getUserFailure() throws SQLException, ClassNotFoundException{
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        assertThrows(EmptyResultDataAccessException.class, () -> {
            dao.get("unknown_id");
        });

    }

    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));

    }

    private void checkSameUser(User origin, User user) {
        assertThat(origin.getId(), is(user.getId()));
        assertThat(origin.getName(), is(user.getName()));
        assertThat(origin.getPassword(), is(user.getPassword()));

    }

    @Test
    public void duplicateKey(){
        dao.deleteAll();

        dao.add(user1);

//        assertThrows(DuplicateUserIdException.class, () -> {
//            dao.add(user1);
//        });
        assertThrows(DuplicateKeyException.class, () -> {
            dao.add(user1);
        });
        assertThrows(DataAccessException.class, () -> {
            dao.add(user1);
        });

    }

    @Test
    public void sqlExceptionTranslate(){
//        dao.deleteAll();
//
//        try{
//            dao.add(user1);
//            dao.add(user1);
//        }catch(DuplicateKeyException e){
//            SQLException sqlEx = (SQLException)e.getRootCause();
//            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
//            assertThat(set.translate(null, null, sqlEx), is(DuplicateKeyException.class));
//        }
    }

}