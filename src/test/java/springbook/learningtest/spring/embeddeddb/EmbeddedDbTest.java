package springbook.learningtest.spring.embeddeddb;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmbeddedDbTest {
	EmbeddedDatabase db;

	// deprecated
	//SimpleJdbcTemplate template;

	JdbcTemplate template;

	@BeforeEach
	public void setUp() {
		db = new EmbeddedDatabaseBuilder().setType(HSQL)
			.addScript("classpath:/schema.sql")
			.addScript("classpath:/data.sql")
			.build();

		template = new JdbcTemplate(db);
	}

	@AfterEach
	public void tearDown() {
		db.shutdown();
	}

	@Test
	public void initData() {
		assertThat(template.queryForObject("select count(*) from sqlmap", Integer.class), is(2));

		List<Map<String, Object>> list = template.queryForList("select * from sqlmap order by key_");
		assertThat((String)list.get(0).get("key_"), is("KEY1"));
		assertThat((String)list.get(0).get("sql_"), is("SQL1"));
		assertThat((String)list.get(1).get("key_"), is("KEY2"));
		assertThat((String)list.get(1).get("sql_"), is("SQL2"));
	}

	@Test
	public void insert() {
		template.update("insert into sqlmap(key_, sql_) values (?, ?)", "KEY3", "SQL3");

		assertThat(template.queryForObject("select count(*) from sqlmap", Integer.class), is(3));
	}

}
