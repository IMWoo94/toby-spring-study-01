package springbook.user.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {

	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc dao = new UserDaoJdbc();
		dao.setDataSource(dataSource());
		return dao;
	}

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl(
			"jdbc:mysql://localhost:3306/tobyspring?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul");
		dataSource.setUsername("toby");
		dataSource.setPassword("spring");

		return dataSource;
	}
}
