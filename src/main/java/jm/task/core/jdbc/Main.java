package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        for (int i = 0; i < 4; i++) {
            String name = "Jon" + i;
            userService.saveUser(name, "Doe" + i, (byte) (i + 20));
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        }

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
//
        userService.cleanUsersTable();
//
        userService.dropUsersTable();

    }
}
