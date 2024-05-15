package repository;

import database.DatabaseConfiguration;
import model.TheatrePlayTicket;

import java.sql.Connection;
import java.sql.Statement;

public class TheatrePlayTicketRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS theatre_play_tickets" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, ticketID int, qaPrice double, FOREIGN KEY (ticketID) REFERENCES tickets(id)";
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

    public static void addTheatrePlayTicket(int ticketID, double qaPrice) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addTheatrePlayTicketSql = "INSERT INTO theatre_play_tickets (ticketID, qaPrice) VALUES (" + ticketID + ", " + qaPrice + ")";
            stmt = connection.createStatement();
            stmt.execute(addTheatrePlayTicketSql);
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteTheatrePlayTicketSql = "DELETE FROM theatre_play_tickets WHERE ticketID = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(deleteTheatrePlayTicketSql);
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
        TicketRepository.deleteTicket(theatrePlayTicket);
    }

    public static void updateQAPrice(int ticketID, double qaPrice) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateQAPriceSql = "UPDATE theatre_play_tickets SET qaPrice = " + qaPrice + " WHERE ticketID = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(updateQAPriceSql);
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
