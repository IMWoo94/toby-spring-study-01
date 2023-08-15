package springbook.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.*;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.List;

public class UserDaoTest {


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);

        dao.deleteAll();

        User addUser = new User();
        addUser.setId("whiteship");
        addUser.setName("백기선");
        addUser.setPassword("spring");
        addUser.setLevel(Level.BASIC);
        addUser.setLogin(50);
        addUser.setRecommend(30);
        addUser.setEmail("dlrldyd1002@gmail.com");

        dao.add(addUser);

        System.out.println(addUser.getId() + " 등록 성공");
        
        User searchUser = new User();
        searchUser = dao.get("whiteship");
        if(!searchUser.getName().equals(addUser.getName())){
            System.out.println("테스트 실패 (name)");
        }else if(!searchUser.getPassword().equals(addUser.getPassword())){
            System.out.println("테스트 실패 (password)");
        }else{
            System.out.println("조회 테스트 성공");
        }
        System.out.println("search whiteship 이름 : " + searchUser.getName());
        System.out.println("search whiteship 비밀번호 : " + searchUser.getPassword());

        System.out.println(searchUser.getId() + " 조회 성공");

        System.out.println("user count = " + dao.getCount());

        List<User> userList = dao.getAll();
        for(User temp : userList) {
            System.out.println("user 정보 : " + temp.getId() + " / " + temp.getName() + " / " + temp.getPassword());
        }
        

/*        // 동일 오브젝트 확인
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDao();
        UserDao dao2 = factory.userDao();

        System.out.println(dao1);
        System.out.println(dao2);

        // 어플리케이션 컨테스트에서 생성된 Bean으로 가져 오는 경우
        UserDao dao3 = context.getBean("userDao", UserDao.class);
        UserDao dao4 = context.getBean("userDao", UserDao.class);

        System.out.println(dao3);
        System.out.println(dao4);*/


    }
}
