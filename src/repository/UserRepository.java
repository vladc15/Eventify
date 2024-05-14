package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import database.DatabaseConfiguration;
import user.Admin;
import user.Artist;
import user.Customer;
import user.User;

public class UserRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS users" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, username varchar(30) NOT NULL, password varchar(30) NOT NULL, name varchar(30), age int, locationID int NULL, FOREIGN KEY (locationID) REFERENCES locations(id))";
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
        }
    }

    public static void addUser(User user) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String insertUserSql = "INSERT INTO users(username, password, name, age, locationID) VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getName() + "', " + user.getAge() + ", " + LocationRepository.getLocationId(user.getLocation()) + ")";
            stmt.execute(insertUserSql);
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
        }
    }

    public static void updateUser(User user, int userId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String updateUserSql = "UPDATE users SET username = '" + user.getUsername() + "', password = '" + user.getPassword() + "', name = '" + user.getName() + "', age = " + user.getAge() + ", locationID = " + LocationRepository.getLocationId(user.getLocation()) + " WHERE id = " + userId;
            stmt.execute(updateUserSql);
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
        }
    }

    public static int getUserIdByUsername(String username) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE username = '" + username + "'");
            if (rs.next()) {
                return rs.getInt("id");
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
            e.printStackTrace();
        }
        return -1;
    }

    public static int getUserId(User user) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE username = '" + user.getUsername() + "'");
            if (rs.next()) {
                return rs.getInt("id");
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
            e.printStackTrace();
        }
        return -1;
    }

    public static User getUserById(int userId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users u LEFT JOIN customers c LEFT JOIN artists a LEFT JOIN admins ad ON (u.id = c.userId OR u.id = a.userId OR u.id = ad.userId) WHERE u.id = " + userId);
            if (rs.next()) {
                if (rs.getInt("c.userId") == userId) {
                    Customer c = new Customer(userId, rs.getString("u.username"), rs.getString("u.password"), rs.getString("u.name"), rs.getInt("u.age"), LocationRepository.getLocationById(rs.getInt("u.locationID")));
                    int customer_id = rs.getInt("c.id");
                    c.setFavorites(CustomerFavoritesRepository.getCustomerFavorites(customer_id));
                    c.setHistory(CustomerHistoryRepository.getCustomerHistory(customer_id));
                    c.setReviews(ReviewRepository.getReviewsByUserId(userId));
                    c.setFollowedArtists(CustomerFollowedArtistsRepository.getCustomerFollowedArtists(customer_id));
                    c.setTickets(CustomerTicketsRepository.getTicketsByCustomerId(customer_id));
                    return c;
                } else if (rs.getInt("a.userId") == userId) {
                    return new Artist(userId, rs.getString("u.username"), rs.getString("u.password"), rs.getString("u.name"), rs.getInt("u.age"), LocationRepository.getLocationById(rs.getInt("u.locationID")), rs.getString("a.bio"), rs.getString("a.genre"));
                } else if (rs.getInt("ad.userId") == userId) {
                    return Admin.getInstance();
                }
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
            e.printStackTrace();
        }
        return null;
    }

}
