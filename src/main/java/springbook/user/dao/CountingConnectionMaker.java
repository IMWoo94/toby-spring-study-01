package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
	int counter = 0;
	private ConnectionMaker realConnetionMaker;

	public CountingConnectionMaker(ConnectionMaker realConnetionMaker) {
		this.realConnetionMaker = realConnetionMaker;
	}

	@Override
	public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
		counter++;
		return realConnetionMaker.makeNewConnection();
	}

	public int getCounter() {
		return counter;
	}

}
