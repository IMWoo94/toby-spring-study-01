package springbook.user.sqlservice;

public class SqlRetrievalFialureException extends RuntimeException {
	public SqlRetrievalFialureException(String message) {
		super(message);
	}

	public SqlRetrievalFialureException(String message, Throwable cause) {
		super(message, cause);
	}
}
