package springbook.user.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static springbook.user.service.UserServiceImpl.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.dao.MockUserDao;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.factorybean.TxProxyFactoryBean;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

	@Autowired
	UserService userService;

	@Autowired
	UserDao userDao;

	@Autowired
	PlatformTransactionManager transactionManager;

	@Autowired
	MailSender mailSender;

	@Autowired
	ApplicationContext context;

	List<User> users;

	@BeforeEach
	public void setUp() {
		users = Arrays.asList(
			new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "dlrldyd1002@gamil.com"),
			new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "dlrldyd1002@gamil.com"),
			new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECCOMENT_FOR_BRONZE - 1, "dlrldyd1002@gamil.com"),
			new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECCOMENT_FOR_BRONZE, "dlrldyd1002@gamil.com"),
			new User("lee", "이상민", "lee", Level.BRONZE, MIN_LOGCOUNT_FOR_GOLD, 70, "dlrldyd1002@gamil.com"),
			new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "dlrldyd1002@gamil.com")
		);
	}

	@Test
	public void bean() {
		//assertThat(userService, is(context.getBean("userService", UserService.class)));
		assertNotNull(userService);
		assertThat(userService, is(notNullValue()));
	}

	@Test
	public void upgradeLevels() throws Exception {
		UserServiceImpl userServiceImpl = new UserServiceImpl();

		MockUserDao mockUserDao = new MockUserDao(this.users);
		userServiceImpl.setUserDao(mockUserDao);

		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);

		userServiceImpl.upgradeLevels();

		//        checkLevel(users.get(0), Level.BASIC);
		//        checkLevel(users.get(1), Level.SILVER);
		//        checkLevel(users.get(2), Level.SILVER);
		//        checkLevel(users.get(3), Level.BRONZE);
		//        checkLevel(users.get(4), Level.GOLD);
		//        checkLevel(users.get(5), Level.GOLD);

		List<User> updated = mockUserDao.getUpdated();
		assertThat(updated.size(), is(3));
		checkUserAndLevel(updated.get(0), "joytouch", Level.SILVER);
		checkUserAndLevel(updated.get(1), "madnite1", Level.BRONZE);
		checkUserAndLevel(updated.get(2), "lee", Level.GOLD);

		//        checkLevelUpgrade(users.get(0), false);
		//        checkLevelUpgrade(users.get(1), true);
		//        checkLevelUpgrade(users.get(2), false);
		//        checkLevelUpgrade(users.get(3), true);
		//        checkLevelUpgrade(users.get(4), true);
		//        checkLevelUpgrade(users.get(5), false);

		List<String> request = mockMailSender.getRequests();
		assertThat(request.size(), is(3));
		assertThat(request.get(0), is(users.get(1).getEmail()));
		assertThat(request.get(1), is(users.get(3).getEmail()));
		assertThat(request.get(2), is(users.get(4).getEmail()));

	}

	private void checkUserAndLevel(User user, String id, Level level) {
		assertThat(user.getId(), is(id));
		assertThat(user.getLevel(), is(level));
	}

	@Test
	public void mockUpgradeLevels() throws Exception {
		UserServiceImpl userServiceImpl = new UserServiceImpl();

		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);

		userServiceImpl.setUserDao(mockUserDao);

		MailSender mockMailSender = mock(MailSender.class);
		userServiceImpl.setMailSender(mockMailSender);

		userServiceImpl.upgradeLevels();

		verify(mockUserDao, times(3)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel(), is(Level.SILVER));
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel(), is(Level.BRONZE));
		verify(mockUserDao).update(users.get(4));
		assertThat(users.get(4).getLevel(), is(Level.GOLD));

		ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mockMailSender, times(3)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
		assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
		assertThat(mailMessages.get(2).getTo()[0], is(users.get(4).getEmail()));
	}

	@Test
	public void add() {
		userDao.deleteAll();

		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);

		userService.add(userWithLevel);
		userService.add(userWithoutLevel);

		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(userWithoutLevel.getLevel()));

	}

	private void checkLevel(User user, Level level) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(level));
	}

	private void checkLevelUpgrade(User user, boolean bool) {
		User userUpdate = userDao.get(user.getId());
		if (bool) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}

	@Test
	@DirtiesContext
	public void upgradeAllOrNothing() throws Exception {
		TestUserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		testUserService.setMailSender(this.mailSender);

		TxProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", TxProxyFactoryBean.class);
		txProxyFactoryBean.setTarget(testUserService);

		UserService txUserService = (UserService)txProxyFactoryBean.getObject();

		userDao.deleteAll();

		for (User user : users) {
			userDao.add(user);
		}

		try {
			txUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {

		}

		checkLevelUpgrade(users.get(1), false);
	}

	static class TestUserService extends UserServiceImpl {
		private String id;

		private TestUserService(String id) {
			this.id = id;
		}

		@Override
		public void upgradeLevel(User user) {
			System.out.println("TestUserService upgradeLevel method");
			if (user.getId().equals(this.id)) {
				throw new TestUserServiceException();
			}
			super.upgradeLevel(user);
		}
	}

	static class TestUserServiceException extends RuntimeException {

	}

}
