package springbook.learningtest.concurrent;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import springbook.user.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.user.issuetracker.sqlservice.UpdatableSqlRegistry;
import springbook.user.issuetracker.sqlservice.updatable.EmbeddedDbSqlRegistry;

public class EmbeddedDbSqlRegistryTest extends AbstactUpdatableSqlRegistryTest {
	EmbeddedDatabase db;

	@Override
	protected UpdatableSqlRegistry createUpdatableSqlRegisrty() {
		db = new EmbeddedDatabaseBuilder().setType(HSQL)
			.addScript("classpath:/sqlRegistrySchema.sql")
			.build();

		EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
		embeddedDbSqlRegistry.setDataSource(db);

		return embeddedDbSqlRegistry;
	}

	@AfterAll
	public void tearDown() {
		db.shutdown();
	}

	@Test
	public void transcationalUpdate() {
		checkFine("SQL1", "SQL2", "SQL3");

		Map<String, String> sqlmap = new HashMap<>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY9999!@#$", "Modified9999");

		try {
			sqlRegistry.updateSql(sqlmap);
			fail();
		} catch (SqlUpdateFailureException e) {
			checkFine("SQL1", "SQL2", "SQL3");
		}
	}
}
