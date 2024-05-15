package repository;

import database.DatabaseConfiguration;
import model.TheatrePlay;

import java.sql.Connection;
import java.sql.Statement;

public class TheatrePlayRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS theatrePlays" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, intermission boolean, qa boolean, eventID int, FOREIGN KEY (eventID) REFERENCES events(id))";
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

    public static void addTheatrePlay(boolean intermission, boolean qa, int eventID) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addTheatrePlaySql = "INSERT INTO theatrePlays (intermission, qa, eventID) VALUES (" + intermission + ", " + qa + ", " + eventID + ")";
            stmt = connection.createStatement();
            stmt.execute(addTheatrePlaySql);
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addTheatrePlay(TheatrePlay theatrePlay) {
        EventRepository.addEvent(theatrePlay);
        addTheatrePlay(theatrePlay.getIntermission(), theatrePlay.getQA(), EventRepository.getEventId(theatrePlay));
    }

    public static void deleteTheatrePlay(int theatrePlayID) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteTheatrePlaySql = "DELETE FROM theatrePlays WHERE id = " + theatrePlayID;
            stmt = connection.createStatement();
            stmt.execute(deleteTheatrePlaySql);
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteTheatrePlay(TheatrePlay theatrePlay) {
        deleteTheatrePlay(EventRepository.getEventId(theatrePlay));
        EventRepository.deleteEvent(theatrePlay);
    }
}