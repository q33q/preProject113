package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getInstance().getSessionFactory();

    public UserDaoHibernateImpl() {

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

        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            session.createNativeQuery(sql).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            session.save(new User(name, lastName, age));

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Query query = session.createQuery("DELETE FROM users WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;

        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Query query = session.createQuery("FROM users");
            users = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Query query = session.createSQLQuery("TRUNCATE TABLE users");
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }
}
