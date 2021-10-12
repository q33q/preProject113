package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private void executeStatement(String sql) {
        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection)) {

            statement.execute(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        String tableSql = "CREATE Table IF NOT EXISTS users" +
                "(" +
                "    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "    name     VARCHAR(45)                             NOT NULL," +
                "    lastName VARCHAR(45)                             NOT NULL," +
                "    age      TINYINT UNSIGNED                        NOT NULL" +
                ")";
        executeStatement(tableSql);
    }

    @Override
    public void dropUsersTable() {
        executeStatement("DROP TABLE IF EXISTS users");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age)\n" +
                "VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection);
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setInt(3, age);

            prepareStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection);
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setLong(1, id);
            prepareStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection);
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        executeStatement("TRUNCATE TABLE users");
    }

}
