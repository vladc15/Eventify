package repository;

import database.DatabaseConfiguration;
import model.ConcertTicket;

import java.sql.Connection;
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addConcertTicketSql = "INSERT INTO concert_tickets (ticketID, afterPartyPrice, meetAndGreetPrice) VALUES (" + ticketID + ", " + afterPartyPrice + ", " + meetAndGreetPrice + ")";
            stmt = connection.createStatement();
            stmt.execute(addConcertTicketSql);
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateAfterPartyPriceSql = "UPDATE concert_tickets SET afterPartyPrice = " + afterPartyPrice + " WHERE ticketID = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(updateAfterPartyPriceSql);
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateMeetAndGreetPriceSql = "UPDATE concert_tickets SET meetAndGreetPrice = " + meetAndGreetPrice + " WHERE ticketID = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(updateMeetAndGreetPriceSql);
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteConcertTicketSql = "DELETE FROM concert_tickets WHERE ticketID = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(deleteConcertTicketSql);
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
