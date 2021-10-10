package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    public Util() {
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String parameter = "?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String URL = "jdbc:mysql://127.0.0.1:3306/db_pp113" + parameter;
        String USERNAME = "root";
        String PASSWORD = "root";

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

}
