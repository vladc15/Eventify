package repository;

import database.DatabaseConfiguration;
import model.FilmScreeningTicket;

import javax.naming.ldap.PagedResultsControl;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addFilmScreeningTicket(int ticketID, double qaPrice, double imaxPrice) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String addFilmScreeningTicketSql = "INSERT INTO filmScreening_tickets (ticketID, qaPrice, imaxPrice) VALUES (" + ticketID + ", " + qaPrice + ", " + imaxPrice + ")";
            //stmt = connection.createStatement();
            //stmt.execute(addFilmScreeningTicketSql);
            String addFilmScreeningTicketSql = "INSERT INTO filmScreening_tickets (ticketID, qaPrice, imaxPrice) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(addFilmScreeningTicketSql);
            stmt.setInt(1, ticketID);
            stmt.setDouble(2, qaPrice);
            stmt.setDouble(3, imaxPrice);
            stmt.executeQuery();
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

    public static void addFilmScreeningTicket(FilmScreeningTicket filmScreeningTicket) {
        TicketRepository.addTicket(filmScreeningTicket);
        addFilmScreeningTicket(TicketRepository.getTicketId(filmScreeningTicket), filmScreeningTicket.getQAPrice(), filmScreeningTicket.getImaxPrice());
    }

    public static void deleteFilmScreeningTicket(int ticketID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteFilmScreeningTicketSql = "DELETE FROM filmScreening_tickets WHERE ticketID = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(deleteFilmScreeningTicketSql);
            String deleteFilmScreeningTicketSql = "DELETE FROM filmScreening_tickets WHERE ticketID = ?";
            stmt = connection.prepareStatement(deleteFilmScreeningTicketSql);
            stmt.setInt(1, ticketID);
            stmt.executeQuery();
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

    public static void deleteFilmScreeningTicket(FilmScreeningTicket filmScreeningTicket) {
        deleteFilmScreeningTicket(TicketRepository.getTicketId(filmScreeningTicket));
        //TicketRepository.deleteTicket(TicketRepository.getTicketId(filmScreeningTicket));
    }

    public static void updateQaPrice(int ticketID, double qaPrice) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateQaPriceSql = "UPDATE filmScreening_tickets SET qaPrice = " + qaPrice + " WHERE ticketID = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(updateQaPriceSql);
            String updateQaPriceSql = "UPDATE filmScreening_tickets SET qaPrice = ? WHERE ticketID = ?";
            stmt = connection.prepareStatement(updateQaPriceSql);
            stmt.setDouble(1, qaPrice);
            stmt.setInt(2, ticketID);
            stmt.executeQuery();
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

    public static void updateImaxPrice(int ticketID, double imaxPrice) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateImaxPriceSql = "UPDATE filmScreening_tickets SET imaxPrice = " + imaxPrice + " WHERE ticketID = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(updateImaxPriceSql);
            String updateImaxPriceSql = "UPDATE filmScreening_tickets SET imaxPrice = ? WHERE ticketID = ?";
            stmt = connection.prepareStatement(updateImaxPriceSql);
            stmt.setDouble(1, imaxPrice);
            stmt.setInt(2, ticketID);
            stmt.executeQuery();
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
