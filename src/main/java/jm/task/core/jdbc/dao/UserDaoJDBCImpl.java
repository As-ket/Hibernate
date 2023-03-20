package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util = new Util();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            String sqlCreate = "CREATE TABLE IF NOT EXISTS Users"
                    + " (id integer not null auto_increment primary key,"
                    + " name varchar(255),"
                    + " lastName varchar(255),"
                    + " age integer)";
            statement.executeUpdate(sqlCreate);
        } catch (SQLException e) {
            System.out.println("не удалось создать таблицу");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            String sqlDrop = "DROP TABLE IF EXISTS Users";
            statement.executeUpdate(sqlDrop);
        } catch (SQLException e) {
            System.out.println("не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String sql = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            System.out.println("не удалось добавить пользователя");
        }
    }

    public void removeUserById(long id) {
        final String sql = "DELETE FROM Users WHERE id=?";
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не удалось удалить User по указанному id");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        final String sql = "SELECT name, lastName, age FROM Users";
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            for (User u : userList) {
                System.out.println(u);
            }
        } catch (SQLException e) {
            System.out.println("не удалось получить всех пользователей");
        }
        return userList;
    }

    public void cleanUsersTable() {
        final String sql = "DELETE FROM Users";
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("не удалось очистить таблицу");
        }
    }
}