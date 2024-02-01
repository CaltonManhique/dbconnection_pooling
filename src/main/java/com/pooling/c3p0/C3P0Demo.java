package com.pooling.c3p0;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Demo {

	public static ComboPooledDataSource comboPooledDataSource = null;

	static {

		comboPooledDataSource = new ComboPooledDataSource();

		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/studentDb?useSSL=false");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("1234");

		comboPooledDataSource.setMinPoolSize(3);
		comboPooledDataSource.setAcquireIncrement(3);
		comboPooledDataSource.setMaxPoolSize(30);

	}

	public static void main(String[] args) throws SQLException {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = comboPooledDataSource.getConnection();
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
