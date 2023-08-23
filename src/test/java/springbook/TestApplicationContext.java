package springbook;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mail.MailSender;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.cj.jdbc.Driver;

import springbook.user.dao.UserDao;
import springbook.user.dao.UserDaoJdbc;
import springbook.user.issuetracker.sqlservice.updatable.EmbeddedDbSqlRegistry;
import springbook.user.service.MockMailSender;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceImpl;
import springbook.user.service.UserServiceTest;
import springbook.user.sqlservice.OxmSqlService;
import springbook.user.sqlservice.SqlRegistry;
import springbook.user.sqlservice.SqlService;

@Configuration
// @ImportResource("/test-applicationContext.xml")
@EnableTransactionManagement
public class TestApplicationContext {

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

		dataSource.setDriverClass(Driver.class);
		dataSource.setUrl(
			"jdbc:mysql://localhost:3306/tobyspring?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul");
		dataSource.setUsername("toby");
		dataSource.setPassword("spring");

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}

	@Bean
	public UserDao userDao() {
		UserDaoJdbc dao = new UserDaoJdbc();
		dao.setDataSource(dataSource());
		dao.setSqlService(sqlService());

		return dao;
	}

	@Bean
	public UserService userService() {
		UserServiceImpl userService = new UserServiceImpl();
		userService.setUserDao(userDao());
		userService.setMailSender(mailSender());

		return userService;
	}

	@Bean
	public UserService testUserService() {
		UserServiceTest.TestUserServiceImpl testUserService = new UserServiceTest.TestUserServiceImpl();
		testUserService.setUserDao(userDao());
		testUserService.setMailSender(mailSender());

		return testUserService;
	}

	@Bean
	public MailSender mailSender() {
		// DummyMailSender mailSender = new DummyMailSender();
		MockMailSender mailSender = new MockMailSender();

		return mailSender;
	}

	@Bean
	public SqlService sqlService() {
		OxmSqlService sqlService = new OxmSqlService();
		sqlService.setUnmarshaller(unmarshaller());
		sqlService.setSqlRegistry(sqlRegistry());

		return sqlService;
	}

	@Bean
	public SqlRegistry sqlRegistry() {
		EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
		sqlRegistry.setDataSource(embeddedDatabase());

		return sqlRegistry;
	}

	@Bean
	public Unmarshaller unmarshaller() {
		Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
		unmarshaller.setContextPath("springbook.user.sqlservice.jaxb");

		return unmarshaller;
	}

	@Bean
	public DataSource embeddedDatabase() {
		return new EmbeddedDatabaseBuilder().setName("embeddedDatabase")
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("classpath:/sqlRegistrySchema.sql")
			.build();
	}

}
