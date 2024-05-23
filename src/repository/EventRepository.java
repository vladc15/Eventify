package repository;

import database.DatabaseConfiguration;
import model.*;
import user.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
                    "(id int PRIMARY KEY AUTO_INCREMENT, name varchar(100), description varchar(100), date varchar(50), time varchar(50), duration int, totalTickets int, availableTickets int, locationID int NULL, genre varchar(50), FOREIGN KEY (locationID) REFERENCES locations(id))";
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

    public static void addEvent(String name, String description, String date, String time, int duration, int totalTickets, int availableTickets, int locationID, String genre) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String addEventSql = "INSERT INTO events (name, description, date, time, duration, totalTickets, availableTickets, locationID, genre) VALUES ('" + name + "', '" + description + "', '" + date + "', '" + time + "', " + duration + ", " + totalTickets + ", " + availableTickets + ", " + locationID + ", '" + genre + "')";
            //stmt = connection.createStatement();
            //stmt.execute(addEventSql);
            stmt = connection.prepareStatement("INSERT INTO events (name, description, date, time, duration, totalTickets, availableTickets, locationID, genre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setInt(5, duration);
            stmt.setInt(6, totalTickets);
            stmt.setInt(7, availableTickets);
            stmt.setInt(8, locationID);
            stmt.setString(9, genre);
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

    public static void addEvent(Event event) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addEventSql = "INSERT INTO events (name, description, date, time, duration, totalTickets, availableTickets, locationID, genre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            if (event.getLocation() != null) {
                if (LocationRepository.getLocationId(event.getLocation()) == -1)
                    LocationRepository.addLocation(event.getLocation());
                //addEventSql = "INSERT INTO events (name, description, date, time, duration, totalTickets, availableTickets, locationID, genre) VALUES ('" + event.getName() + "', '" + event.getDescription() + "', '" + event.getDate() + "', '" + event.getTime() + "', " + event.getDuration() + ", " + event.getTotalTickets() + ", " + event.getAvailableTickets() + ", " + LocationRepository.getLocationId(event.getLocation()) + ", '" + event.getGenre() + "')";
            }
            //stmt = connection.createStatement();
            //stmt.execute(addEventSql);
            stmt = connection.prepareStatement(addEventSql);
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setString(3, event.getDate());
            stmt.setString(4, event.getTime());
            stmt.setInt(5, event.getDuration());
            stmt.setInt(6, event.getTotalTickets());
            stmt.setInt(7, event.getAvailableTickets());
            if (event.getLocation() != null)
                stmt.setInt(8, LocationRepository.getLocationId(event.getLocation()));
            else
                stmt.setInt(8, -1);
            stmt.setString(9, event.getGenre());
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

    public static void updateEventById(int eventId, Event event) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateEventSql = "UPDATE events SET name = '" + event.getName() + "', description = '" + event.getDescription() + "', date = '" + event.getDate() + "', time = '" + event.getTime() + "', duration = " + event.getDuration() + ", totalTickets = " + event.getTotalTickets() + ", availableTickets = " + event.getAvailableTickets() + ", locationID = " + LocationRepository.getLocationId(event.getLocation()) + ", genre = '" + event.getGenre() + "' WHERE id = " + eventId;
            //stmt = connection.createStatement();
            //stmt.execute(updateEventSql);
            String updateEventSql = "UPDATE events SET name = ?, description = ?, date = ?, time = ?, duration = ?, totalTickets = ?, availableTickets = ?, locationID = ?, genre = ? WHERE id = ?";
            stmt = connection.prepareStatement(updateEventSql);
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setString(3, event.getDate());
            stmt.setString(4, event.getTime());
            stmt.setInt(5, event.getDuration());
            stmt.setInt(6, event.getTotalTickets());
            stmt.setInt(7, event.getAvailableTickets());
            if (event.getLocation() != null)
                stmt.setInt(8, LocationRepository.getLocationId(event.getLocation()));
            else
                stmt.setInt(8, -1);
            stmt.setString(9, event.getGenre());
            stmt.setInt(10, eventId);
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

    public static void deleteEventById(int eventId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteEventSql = "DELETE FROM events WHERE id = " + eventId;
            //stmt = connection.createStatement();
            //stmt.execute(deleteEventSql);
            stmt = connection.prepareStatement("DELETE FROM events WHERE id = ?");
            stmt.setInt(1, eventId);
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

    public static int getEventId(Event event) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int eventId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String getEventIdSql = "SELECT id FROM events WHERE name = '" + event.getName() + "' AND description = '" + event.getDescription() + "' AND date = '" + event.getDate() + "' AND time = '" + event.getTime() + "' AND duration = " + event.getDuration() + " AND totalTickets = " + event.getTotalTickets() + " AND availableTickets = " + event.getAvailableTickets() + " AND locationID = " + LocationRepository.getLocationId(event.getLocation()) + " AND genre = '" + event.getGenre() + "'";
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(getEventIdSql);
            String getEventIdSql = "SELECT id FROM events WHERE name = ? AND description = ? AND date = ? AND time = ? AND duration = ? AND totalTickets = ? AND availableTickets = ? AND locationID = ? AND genre = ?";
            stmt = connection.prepareStatement(getEventIdSql);
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setString(3, event.getDate());
            stmt.setString(4, event.getTime());
            stmt.setInt(5, event.getDuration());
            stmt.setInt(6, event.getTotalTickets());
            stmt.setInt(7, event.getAvailableTickets());
            if (event.getLocation() != null)
                stmt.setInt(8, LocationRepository.getLocationId(event.getLocation()));
            else
                stmt.setInt(8, -1);
            stmt.setString(9, event.getGenre());
            rs = stmt.executeQuery();
            if (rs.next()) {
                eventId = rs.getInt("id");
            }
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
        return eventId;
    }

    public static void deleteEvent(Event event) {
        deleteEventById(getEventId(event));
    }

    public static Event getEventById(int id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Event event = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            /*rs = stmt.executeQuery("SELECT * FROM events e " +
                                        "LEFT JOIN concerts c ON e.id = c.eventID " +
                                        "LEFT JOIN filmScreenings f ON e.id = f.eventID " +
                                        "LEFT JOIN theatrePlays t ON e.id = t.eventID " +
                                        "LEFT JOIN event_artists ea ON e.id = ea.eventID " +
                                        "LEFT JOIN map_events me ON e.id = me.eventID WHERE e.id = " + id);*/
            String getEventByIdSql = "SELECT * FROM events e " +
                    "LEFT JOIN concerts c ON e.id = c.eventID " +
                    "LEFT JOIN filmScreenings f ON e.id = f.eventID " +
                    "LEFT JOIN theatrePlays t ON e.id = t.eventID " +
                    "LEFT JOIN event_artists ea ON e.id = ea.eventID " +
                    "LEFT JOIN map_events me ON e.id = me.eventID WHERE e.id = ?";
            stmt = connection.prepareStatement(getEventByIdSql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Location location = null;
                if (rs.getInt("locationID") != 0)
                    location = LocationRepository.getLocationById(rs.getInt("locationID"));
                if (rs.getString("isSeated") != null) {
                    event = new Concert(id, rs.getString("name"), rs.getString("description"), rs.getString("date"), rs.getString("time"), rs.getInt("duration"), rs.getInt("totalTickets"), rs.getInt("availableTickets"), location, EventArtistRepository.getArtistsByEventID(id), MapEventRepository.getMap(id), rs.getString("genre"), rs.getBoolean("isSeated"), rs.getBoolean("afterParty"), rs.getBoolean("meetAndGreet"));
                } else if (rs.getString("dimension") != null) {
                    event = new FilmScreening(id, rs.getString("name"), rs.getString("description"), rs.getString("date"), rs.getString("time"), rs.getInt("duration"), rs.getInt("totalTickets"), rs.getInt("availableTickets"), location, EventArtistRepository.getArtistsByEventID(id), MapEventRepository.getMap(id), rs.getString("genre"), rs.getString("dimension"), rs.getBoolean("imax"), rs.getInt("releaseYear"), rs.getBoolean("premiere"), rs.getInt("appropriateAge"), rs.getBoolean("qa"));
                } else {
                    event = new TheatrePlay(id, rs.getString("name"), rs.getString("description"), rs.getString("date"), rs.getString("time"), rs.getInt("duration"), rs.getInt("totalTickets"), rs.getInt("availableTickets"), location, EventArtistRepository.getArtistsByEventID(id), MapEventRepository.getMap(id), rs.getString("genre"), rs.getBoolean("intermission"), rs.getBoolean("qa"));
                }
            }
            connection.close();
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return event;
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

    public List<Review> getReviews(Event event) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * FROM reviews WHERE event_id = " + getEventId(event));
            stmt = connection.prepareStatement("SELECT * FROM reviews WHERE event_id = ?");
            stmt.setInt(1, getEventId(event));
            rs = stmt.executeQuery();
            while (rs.next()) {
                Review review = new Review(rs.getInt("id"), event, UserRepository.getUserById(rs.getInt("user_id")), rs.getDouble("rating"), rs.getString("comment"));
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }

    public List<Ticket> getTickets(Event event) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ticket> tickets = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * FROM tickets WHERE eventID = " + getEventId(event));
            stmt = connection.prepareStatement("SELECT * FROM tickets WHERE eventID = ?");
            stmt.setInt(1, getEventId(event));
            rs = stmt.executeQuery();
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
        } finally {
            try {
                if (connection != null) connection.close();
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tickets;
    }

}
