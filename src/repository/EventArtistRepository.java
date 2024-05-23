package repository;

import database.DatabaseConfiguration;
import model.Event;
import user.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addEventArtist(int eventID, int artistID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String insertEventArtistSql = "INSERT INTO event_artists(eventID, artistID) VALUES(" + eventID + "," + artistID + ")";
            //stmt = connection.createStatement();
            //stmt.execute(insertEventArtistSql);
            String insertEventArtistSql = "INSERT INTO event_artists(eventID, artistID) VALUES(?, ?)";
            stmt = connection.prepareStatement(insertEventArtistSql);
            stmt.setInt(1, eventID);
            stmt.setInt(2, artistID);
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

    public static void deleteEventArtist(int eventID, int artistID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteEventArtistSql = "DELETE FROM event_artists WHERE eventID = " + eventID + " AND artistID = " + artistID;
            //stmt = connection.createStatement();
            //stmt.execute(deleteEventArtistSql);
            String deleteEventArtistSql = "DELETE FROM event_artists WHERE eventID = ? AND artistID = ?";
            stmt = connection.prepareStatement(deleteEventArtistSql);
            stmt.setInt(1, eventID);
            stmt.setInt(2, artistID);
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

    public static List<Artist> getArtistsByEventID(int eventID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Artist> artists = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectArtistsSql = "SELECT * FROM event_artists ea LEFT JOIN artists a ON ea.artistID = a.id WHERE eventID=" + eventID;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectArtistsSql);
            String selectArtistsSql = "SELECT * FROM event_artists WHERE eventID=?";
            stmt = connection.prepareStatement(selectArtistsSql);
            stmt.setInt(1, eventID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("id");
                Artist artist = (Artist)UserRepository.getUserById(userId);
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return artists;
    }

    public static List<Integer> getArtistIdsById(int eventID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> artistIds = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectArtistsSql = "SELECT * FROM event_artists WHERE eventID=" + eventID;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectArtistsSql);
            String selectArtistsSql = "SELECT * FROM event_artists WHERE eventID=?";
            stmt = connection.prepareStatement(selectArtistsSql);
            stmt.setInt(1, eventID);
            rs = stmt.executeQuery();
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return artistIds;
    }

    public static List<Integer> getEventIdsByArtistId(int artistID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> eventIds = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectEventsSql = "SELECT * FROM event_artists WHERE artistID=" + artistID;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectEventsSql);
            String selectEventsSql = "SELECT * FROM event_artists WHERE artistID=?";
            stmt = connection.prepareStatement(selectEventsSql);
            stmt.setInt(1, artistID);
            rs = stmt.executeQuery();
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return eventIds;
    }

    public static List<Event> getEventsByArtistId(int artistID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Event> events = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectEventsSql = "SELECT * FROM event_artists WHERE artistID=" + artistID;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectEventsSql);
            String selectEventsSql = "SELECT * FROM event_artists WHERE artistID=?";
            stmt = connection.prepareStatement(selectEventsSql);
            stmt.setInt(1, artistID);
            rs = stmt.executeQuery();
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    public static void showEventArtists(int eventID) {
        List<Artist> artists = getArtistsByEventID(eventID);
        for (Artist artist : artists) {
            System.out.println(artist);
        }
    }
}
