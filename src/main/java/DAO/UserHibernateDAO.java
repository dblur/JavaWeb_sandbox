package DAO;

import Entity.User;
import database.HibernateConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserHibernateDAO implements UserDAO {
    private final SessionFactory sessionFactory;

    public UserHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = HibernateConnection.getSessionFactory();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = sessionFactory.openSession()) {
            userList = session.createQuery("FROM User", User.class).getResultList();
            session.close();
        }
        return userList;
    }

    @Override
    public User getUserByName(String name) {
        User user;
        try (Session session = sessionFactory.openSession()) {
            user = session.createQuery("FROM User WHERE name=:name", User.class)
            .setParameter("name", name).uniqueResult();
            session.close();
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user;
        try (Session session = sessionFactory.openSession()) {
            user = session.createQuery("FROM User WHERE id=:id", User.class)
                    .setParameter("id", id).uniqueResult();
            session.close();
        }
        return user;
    }

    public void addUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
        }
    }

    public void deleteUser(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(getUserById(id));
            transaction.commit();
            session.close();
        }
    }
}