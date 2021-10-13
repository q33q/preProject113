package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {

    }

    private SessionFactory getSessionFactory() {
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

    private void executeQuery(String sql) {
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE Table IF NOT EXISTS users" +
                "(" +
                "    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "    name     VARCHAR(45)                             NOT NULL," +
                "    lastName VARCHAR(45)                             NOT NULL," +
                "    age      TINYINT UNSIGNED                        NOT NULL" +
                ")";
        executeQuery(sql);
    }

    @Override
    public void dropUsersTable() {
        executeQuery("DROP TABLE IF EXISTS users");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age)\n" +
                "VALUES (?, ?, ?)";
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<User> query = session.createNativeQuery(sql, User.class);
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        executeQuery("DELETE FROM users WHERE id = " + id);
    }

    @Override
    public List<User> getAllUsers() {
        return getSessionFactory().openSession().createNativeQuery("SELECT * FROM users", User.class).getResultList();
    }

    @Override
    public void cleanUsersTable() {
        executeQuery("TRUNCATE TABLE users");
    }
}
