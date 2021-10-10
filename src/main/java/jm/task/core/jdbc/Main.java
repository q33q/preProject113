package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

//      Создание таблицы User(ов)
        userService.createUsersTable();

//      Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        for (int i = 0; i < 4; i++) {
            userService.saveUser("Jon" + i, "Doe" + i, (byte) (i + 20));
        }
//      Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

//      Очистка таблицы User(ов)
        userService.cleanUsersTable();

//      Удаление таблицы
        userService.dropUsersTable();

    }
}
