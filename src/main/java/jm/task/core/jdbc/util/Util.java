package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

public class Util {
    private final static String parameter = "?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String URL = "jdbc:mysql://127.0.0.1:3306/db_pp113" + parameter;
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.datasource", getMysqlDataSource());
        return properties;
    }

    private static DataSource getMysqlDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        mysqlDataSource.setUser(USERNAME);
        mysqlDataSource.setPassword(PASSWORD);
        return mysqlDataSource;
    }

}
