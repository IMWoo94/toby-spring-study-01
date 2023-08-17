package springbook.learningtest.junit;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springbook.user.dao.UserDaoJdbc;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class LearningTest {

	@Autowired
	UserDaoJdbc user1;

	@Autowired
	UserDaoJdbc user2;

	@Autowired
	ApplicationContext context;

	@Autowired
	DataSource dataSource;

	// Spring 컨테이너는 싱글톤을 기반으로 하는지 확인.
	@Test
	@DisplayName("싱글톤확인")
	public void singletonCheck() {

		assertThat(user1, is(sameInstance(user2)));
	}

	// ApplicationContext의 getBean과 Autowired의 값이 동일할가?
	@Test
	public void applicationContextisAutowired() {
		UserDaoJdbc contextUserDao = context.getBean("userDao", UserDaoJdbc.class);
		assertThat(contextUserDao, is(sameInstance(user1)));
		assertThat(contextUserDao, is(sameInstance(user2)));
	}

	// getBean이 없다면?
	@Test
	public void noBean() {
		assertThrows(NoSuchBeanDefinitionException.class, () -> {
			context.getBean("unknown", UserDaoJdbc.class);
		});

	}

	@Test
	public void throwExceptionTest() {
		try {
			System.out.println("test start");
			throwException();
		} catch (Throwable e) {
			System.out.println("여기는 테스트 구간입니다.");
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();

		}

	}

	public static void throwException() throws Throwable {
		try {
			System.out.println("throwException start");
			throw new NullPointerException("일부러 nullexception 일으키기");
		} catch (Exception e) {
			System.out.println("여기는 catch 입니다.");
			System.out.println(e.getMessage());
			//throw e;
			throw new Exception().initCause(e);
		}
	}
}
