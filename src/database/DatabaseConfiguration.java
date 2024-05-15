package database;

import java.sql.Connection;

public class DatabaseConfiguration {
    private static final String URL = "jdbc:mysql://localhost:3306/eventify";
    private static final String USER = "vlad";
    private static final String PASSWORD = "vlad";

    //private static Connection connection;

    private DatabaseConfiguration() {}

    public static Connection getConnection() {
        Connection connection = null;
        try {
            if (connection == null || connection.isClosed()) {
                connection = java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
