package repository;

import database.DatabaseConfiguration;
import model.FilmScreening;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class FilmScreeningRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS filmScreenings" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, dimension varchar(50), imax boolean, releaseYear int, premiere boolean, appropriateAge int, qa boolean, eventID int, FOREIGN KEY (eventID) REFERENCES events(id))";
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

    public static void addFilmScreening(String dimension, boolean imax, int releaseYear, boolean premiere, int appropriateAge, boolean qa, int eventID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String addFilmScreeningSql = "INSERT INTO filmScreenings (dimension, imax, releaseYear, premiere, appropriateAge, qa, eventID) VALUES ('" + dimension + "', " + imax + ", " + releaseYear + ", " + premiere + ", " + appropriateAge + ", " + qa + ", " + eventID + ")";
            //stmt = connection.createStatement();
            //stmt.execute(addFilmScreeningSql);
            String addFilmScreeningSql = "INSERT INTO filmScreenings (dimension, imax, releaseYear, premiere, appropriateAge, qa, eventID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(addFilmScreeningSql);
            stmt.setString(1, dimension);
            stmt.setBoolean(2, imax);
            stmt.setInt(3, releaseYear);
            stmt.setBoolean(4, premiere);
            stmt.setInt(5, appropriateAge);
            stmt.setBoolean(6, qa);
            stmt.setInt(7, eventID);
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addFilmScreening(FilmScreening filmScreening) {
        EventRepository.addEvent(filmScreening);
        addFilmScreening(filmScreening.getDimension(), filmScreening.getImax(), filmScreening.getReleaseYear(), filmScreening.getPremiere(), filmScreening.getAppropriateAge(), filmScreening.getQA(), EventRepository.getEventId(filmScreening));
    }

    public static void deleteFilmScreening(int filmScreeningId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteFilmScreeningSql = "DELETE FROM filmScreenings WHERE id = " + filmScreeningId;
            //stmt = connection.createStatement();
            //stmt.execute(deleteFilmScreeningSql);
            String deleteFilmScreeningSql = "DELETE FROM filmScreenings WHERE id = ?";
            stmt = connection.prepareStatement(deleteFilmScreeningSql);
            stmt.setInt(1, filmScreeningId);
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFilmScreening(FilmScreening filmScreening) {
        deleteFilmScreening(EventRepository.getEventId(filmScreening));
        //EventRepository.deleteEvent(filmScreening);
    }
}
