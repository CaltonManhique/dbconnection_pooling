package com.pooling;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPoolingDemo {

	public static BasicDataSource dataSource = null;

	static {
		dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/studentDb?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		// specify minimum number of idle connections
		// specify maximum number of idle connections
		// specify total number of max connections
		dataSource.setMinIdle(5);
		dataSource.setMaxIdle(10);
		dataSource.setMaxTotal(10);
	}

	public static void main(String[] args) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM tblstudent");

			while (resultSet.next()) {
				System.out.println(resultSet.getInt("student_id") + " " + resultSet.getString("student_name") + " "
						+ resultSet.getDate("birth_date") + " " + resultSet.getString("address"));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			if (connection != null)
				connection.close();

			if (statement != null)
				statement.close();

			if (resultSet != null)
				resultSet.close();
		}

	}
}
