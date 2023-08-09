package springbook.learningtest.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springbook.user.dao.UserDao;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class LearningTest {

    @Autowired
    UserDao user1;

    @Autowired
    UserDao user2;

    @Autowired
    ApplicationContext context;

    @Autowired
    DataSource dataSource;

    // Spring 컨테이너는 싱글톤을 기반으로 하는지 확인.
    @Test
    public void 싱글톤확인(){

        assertThat(user1, is(sameInstance(user2)));
    }

    // ApplicationContext의 getBean과 Autowired의 값이 동일할가?
    @Test
    public void ApplicationContextisAutowired(){
        UserDao contextUserDao = context.getBean("userDao", UserDao.class);
        assertThat(contextUserDao, is(sameInstance(user1)));
        assertThat(contextUserDao, is(sameInstance(user2)));
    }

    // getBean이 없다면?
    @Test
    public void noBean(){
        assertThrows(NoSuchBeanDefinitionException.class, () -> {
            context.getBean("unknown", UserDao.class);
        });
    }
}
