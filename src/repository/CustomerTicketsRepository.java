package repository;

import database.DatabaseConfiguration;
import model.Ticket;

import java.sql.Connection;
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
                    "(ticketId int, customerId int, FOREIGN KEY (ticketId) REFERENCES tickets(id), FOREIGN KEY (customerId) REFERENCES customers(id))";
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

    public void addCustomerTicket(int ticketId, int customerId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String insertCustomerTicketSql = "INSERT INTO customer_tickets(ticketId, customerId) VALUES(" +
                    ticketId + "," + customerId + ")";
            stmt = connection.createStatement();
            stmt.execute(insertCustomerTicketSql);
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

    public void deleteCustomerTicket(int ticketId, int customerId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteCustomerTicketSql = "DELETE FROM customer_tickets WHERE ticketId = " + ticketId + " AND customerId = " + customerId;
            stmt = connection.createStatement();
            stmt.execute(deleteCustomerTicketSql);
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

    public List<Integer> getTicketsIdsByCustomerId(int customerId) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> tickets = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectTicketsSql = "SELECT ticketId FROM customer_tickets WHERE customerId=" + customerId;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectTicketsSql);
            while (rs.next()) {
                tickets.add(rs.getInt("ticketId"));
            }
            connection.close();
            return tickets;
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
        }
        return null;
    }

    public static TreeSet<Ticket> getTicketsByCustomerId(int customerId) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        TreeSet<Ticket> tickets = new TreeSet<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectTicketsSql = "SELECT ticketId FROM customer_tickets WHERE customerId=" + customerId;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectTicketsSql);
            while (rs.next()) {
                Ticket ticket = new TicketRepository().getTicketById(rs.getInt("ticketId"));
                tickets.add(ticket);
            }
            connection.close();
            return tickets;
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
        }
        return null;
    }

    public void showTicketsByCustomerId(int customerId) {
        TreeSet<Ticket> tickets = getTicketsByCustomerId(customerId);
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

}
