package springbook.user.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import springbook.user.domain.Level;
import springbook.user.domain.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {
	User user;

	@BeforeAll
	public void setUp() {
		user = new User();
	}

	@Test
	public void upgradeLevel() {
		Level[] levels = Level.values();
		for (Level level : levels) {
			if (level.nextLevel() == null) {
				continue;
			}
			user.setLevel(level);
			user.upgradeLevel();
			assertThat(user.getLevel(), is(level.nextLevel()));
		}
	}

	@Test
	public void cannotUpgradeLevel() {
		Level[] levels = Level.values();
		for (Level level : levels) {
			if (level.nextLevel() != null) {
				continue;
			}
			user.setLevel(level);
			assertThrows(IllegalArgumentException.class, () -> {
				user.upgradeLevel();
			});
		}
	}

}
