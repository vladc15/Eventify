package repository;

import database.DatabaseConfiguration;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS events" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, name varchar(100), description varchar(100), date varchar(50), time varchar(50), duration int, totalTickets int, availableTickets int, locationID int, genre varchar(50))";
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

    public static void addEvent(String name, String description, String date, String time, int duration, int totalTickets, int availableTickets, int locationID, String genre) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addEventSql = "INSERT INTO events (name, description, date, time, duration, totalTickets, availableTickets, locationID, genre) VALUES ('" + name + "', '" + description + "', '" + date + "', '" + time + "', " + duration + ", " + totalTickets + ", " + availableTickets + ", " + locationID + ", '" + genre + "')";
            stmt = connection.createStatement();
            stmt.execute(addEventSql);
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

    public static void addEvent(Event event) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addEventSql = "INSERT INTO events (name, description, date, time, duration, totalTickets, availableTickets, locationID, genre) VALUES ('" + event.getName() + "', '" + event.getDescription() + "', '" + event.getDate() + "', '" + event.getTime() + "', " + event.getDuration() + ", " + event.getTotalTickets() + ", " + event.getAvailableTickets() + ", " + LocationRepository.getLocationId(event.getLocation()) + ", '" + event.getGenre() + "')";
            stmt = connection.createStatement();
            stmt.execute(addEventSql);
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

    public static void updateEventById(int eventId, Event event) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateEventSql = "UPDATE events SET name = '" + event.getName() + "', description = '" + event.getDescription() + "', date = '" + event.getDate() + "', time = '" + event.getTime() + "', duration = " + event.getDuration() + ", totalTickets = " + event.getTotalTickets() + ", availableTickets = " + event.getAvailableTickets() + ", locationID = " + LocationRepository.getLocationId(event.getLocation()) + ", genre = '" + event.getGenre() + "' WHERE id = " + eventId;
            stmt = connection.createStatement();
            stmt.execute(updateEventSql);
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

    public static void deleteEventById(int eventId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteEventSql = "DELETE FROM events WHERE id = " + eventId;
            stmt = connection.createStatement();
            stmt.execute(deleteEventSql);
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

    public static int getEventId(Event event) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        int eventId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            String getEventIdSql = "SELECT id FROM events WHERE name = '" + event.getName() + "' AND description = '" + event.getDescription() + "' AND date = '" + event.getDate() + "' AND time = '" + event.getTime() + "' AND duration = " + event.getDuration() + " AND totalTickets = " + event.getTotalTickets() + " AND availableTickets = " + event.getAvailableTickets() + " AND locationID = " + LocationRepository.getLocationId(event.getLocation()) + " AND genre = '" + event.getGenre() + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(getEventIdSql);
            if (rs.next()) {
                eventId = rs.getInt("id");
            }
            return eventId;
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
            return -1;
        }
    }

    public static void deleteEvent(Event event) {
        deleteEventById(getEventId(event));
    }

    public static Event getEventById(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        Event event = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM events e LEFT JOIN concerts c LEFT JOIN filmScreenings f LEFT JOIN theaterPlays t LEFT JOIN event_artists ea LEFT JOIN map_events me ON (e.id = c.eventID OR e.id = f.eventID OR e.id = t.eventID) AND e.id = ea.eventID AND e.id = me.eventID WHERE e.id = " + id);
            if (rs.next()) {
                if (rs.getString("isSeated") != null) {
                    event = new Concert(id, rs.getString("name"), rs.getString("description"), rs.getString("date"), rs.getString("time"), rs.getInt("duration"), rs.getInt("totalTickets"), rs.getInt("availableTickets"), LocationRepository.getLocationById(rs.getInt("locationID")), EventArtistRepository.getArtistsByEventID(id), MapEventRepository.getMap(id), rs.getString("genre"), rs.getBoolean("isSeated"), rs.getBoolean("afterParty"), rs.getBoolean("meetAndGreet"));
                } else if (rs.getString("dimension") != null) {
                    event = new FilmScreening(id, rs.getString("name"), rs.getString("description"), rs.getString("date"), rs.getString("time"), rs.getInt("duration"), rs.getInt("totalTickets"), rs.getInt("availableTickets"), LocationRepository.getLocationById(rs.getInt("locationID")), EventArtistRepository.getArtistsByEventID(id), MapEventRepository.getMap(id), rs.getString("genre"), rs.getString("dimension"), rs.getBoolean("imax"), rs.getInt("releaseYear"), rs.getBoolean("premiere"), rs.getInt("appropriateAge"), rs.getBoolean("qa"));
                } else {
                    event = new TheatrePlay(id, rs.getString("name"), rs.getString("description"), rs.getString("date"), rs.getString("time"), rs.getInt("duration"), rs.getInt("totalTickets"), rs.getInt("availableTickets"), LocationRepository.getLocationById(rs.getInt("locationID")), EventArtistRepository.getArtistsByEventID(id), MapEventRepository.getMap(id), rs.getString("genre"), rs.getBoolean("intermission"), rs.getBoolean("qa"));
                }
            }
            connection.close();
            return event;
        } catch (Exception e) {
            if (connection != null) {
                try {
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
            return null;
        }
    }

    public List<Event> getEvents() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Event> events = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM events");
            while (rs.next()) {
                Event event = getEventById(rs.getInt("id"));
                events.add(event);
            }
        } catch (Exception e) {
            if (connection != null) {
                try {
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
        }
        return events;
    }

    public List<Review> getReviews(Event event) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM reviews WHERE eventID = " + getEventId(event));
            while (rs.next()) {
                Review review = new Review(rs.getInt("id"), event, UserRepository.getUserById(rs.getInt("userID")), rs.getDouble("rating"), rs.getString("comment"));
                reviews.add(review);
            }
        } catch (Exception e) {
            if (connection != null) {
                try {
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
        }
        return reviews;
    }

    public List<Ticket> getTickets(Event event) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Ticket> tickets = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM tickets WHERE eventID = " + getEventId(event));
            while (rs.next()) {
                Ticket ticket = TicketRepository.getTicketById(rs.getInt("id"));
                tickets.add(ticket);
            }
        } catch (Exception e) {
            if (connection != null) {
                try {
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
        }
        return tickets;
    }

}
