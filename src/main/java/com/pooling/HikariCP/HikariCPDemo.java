package com.pooling.HikariCP;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {

	private static HikariDataSource dataSource = null;

	static {
		HikariConfig config = new HikariConfig();
		
		config.setJdbcUrl("jdbc:mysql://localhost:3306/studentDb?useSSL=false");
		config.setUsername("root");
		config.setPassword("1234");

		config.addDataSourceProperty("minimumIdle", 5);
		config.addDataSourceProperty("maximumPoolSize", 25);

		dataSource = new HikariDataSource(config);
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
