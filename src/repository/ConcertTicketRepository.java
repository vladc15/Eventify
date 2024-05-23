package repository;

import database.DatabaseConfiguration;
import model.ConcertTicket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ConcertTicketRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS concert_tickets" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, ticketID int, afterPartyPrice double, meetAndGreetPrice double, " +
                    "FOREIGN KEY (ticketID) REFERENCES tickets(id))";
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

    public static void addConcertTicket(int ticketID, double afterPartyPrice, double meetAndGreetPrice) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String addConcertTicketSql = "INSERT INTO concert_tickets (ticketID, afterPartyPrice, meetAndGreetPrice) VALUES (" + ticketID + ", " + afterPartyPrice + ", " + meetAndGreetPrice + ")";
            //stmt = connection.createStatement();
            //stmt.execute(addConcertTicketSql);
            String addConcertTicketSql = "INSERT INTO concert_tickets (ticketID, afterPartyPrice, meetAndGreetPrice) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(addConcertTicketSql);
            stmt.setInt(1, ticketID);
            stmt.setDouble(2, afterPartyPrice);
            stmt.setDouble(3, meetAndGreetPrice);
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

    public static void addConcertTicket(ConcertTicket concertTicket) {
        TicketRepository.addTicket(concertTicket);
        addConcertTicket(TicketRepository.getTicketId(concertTicket), concertTicket.getAfterPartyPrice(), concertTicket.getMeetAndGreetPrice());
    }

    public static void updateAfterPartyPrice(int ticketID, double afterPartyPrice) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateAfterPartyPriceSql = "UPDATE concert_tickets SET afterPartyPrice = " + afterPartyPrice + " WHERE ticketID = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(updateAfterPartyPriceSql);
            String updateAfterPartyPriceSql = "UPDATE concert_tickets SET afterPartyPrice = ? WHERE ticketID = ?";
            stmt = connection.prepareStatement(updateAfterPartyPriceSql);
            stmt.setDouble(1, afterPartyPrice);
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

    public static void updateMeetAndGreetPrice(int ticketID, double meetAndGreetPrice) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateMeetAndGreetPriceSql = "UPDATE concert_tickets SET meetAndGreetPrice = " + meetAndGreetPrice + " WHERE ticketID = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(updateMeetAndGreetPriceSql);
            String updateMeetAndGreetPriceSql = "UPDATE concert_tickets SET meetAndGreetPrice = ? WHERE ticketID = ?";
            stmt = connection.prepareStatement(updateMeetAndGreetPriceSql);
            stmt.setDouble(1, meetAndGreetPrice);
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

    public static void deleteConcertTicket(int ticketID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteConcertTicketSql = "DELETE FROM concert_tickets WHERE ticketID = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(deleteConcertTicketSql);
            String deleteConcertTicketSql = "DELETE FROM concert_tickets WHERE ticketID = ?";
            stmt = connection.prepareStatement(deleteConcertTicketSql);
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

    public static void deleteConcertTicket(ConcertTicket concertTicket) {
        deleteConcertTicket(TicketRepository.getTicketId(concertTicket));
        //TicketRepository.deleteTicket(concertTicket);
    }
}
