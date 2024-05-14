package repository;

import database.DatabaseConfiguration;
import model.Event;
import model.Review;
import model.Ticket;
import user.Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ArtistRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS artists" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, bio varchar(30), genre varchar(30), userId int, FOREIGN KEY (userId) REFERENCES users(id))";
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

    public static void addArtist(Artist artist) {
        UserRepository.addUser(artist);

        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String insertArtistSql = "INSERT INTO artists(bio, genre, userId) VALUES('" + artist.getBio() + "', '" + artist.getGenre() + "', " + UserRepository.getUserId(artist) + ")";
            stmt.execute(insertArtistSql);
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

    /*public static Artist getArtistById(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        Artist artist = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM artists WHERE id = " + id);
            if (rs.next()) {
                artist = new Artist(rs.getString("bio"), rs.getString("genre"), rs.getDouble("rating"));
                //artist.setId(rs.getInt("id"));
                artist.setUserId(rs.getInt("userId"));
            }
            return artist;
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
            return null;
        }
    }*/

    public List<Artist> getArtists() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Artist> artists = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM artists");
            while (rs.next()) {
                int userId = rs.getInt("userId");
                Artist artist = (Artist) UserRepository.getUserById(userId);
                artists.add(artist);
            }
            return artists;
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

    public String getBio(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        String bio = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT bio FROM artists WHERE id = " + id);
            if (rs.next()) {
                bio = rs.getString("bio");
            }
            return bio;
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

    public void updateBio(int id, String bio) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String updateBioSql = "UPDATE artists SET bio = '" + bio + "' WHERE id = " + id;
            stmt.execute(updateBioSql);
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

    public String getGenre(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        String genre = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT genre FROM artists WHERE id = " + id);
            if (rs.next()) {
                genre = rs.getString("genre");
            }
            return genre;
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

    public void updateGenre(int id, String genre) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String updateGenreSql = "UPDATE artists SET genre = '" + genre + "' WHERE id = " + id;
            stmt.execute(updateGenreSql);
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

    public int getArtistId(Artist artist) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        int artistId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT id FROM artists WHERE userId = " + UserRepository.getUserId(artist));
            if (rs.next()) {
                artistId = rs.getInt("id");
            }
            return artistId;
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
            return -1;
        }
    }

    public double getRating(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        double rating = 0.0;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM artists a LEFT JOIN event_artists ea LEFT JOIN reviews r ON a.id = ea.artistID AND ea.eventID = r.event_id WHERE a.id = " + id);
            int nr = 0;
            while (rs.next()) {
                rating += rs.getDouble("rating");
                nr++;
            }
            rating /= nr;
            return rating;
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
            return 0.0;
        }
    }

    public List<Review> getReviews(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM artists a LEFT JOIN event_artists ea LEFT JOIN reviews r ON a.id = ea.artistID AND ea.eventID = r.event_id WHERE a.id = " + id);
            while (rs.next()) {
                Review review = new Review(rs.getInt("r.id"), EventRepository.getEventById(rs.getInt("r.event_id")), UserRepository.getUserById(rs.getInt("r.user_id")), rs.getDouble("r.rating"), rs.getString("r.comment"));
                reviews.add(review);
            }
            return reviews;
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

    public List<Event> getEvents(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Event> events = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM event_artists WHERE artistID = " + id);
            while (rs.next()) {
                Event event = EventRepository.getEventById(rs.getInt("eventID"));
                events.add(event);
            }
            return events;
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

    public List<Ticket> getTickets(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Ticket> tickets = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM event_artists ea LEFT JOIN tickets t ON ea.eventID = t.eventID WHERE ea.artistID = " + id);
            while (rs.next()) {
                Ticket ticket = TicketRepository.getTicketById(rs.getInt("t.id"));
                tickets.add(ticket);
            }
            return tickets;
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

}
