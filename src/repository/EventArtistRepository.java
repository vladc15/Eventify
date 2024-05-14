package repository;

import database.DatabaseConfiguration;
import model.Event;
import user.Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventArtistRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS event_artists" +
                    "(eventID int, artistID int, PRIMARY KEY(eventID, artistID), FOREIGN KEY(eventID) REFERENCES events(id), FOREIGN KEY(artistID) REFERENCES artists(id))";
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

    public static void addEventArtist(int eventID, int artistID) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String insertEventArtistSql = "INSERT INTO event_artists(eventID, artistID) VALUES(" + eventID + "," + artistID + ")";
            stmt = connection.createStatement();
            stmt.execute(insertEventArtistSql);
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

    public static void deleteEventArtist(int eventID, int artistID) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteEventArtistSql = "DELETE FROM event_artists WHERE eventID = " + eventID + " AND artistID = " + artistID;
            stmt = connection.createStatement();
            stmt.execute(deleteEventArtistSql);
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

    public static List<Artist> getArtistsByEventID(int eventID) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Artist> artists = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectArtistsSql = "SELECT * FROM event_artists WHERE eventID=" + eventID;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectArtistsSql);
            while (rs.next()) {
                int artistID = rs.getInt("artistID");
                Artist artist = (Artist)UserRepository.getUserById(artistID);
                artists.add(artist);
            }
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
        return null;
    }

    public static List<Integer> getArtistIdsById(int eventID) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> artistIds = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectArtistsSql = "SELECT * FROM event_artists WHERE eventID=" + eventID;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectArtistsSql);
            while (rs.next()) {
                int artistID = rs.getInt("artistID");
                artistIds.add(artistID);
            }
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
        return null;
    }

    public static List<Integer> getEventIdsByArtistId(int artistID) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> eventIds = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectEventsSql = "SELECT * FROM event_artists WHERE artistID=" + artistID;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectEventsSql);
            while (rs.next()) {
                int eventID = rs.getInt("eventID");
                eventIds.add(eventID);
            }
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
        return null;
    }

    public static List<Event> getEventsByArtistId(int artistID) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Event> events = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectEventsSql = "SELECT * FROM event_artists WHERE artistID=" + artistID;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectEventsSql);
            while (rs.next()) {
                int eventID = rs.getInt("eventID");
                Event event = EventRepository.getEventById(eventID);
                events.add(event);
            }
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
        return null;
    }

    public static void showEventArtists(int eventID) {
        List<Artist> artists = getArtistsByEventID(eventID);
        for (Artist artist : artists) {
            System.out.println(artist);
        }
    }
}
