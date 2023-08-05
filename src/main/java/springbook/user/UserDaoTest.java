package springbook.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.*;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        User addUser = new User();
        addUser.setId("whiteship");
        addUser.setName("백기선");
        addUser.setPassword("spring");

        //dao.add(addUser);

        System.out.println(addUser.getId() + " 등록 성공");
        
        User searchUser = new User();
        searchUser = dao.get("whiteship");
        System.out.println("search whiteship 이름 : " + searchUser.getName());
        System.out.println("search whiteship 비밀번호 : " + searchUser.getPassword());

        System.out.println(searchUser.getId() + " 조회 성공");
        

        // 동일 오브젝트 확인
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDao();
        UserDao dao2 = factory.userDao();

        System.out.println(dao1);
        System.out.println(dao2);

        // 어플리케이션 컨테스트에서 생성된 Bean으로 가져 오는 경우
        UserDao dao3 = context.getBean("userDao", UserDao.class);
        UserDao dao4 = context.getBean("userDao", UserDao.class);

        System.out.println(dao3);
        System.out.println(dao4);

    }
}
