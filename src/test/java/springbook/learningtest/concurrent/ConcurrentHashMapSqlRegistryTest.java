package springbook.learningtest.concurrent;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import springbook.user.issuetracker.sqlservice.SqlUpdateFailureException;
import springbook.user.issuetracker.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.ConcurrentHashMapSqlRegistry;
import springbook.user.sqlservice.SqlRetrievalFailureException;

public class ConcurrentHashMapSqlRegistryTest extends AbstactUpdatableSqlRegistryTest {

	@Override
	protected UpdatableSqlRegistry createUpdatableSqlRegisrty() {
		return new ConcurrentHashMapSqlRegistry();
	}

	UpdatableSqlRegistry sqlRegistry;

	@BeforeEach
	public void setUp() {
		sqlRegistry = new ConcurrentHashMapSqlRegistry();
		sqlRegistry.registrySql("KEY1", "SQL1");
		sqlRegistry.registrySql("KEY2", "SQL2");
		sqlRegistry.registrySql("KEY3", "SQL3");
	}

	@Test
	public void find() {
		checkFindResult("SQL1", "SQL2", "SQL3");
	}

	private void checkFindResult(String expected1, String expected2, String expected3) {
		assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
		assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
		assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
	}

	@Test
	public void unknownkey() {
		assertThrows(SqlRetrievalFailureException.class, () -> sqlRegistry.findSql("SQL9999!@#$"));
	}

	@Test
	public void updateSingle() {
		sqlRegistry.updateSql("KEY2", "Modified2");
		checkFindResult("SQL1", "Modified2", "SQL3");
	}

	@Test
	public void updateMulti() {
		Map<String, String> sqlmap = new HashMap<>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY3", "Modified3");

		sqlRegistry.updateSql(sqlmap);

		checkFindResult("Modified1", "SQL2", "Modified3");
	}

	@Test
	public void updateWithNoExistingKey() {
		assertThrows(SqlUpdateFailureException.class, () -> sqlRegistry.updateSql("SQL9999!@#$", "Modified2"));
	}
}
