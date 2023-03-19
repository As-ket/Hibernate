package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;



import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = new Util();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = util.getSessionFactory().openSession()) {
            session.beginTransaction();

            final String sql = "CREATE TABLE IF NOT EXISTS Users"
                    + " (id integer not null auto_increment primary key,"
                    + " name varchar(255),"
                    + " lastName varchar(255),"
                    + " age integer)";

            session.createNativeQuery(sql, User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = util.getSessionFactory().openSession()) {
            session.beginTransaction();

            final String sql = "DROP TABLE IF EXISTS Users";

            session.createNativeQuery(sql, User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = util.getSessionFactory().openSession()) {
            User emp = new User(name, lastName, age);
            session.beginTransaction();
            session.persist(emp);
            session.getTransaction().commit();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);

        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = util.getSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
            for (User u : users) {
                System.out.println(u);
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createMutationQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
