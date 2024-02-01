package com.pooling.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPoolingDemo2 {

	public static DataSource dataSource = null;

	static {

		Properties properties = new Properties();

		properties.setProperty("user", "root");
		properties.setProperty("password", "1234");
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				"jdbc:mysql://localhost:3306/studentDb", properties);

		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
		GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<>();

		// specify mininum number of idle connections
		// specify max number of idle connections
		// specify total number of max connections
		// dataSource.setMinIdle(4);
		// dataSource.setMaxIdle(10);
		// dataSource.setMaxTotal(10);
		config.setMinIdle(5);
		config.setMaxIdle(25);
		config.setMaxTotal(25);

		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory, config);
		poolableConnectionFactory.setPool(connectionPool);

		dataSource = new PoolingDataSource<>(connectionPool);
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
