package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private Connection connection = null;
    private Statement statement = null;

    public Util() {
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String parameter = "?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String URL = "jdbc:mysql://127.0.0.1:3306/db_pp113" + parameter;
        String USERNAME = "root";
        String PASSWORD = "root";

        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Class.forName("com.mysql.cj.jdbc.Driver");

        return connection;
    }

    public Statement getStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
        return statement;
    }

    public void close() throws SQLException, ClassNotFoundException {
        statement.close();
        connection.close();
    }
}
