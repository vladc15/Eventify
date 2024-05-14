package repository;

import database.DatabaseConfiguration;
import model.FilmScreeningTicket;

import java.sql.Connection;
import java.sql.Statement;

public class FilmScreeningTicketRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS filmScreening_tickets" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, ticketID int, qaPrice double, imaxPrice double, FOREIGN KEY (ticketID) REFERENCES tickets(id))";
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

    public static void addFilmScreeningTicket(int ticketID, double qaPrice, double imaxPrice) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addFilmScreeningTicketSql = "INSERT INTO filmScreening_tickets (ticketID, qaPrice, imaxPrice) VALUES (" + ticketID + ", " + qaPrice + ", " + imaxPrice + ")";
            stmt = connection.createStatement();
            stmt.execute(addFilmScreeningTicketSql);
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
        }
    }

    public static void addFilmScreeningTicket(FilmScreeningTicket filmScreeningTicket) {
        TicketRepository.addTicket(filmScreeningTicket);
        addFilmScreeningTicket(TicketRepository.getTicketId(filmScreeningTicket), filmScreeningTicket.getQAPrice(), filmScreeningTicket.getImaxPrice());
    }

    public static void deleteFilmScreeningTicket(int ticketID) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteFilmScreeningTicketSql = "DELETE FROM filmScreening_tickets WHERE ticketID = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(deleteFilmScreeningTicketSql);
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
        }
    }

    public static void deleteFilmScreeningTicket(FilmScreeningTicket filmScreeningTicket) {
        deleteFilmScreeningTicket(TicketRepository.getTicketId(filmScreeningTicket));
        TicketRepository.deleteTicket(TicketRepository.getTicketId(filmScreeningTicket));
    }

    public static void updateQaPrice(int ticketID, double qaPrice) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateQaPriceSql = "UPDATE filmScreening_tickets SET qaPrice = " + qaPrice + " WHERE ticketID = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(updateQaPriceSql);
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
        }
    }

    public static void updateImaxPrice(int ticketID, double imaxPrice) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateImaxPriceSql = "UPDATE filmScreening_tickets SET imaxPrice = " + imaxPrice + " WHERE ticketID = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(updateImaxPriceSql);
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
        }
    }


}
