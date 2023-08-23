package springbook.learningtest.concurrent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import springbook.user.issuetracker.sqlservice.UpdatableSqlRegistry;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstactUpdatableSqlRegistryTest {
	UpdatableSqlRegistry sqlRegistry;

	@BeforeAll
	public void setUp() {
		sqlRegistry = createUpdatableSqlRegisrty();
	}

	abstract protected UpdatableSqlRegistry createUpdatableSqlRegisrty();

	protected void checkFine(String expected1, String expected2, String expected3) {

	}

	@Test
	public void find() {

	}
}
