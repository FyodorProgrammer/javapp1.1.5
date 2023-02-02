package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import jm.task.core.jdbc.util.Util;
import org.hibernate.cfg.Configuration;

public class UserDaoHibernateImpl implements UserDao {
    private static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS UsersTable (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), lastName VARCHAR(255), age TINYINT);";

    private static String DELETE_TABLE = "DROP TABLE IF EXISTS UsersTable;";
    private static final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession();) {
            try {
                session.beginTransaction();

                session.createSQLQuery(CREATE_TABLE).executeUpdate();

                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession();) {
            try {
                session.beginTransaction();

                session.createSQLQuery(DELETE_TABLE).executeUpdate();

                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession();) {
            try {

                User user = new User(name, lastName, age);
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
                System.out.println("User с именем - " + name + " добавлен в базу данных");

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.getCurrentSession();) {
            try {
                session.beginTransaction();

                User user = session.get(User.class, id);
                session.delete(user);

                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }

        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = sessionFactory.getCurrentSession();) {
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession();) {
            try {
                session.beginTransaction();
                session.createQuery("delete User").executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }
}
