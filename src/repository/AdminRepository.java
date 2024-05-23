package repository;

import database.DatabaseConfiguration;
import user.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS admins" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, userID int, FOREIGN KEY (userID) REFERENCES users(id))";
            stmt = connection.createStatement();
            stmt.execute(createTableSql);
            connection.commit();
            connection.close();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addAdmin(Admin admin) {
        UserRepository.addUser(admin);

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //String insertAdminSql = "INSERT INTO admins(userID) VALUES(" + UserRepository.getUserId(admin) + ")";
            String insertAdminSql = "INSERT INTO admins(userID) VALUES(?)";
            stmt = connection.prepareStatement(insertAdminSql);
            stmt.setInt(1, UserRepository.getUserId(admin));
            stmt.executeUpdate();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Admin getAdmin() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        int userId = -1;
        String username = "";
        String password = "";
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String selectAdminSql = "SELECT * FROM users u JOIN admins a ON u.id = a.userID";
            rs = stmt.executeQuery(selectAdminSql);
            if (rs.next()) {
                userId = rs.getInt("u.id");
                username = rs.getString("u.username");
                password = rs.getString("u.password");
            }
            connection.commit();
            connection.close();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (userId == -1) return null;
        return Admin.getInstance(userId, username, password);
    }


}
