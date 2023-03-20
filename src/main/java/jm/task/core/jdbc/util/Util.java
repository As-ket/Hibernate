package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;


public class Util {
    // реализуйте настройку соеденения с БД

    private final static String USER = "root";
    private final static String PASSWORD = "u5WMdxza";
    private final static String URL = "jdbc:mysql://localhost:3306/myTask";


    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("not work");
        }
        return connection;
    }
    public SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/myTask");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "u5WMdxza");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            sessionFactory = new Configuration().setProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();
        } catch (Exception e) {
            System.out.println("not work");
        }
        return sessionFactory;
    }
}
