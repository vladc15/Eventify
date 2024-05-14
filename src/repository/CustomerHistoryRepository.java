package repository;

import database.DatabaseConfiguration;
import model.Event;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerHistoryRepository {
    public static void createTable() {
        Connection connection = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS customer_history" +
                    "(customerId int, eventId int, FOREIGN KEY (customerId) REFERENCES customers(id), FOREIGN KEY (eventId) REFERENCES events(id)), PRIMARY KEY (customerId, eventId)";
            Statement stmt = connection.createStatement();
            stmt.execute(createTableSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToHistory(int customerId, int eventId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String sql = "INSERT INTO customer_history (customerId, eventId) VALUES (" + customerId + ", " + eventId + ")";
            stmt.executeUpdate(sql);
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

    public static List<Event> getCustomerHistory(int customerId) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Event> events = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT * FROM customer_history WHERE customerId = " + customerId;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Event event = EventRepository.getEventById(rs.getInt("eventId"));
                events.add(event);
            }
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
        }
        return events;
    }

    public static void showHistory(int customerId) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT * FROM customer_history WHERE customerId = " + customerId;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Event id: " + rs.getInt("eventId"));
            }
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
        }
    }
}
