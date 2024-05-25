package repository;

import database.DatabaseConfiguration;
import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CustomerTicketsRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS customer_tickets" +
                    "(ticketId int, customerId int, FOREIGN KEY (ticketId) REFERENCES tickets(id), FOREIGN KEY (customerId) REFERENCES customers(id), PRIMARY KEY (ticketId, customerId))";
            stmt = connection.createStatement();
            stmt.executeUpdate(createTableSql);
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

    public void addCustomerTicket(int ticketId, int customerId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String insertCustomerTicketSql = "INSERT INTO customer_tickets(ticketId, customerId) VALUES(" +
            //        ticketId + "," + customerId + ")";
            //stmt = connection.createStatement();
            //stmt.execute(insertCustomerTicketSql);
            String insertCustomerTicketSql = "INSERT INTO customer_tickets(ticketId, customerId) VALUES(?, ?)";
            stmt = connection.prepareStatement(insertCustomerTicketSql);
            stmt.setInt(1, ticketId);
            stmt.setInt(2, customerId);
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

    public void deleteCustomerTicket(int ticketId, int customerId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteCustomerTicketSql = "DELETE FROM customer_tickets WHERE ticketId = " + ticketId + " AND customerId = " + customerId;
            //stmt = connection.createStatement();
            //stmt.execute(deleteCustomerTicketSql);
            String deleteCustomerTicketSql = "DELETE FROM customer_tickets WHERE ticketId = ? AND customerId = ?";
            stmt = connection.prepareStatement(deleteCustomerTicketSql);
            stmt.setInt(1, ticketId);
            stmt.setInt(2, customerId);
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

    public List<Integer> getTicketsIdsByCustomerId(int customerId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> tickets = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectTicketsSql = "SELECT ticketId FROM customer_tickets WHERE customerId=" + customerId;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectTicketsSql);
            String selectTicketsSql = "SELECT ticketId FROM customer_tickets WHERE customerId=?";
            stmt = connection.prepareStatement(selectTicketsSql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(rs.getInt("ticketId"));
            }
            connection.close();
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
        return tickets;
    }

    public static TreeSet<Ticket> getTicketsByCustomerId(int customerId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TreeSet<Ticket> tickets = new TreeSet<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectTicketsSql = "SELECT ticketId FROM customer_tickets WHERE customerId=" + customerId;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectTicketsSql);
            String selectTicketsSql = "SELECT ticketId FROM customer_tickets WHERE customerId=?";
            stmt = connection.prepareStatement(selectTicketsSql);
            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket ticket = new TicketRepository().getTicketById(rs.getInt("ticketId"));
                tickets.add(ticket);
            }
            connection.close();
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
        return tickets;
    }

    public void showTicketsByCustomerId(int customerId) {
        TreeSet<Ticket> tickets = getTicketsByCustomerId(customerId);
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

}
