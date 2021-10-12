package jm.task.core.jdbc.util;

import java.sql.*;

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

}
