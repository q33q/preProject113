package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

public class Util {
    private static volatile Util instance = null;
    private static SessionFactory sessionFactory;
    private final static String PARAMETER = "?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String URL = "jdbc:mysql://127.0.0.1:3306/db_pp113" + PARAMETER;
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    private Util() {
    }

    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperties(Util.getProperties());
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.current_session_context_class", "thread");
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
