package pl.coderslab.entity;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private final static String DB_URL =
            "jdbc:mysql://localhost:3306/workshop2?use_SSL=false&characterEncoding=utf8&serverTimezone=UCT";
    private final static String DB_USERNAME = "root";
    private final static String DB_PASSWORD = "coderslab";
//
//    public static Connection connectDB() throws SQLException {
//        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//        return connection;
//    }


    private static DataSource dataSource;

    public static Connection connectDB() throws SQLException {
        return getInstance().getConnection();
    }

    private static DataSource getInstance() {
        if (dataSource == null) {
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/users");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }


}
