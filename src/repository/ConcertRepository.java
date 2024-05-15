package repository;

import database.DatabaseConfiguration;
import model.Concert;

import java.sql.Connection;
import java.sql.Statement;

public class ConcertRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS concerts" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, isSeated boolean, afterParty boolean, meetAndGreet boolean, eventID int, FOREIGN KEY (eventID) REFERENCES events(id))";
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

    public static void addConcert(boolean isSeated, boolean afterParty, boolean meetAndGreet, int eventID) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addConcertSql = "INSERT INTO concerts (isSeated, afterParty, meetAndGreet, eventID) VALUES (" + isSeated + ", " + afterParty + ", " + meetAndGreet + ", " + eventID + ")";
            stmt = connection.createStatement();
            stmt.execute(addConcertSql);
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

    public static void addConcert(Concert concert) {
        EventRepository.addEvent(concert);
        int eventID = EventRepository.getEventId(concert);
        addConcert(concert.getIsSeated(), concert.getAfterParty(), concert.getMeetAndGreet(), eventID);
    }

    public static void deleteConcert(int concertId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteConcertSql = "DELETE FROM concerts WHERE id = " + concertId;
            stmt = connection.createStatement();
            stmt.execute(deleteConcertSql);
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

    public static void deleteConcert(Concert concert) {
        deleteConcert(EventRepository.getEventId(concert));
        EventRepository.deleteEvent(concert);
    }
}
