package repository;

import database.DatabaseConfiguration;
import model.Review;

import java.sql.Connection;
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
                    "FOREIGN KEY (event_id) REFERENCES event(event_id)," +
                    "FOREIGN KEY (user_id) REFERENCES user(user_id)" +
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO reviews (event_id, user_id, rating, comment) VALUES (" + eventId + ", " + userId + ", " + rating + ", '" + comment + "')");
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM reviews WHERE id = " + reviewId);
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM reviews WHERE user_id = " + UserRepository.getUserId(review.getUser()) + " AND event_id = " + EventRepository.getEventId(review.getEvent()) + " AND rating = " + review.getRating() + " AND comment = '" + review.getComment() + "'");
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
        Statement stmt = null;
        ResultSet rs = null;
        int reviewId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT id FROM reviews WHERE user_id = " + UserRepository.getUserId(review.getUser()) + " AND event_id = " + EventRepository.getEventId(review.getEvent()) + " AND rating = " + review.getRating() + " AND comment = '" + review.getComment() + "'");
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
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE reviews SET rating = " + newRating + ", comment = '" + newComment + "' WHERE user_id = " + UserRepository.getUserId(review.getUser()) + " AND event_id = " + EventRepository.getEventId(review.getEvent()) + " AND rating = " + review.getRating() + " AND comment = '" + review.getComment() + "'");
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
        Statement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM reviews WHERE event_id = " + eventId);
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
        Statement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM reviews WHERE user_id = " + userId);
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

}
