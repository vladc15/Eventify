package repository;

import database.DatabaseConfiguration;
import model.TheatrePlayTicket;

import javax.naming.ldap.PagedResultsControl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class TheatrePlayTicketRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS theatre_play_tickets" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, ticketID int, qaPrice double, FOREIGN KEY (ticketID) REFERENCES tickets(id))";
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

    public static void addTheatrePlayTicket(int ticketID, double qaPrice) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String addTheatrePlayTicketSql = "INSERT INTO theatre_play_tickets (ticketID, qaPrice) VALUES (" + ticketID + ", " + qaPrice + ")";
            //stmt = connection.createStatement();
            //stmt.execute(addTheatrePlayTicketSql);
            String addTheatrePlayTicketSql = "INSERT INTO theatre_play_tickets (ticketID, qaPrice) VALUES (?, ?)";
            stmt = connection.prepareStatement(addTheatrePlayTicketSql);
            stmt.setInt(1, ticketID);
            stmt.setDouble(2, qaPrice);
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

    public static void addTheatrePlayTicket(TheatrePlayTicket theatrePlayTicket) {
        TicketRepository.addTicket(theatrePlayTicket);
        addTheatrePlayTicket(TicketRepository.getTicketId(theatrePlayTicket), theatrePlayTicket.getQaPrice());
    }

    public static void deleteTheatrePlayTicket(int ticketID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteTheatrePlayTicketSql = "DELETE FROM theatre_play_tickets WHERE ticketID = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(deleteTheatrePlayTicketSql);
            String deleteTheatrePlayTicketSql = "DELETE FROM theatre_play_tickets WHERE ticketID = ?";
            stmt = connection.prepareStatement(deleteTheatrePlayTicketSql);
            stmt.setInt(1, ticketID);
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

    public static void deleteTheatrePlayTicket(TheatrePlayTicket theatrePlayTicket) {
        deleteTheatrePlayTicket(TicketRepository.getTicketId(theatrePlayTicket));
        //TicketRepository.deleteTicket(theatrePlayTicket);
    }

    public static void updateQAPrice(int ticketID, double qaPrice) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateQAPriceSql = "UPDATE theatre_play_tickets SET qaPrice = " + qaPrice + " WHERE ticketID = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(updateQAPriceSql);
            String updateQAPriceSql = "UPDATE theatre_play_tickets SET qaPrice = ? WHERE ticketID = ?";
            stmt = connection.prepareStatement(updateQAPriceSql);
            stmt.setDouble(1, qaPrice);
            stmt.setInt(2, ticketID);
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
