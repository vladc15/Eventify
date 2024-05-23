package repository;

import database.DatabaseConfiguration;
import model.Review;
import user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS reviews (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "event_id INT," +
                    "user_id INT," +
                    "rating DOUBLE," +
                    "comment varchar(200)," +
                    "FOREIGN KEY (event_id) REFERENCES events(id)," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)" +
                    ")");
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

    public static void insertReview(int eventId, int userId, double rating, String comment) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //stmt.executeUpdate("INSERT INTO reviews (event_id, user_id, rating, comment) VALUES (" + eventId + ", " + userId + ", " + rating + ", '" + comment + "')");
            stmt = connection.prepareStatement("INSERT INTO reviews (event_id, user_id, rating, comment) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, eventId);
            stmt.setInt(2, userId);
            stmt.setDouble(3, rating);
            stmt.setString(4, comment);
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

    public static void deleteReviewById(int reviewId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //stmt.executeUpdate("DELETE FROM reviews WHERE id = " + reviewId);
            stmt = connection.prepareStatement("DELETE FROM reviews WHERE id = ?");
            stmt.setInt(1, reviewId);
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

    public static void deleteReview(Review review) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //stmt.executeUpdate("DELETE FROM reviews WHERE user_id = " + UserRepository.getUserId(review.getUser()) + " AND event_id = " + EventRepository.getEventId(review.getEvent()) + " AND rating = " + review.getRating() + " AND comment = '" + review.getComment() + "'");
            stmt = connection.prepareStatement("DELETE FROM reviews WHERE user_id = ? AND event_id = ? AND rating = ? AND comment = ?");
            stmt.setInt(1, UserRepository.getUserId(review.getUser()));
            stmt.setInt(2, EventRepository.getEventId(review.getEvent()));
            stmt.setDouble(3, review.getRating());
            stmt.setString(4, review.getComment());
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

    public static int getReviewId(Review review) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int reviewId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT id FROM reviews WHERE user_id = " + UserRepository.getUserId(review.getUser()) + " AND event_id = " + EventRepository.getEventId(review.getEvent()) + " AND rating = " + review.getRating() + " AND comment = '" + review.getComment() + "'");
            stmt = connection.prepareStatement("SELECT id FROM reviews WHERE user_id = ? AND event_id = ? AND rating = ? AND comment = ?");
            stmt.setInt(1, UserRepository.getUserId(review.getUser()));
            stmt.setInt(2, EventRepository.getEventId(review.getEvent()));
            stmt.setDouble(3, review.getRating());
            stmt.setString(4, review.getComment());
            rs = stmt.executeQuery();
            if (rs.next()) {
                reviewId = rs.getInt("id");
            }
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
        return reviewId;
    }

    public static void updateReview(Review review, double newRating, String newComment) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //stmt.executeUpdate("UPDATE reviews SET rating = " + newRating + ", comment = '" + newComment + "' WHERE user_id = " + UserRepository.getUserId(review.getUser()) + " AND event_id = " + EventRepository.getEventId(review.getEvent()) + " AND rating = " + review.getRating() + " AND comment = '" + review.getComment() + "'");
            stmt = connection.prepareStatement("UPDATE reviews SET rating = ?, comment = ? WHERE user_id = ? AND event_id = ? AND rating = ? AND comment = ?");
            stmt.setDouble(1, newRating);
            stmt.setString(2, newComment);
            stmt.setInt(3, UserRepository.getUserId(review.getUser()));
            stmt.setInt(4, EventRepository.getEventId(review.getEvent()));
            stmt.setDouble(5, review.getRating());
            stmt.setString(6, review.getComment());
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

    public static List<Review> getReviewsByEventId(int eventId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * FROM reviews WHERE event_id = " + eventId);
            stmt = connection.prepareStatement("SELECT * FROM reviews WHERE event_id = ?");
            stmt.setInt(1, eventId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Review review = new Review(rs.getInt("id"), EventRepository.getEventById(rs.getInt("event_id")), UserRepository.getUserById(rs.getInt("user_id")), rs.getDouble("rating"), rs.getString("comment"));
                reviews.add(review);
            }
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
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }

    public static List<Review> getReviewsByUserId(int userId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * FROM reviews WHERE user_id = " + userId);
            stmt = connection.prepareStatement("SELECT * FROM reviews WHERE user_id = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Review review = new Review(rs.getInt("id"), EventRepository.getEventById(rs.getInt("event_id")), UserRepository.getUserById(rs.getInt("user_id")), rs.getDouble("rating"), rs.getString("comment"));
                reviews.add(review);
            }
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
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }

    public static List<Review> getReviewsByUserId(int userId, User user) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<model.Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * FROM reviews WHERE user_id = " + userId);
            stmt = connection.prepareStatement("SELECT * FROM reviews WHERE user_id = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                model.Review review = new model.Review(rs.getInt("id"), EventRepository.getEventById(rs.getInt("event_id")), user, rs.getDouble("rating"), rs.getString("comment"));
                reviews.add(review);
            }
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
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }

}
