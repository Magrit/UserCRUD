package pl.coderslab.entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    private static final String CREATE_USER_QUERY = "INSERT INTO user(email,userName,password) VALUES (?,?,?)";
    private static final String READ_USER_QUERY = "SELECT * FROM user WHERE id=?";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET email=?, username=?,password=? WHERE id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id=?";
    private static final String FIND_ALL_USER_QUERY = "SELECT * FROM user";


    public User createUser(String email, String userName, String password) {

        User user = new User(email, userName, password);

        try (Connection conn = DBUtil.connectDB()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            while (rs.next()) {
                long id = rs.getLong(1);
                System.out.println("New User created in DB on id=" + id);
                user.setId(id);
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        }

        return user;
    }


    public User readUser(long id) {
        User user = new User();

        try (Connection conn = DBUtil.connectDB()) {

            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                try {
                    if (resultSet.next()) {
                        user.setId(resultSet.getLong(1));
                        user.setEmail(resultSet.getString(2));
                        user.setUserName(resultSet.getString(3));
                        user.setPassword(resultSet.getString(4));
                    }

                } catch (NumberFormatException exp) {
                    exp.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (
                SQLException exp) {
            exp.printStackTrace();
        }

        return user;
    }


    public void deleteUser(long id) {
        try (Connection conn = DBUtil.connectDB();
             PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY)) {

            statement.setLong(1, id);
            statement.executeUpdate();

            System.out.println("User deleted");

        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }


    public void updateUser(User user) {
        if (user.getId() != 0) {

            long id = user.getId();
            String email = user.getEmail();
            String username = user.getUserName();
            String password = user.getPassword();

            try (Connection conn = DBUtil.connectDB();
                 PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY)) {

                statement.setLong(1, id);
                statement.setString(2, email);
                statement.setString(3, username);
                statement.setString(4, password);

                statement.executeUpdate();

                System.out.println("User with id=" + id + " updated");

            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        } else {
            if (user.getEmail() == null || user.getUserName() == null || user.getPassword() == null) {
                System.out.println("Can't update or create user.");
            } else {
                createUser(user.getEmail(), user.getUserName(), user.getPassword());
            }

        }
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();

        try (Connection conn = DBUtil.connectDB();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_QUERY);
             ResultSet resultSet = statement.executeQuery()){

                try {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getLong(1));
                        user.setEmail(resultSet.getString(2));
                        user.setUserName(resultSet.getString(3));
                        user.setPassword(resultSet.getString(4));
                        userList.add(user);
                    }

                } catch (NumberFormatException exp) {
                    exp.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch(
                SQLException exp){
            exp.printStackTrace();
        }

    return userList;
    }


}
