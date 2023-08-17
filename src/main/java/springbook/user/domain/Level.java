package springbook.user.domain;

public enum Level {
	GOLD(4, null), BRONZE(3, GOLD), SILVER(2, BRONZE), BASIC(1, SILVER);

	private final int value;
	private final Level next;

	Level(int value, Level next) {
		this.value = value;
		this.next = next;
	}

	public int intValue() {
		return value;
	}

	public Level nextLevel() {
		return next;
	}

	public static Level valueOf(int value) {
		switch (value) {
			case 1:
				return BASIC;
			case 2:
				return SILVER;
			case 3:
				return BRONZE;
			case 4:
				return GOLD;
			default:
				throw new AssertionError("Unknown value : " + value);
		}
	}
}
