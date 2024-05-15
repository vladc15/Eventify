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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addUser(User user) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            if (user.getLocation() == null) {
                String insertUserSql = "INSERT INTO users(username, password, name, age) VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getName() + "', " + user.getAge() + ")";
                stmt.execute(insertUserSql);
            } else {
                LocationRepository.addLocation(user.getLocation());
                String insertUserSql = "INSERT INTO users(username, password, name, age, locationID) VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getName() + "', " + user.getAge() + ", " + LocationRepository.getLocationId(user.getLocation()) + ")";
                stmt.execute(insertUserSql);
            }
            //String insertUserSql = "INSERT INTO users(username, password, name, age, locationID) VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getName() + "', " + user.getAge() + ", " + LocationRepository.getLocationId(user.getLocation()) + ")";
            //stmt.execute(insertUserSql);
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

    public static void updateUser(User user, int userId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            if (user.getLocation() == null) {
                String updateUserSql = "UPDATE users SET username = '" + user.getUsername() + "', password = '" + user.getPassword() + "', name = '" + user.getName() + "', age = " + user.getAge() + " WHERE id = " + userId;
                stmt.execute(updateUserSql);
            } else {
                String updateUserSql = "UPDATE users SET username = '" + user.getUsername() + "', password = '" + user.getPassword() + "', name = '" + user.getName() + "', age = " + user.getAge() + ", locationID = " + LocationRepository.getLocationId(user.getLocation()) + " WHERE id = " + userId;
                stmt.execute(updateUserSql);
            }
            //String updateUserSql = "UPDATE users SET username = '" + user.getUsername() + "', password = '" + user.getPassword() + "', name = '" + user.getName() + "', age = " + user.getAge() + ", locationID = " + LocationRepository.getLocationId(user.getLocation()) + " WHERE id = " + userId;
            //stmt.execute(updateUserSql);
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

    public static int getUserIdByUsername(String username) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        int userId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT id FROM users WHERE username = '" + username + "'");
            if (rs.next()) {
                userId = rs.getInt("id");
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
        return userId;
    }

    public static int getUserId(User user) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        int userId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT id FROM users WHERE username = '" + user.getUsername() + "'");
            if (rs.next()) {
                userId = rs.getInt("id");
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
        return userId;
    }

    public static User getUserById(int userId) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users u " +
                                        "LEFT JOIN customers c ON u.id = c.userId " +
                                        "LEFT JOIN artists a ON u.id = a.userId " +
                                        "LEFT JOIN admins ad ON u.id = ad.userId WHERE u.id = " + userId);
            if (rs.next()) {
                if (rs.getInt("c.userId") == userId) {
                    Customer c = null;
                    if (rs.getInt("u.locationID") == 0) {
                        c = new Customer(userId, rs.getString("u.username"), rs.getString("u.password"));
                        c.setName(rs.getString("u.name"));
                        c.setAge(rs.getInt("u.age"));
                    }
                    else
                        c = new Customer(userId, rs.getString("u.username"), rs.getString("u.password"), rs.getString("u.name"), rs.getInt("u.age"), LocationRepository.getLocationById(rs.getInt("u.locationID")));
                    //Customer c = new Customer(userId, rs.getString("u.username"), rs.getString("u.password"), rs.getString("u.name"), rs.getInt("u.age"), LocationRepository.getLocationById(rs.getInt("u.locationID")));
                    int customer_id = rs.getInt("c.id");
                    c.setFavorites(CustomerFavoritesRepository.getCustomerFavorites(customer_id));
                    c.setHistory(CustomerHistoryRepository.getCustomerHistory(customer_id));
                    c.setReviews(ReviewRepository.getReviewsByUserId(userId));
                    c.setFollowedArtists(CustomerFollowedArtistsRepository.getCustomerFollowedArtists(customer_id));
                    c.setTickets(CustomerTicketsRepository.getTicketsByCustomerId(customer_id));
                    user = c;
                } else if (rs.getInt("a.userId") == userId) {
                    if (rs.getInt("u.locationID") == 0) {
                        user = new Artist(userId, rs.getString("u.username"), rs.getString("u.password"));
                        user.setName(rs.getString("u.name"));
                        user.setAge(rs.getInt("u.age"));
                        ((Artist) user).setBio(rs.getString("a.bio"));
                        ((Artist) user).setGenre(rs.getString("a.genre"));
                    } else {
                        user = new Artist(userId, rs.getString("u.username"), rs.getString("u.password"), rs.getString("u.name"), rs.getInt("u.age"), LocationRepository.getLocationById(rs.getInt("u.locationID")), rs.getString("a.bio"), rs.getString("a.genre"));
                    }
                } else if (rs.getInt("ad.userId") == userId) {
                    user = AdminRepository.getAdmin();
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
        return user;
    }

    public void updateName(String name, int userId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String updateNameSql = "UPDATE users SET name = '" + name + "' WHERE id = " + userId;
            stmt.execute(updateNameSql);
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

    public void updateAge(int age, int userId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String updateAgeSql = "UPDATE users SET age = " + age + " WHERE id = " + userId;
            stmt.execute(updateAgeSql);
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

    public void updateLocation(int locationId, int userId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String updateLocationSql = "UPDATE users SET locationID = " + locationId + " WHERE id = " + userId;
            stmt.execute(updateLocationSql);
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

}
