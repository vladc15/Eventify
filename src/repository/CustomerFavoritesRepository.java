package repository;

import com.sun.source.tree.Tree;
import database.DatabaseConfiguration;
import model.Event;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.TreeSet;

public class CustomerFavoritesRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS customer_favorites" +
                    "(customerId int, eventId int, FOREIGN KEY (customerId) REFERENCES customers(id), FOREIGN KEY (eventId) REFERENCES events(id)), PRIMARY KEY (customerId, eventId)";
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

    public void addCustomerFavorite(int customerId, int eventId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String insertCustomerFavoriteSql = "INSERT INTO customer_favorites(customerId, eventId) VALUES(" +
                    customerId + "," + eventId + ")";
            stmt = connection.createStatement();
            stmt.execute(insertCustomerFavoriteSql);
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

    public void removeCustomerFavorite(int customerId, int eventId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteCustomerFavoriteSql = "DELETE FROM customer_favorites WHERE customerId = " + customerId + " AND eventId = " + eventId;
            stmt = connection.createStatement();
            stmt.execute(deleteCustomerFavoriteSql);
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

    public static TreeSet<Event> getCustomerFavorites(int customerId) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        TreeSet<Event> events = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String selectCustomerFavoritesSql = "SELECT * FROM customer_favorites WHERE customerId = " + customerId;
            rs = stmt.executeQuery(selectCustomerFavoritesSql);
            events = new TreeSet<>();
            while (rs.next()) {
                Event event = EventRepository.getEventById(rs.getInt("eventId"));
                events.add(event);
            }
        } catch (Exception e) {
            if (connection != null) {
                try {
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
        return events;
    }

    public static void showCustomerFavorites(int customerId) {
        TreeSet<Event> events = getCustomerFavorites(customerId);
        for (Event event : events) {
            System.out.println(event);
        }
    }
}
