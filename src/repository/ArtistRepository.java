package repository;

import database.DatabaseConfiguration;
import user.Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ArtistRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS artists" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, bio varchar(30), genre varchar(30), rating double, userId int, FOREIGN KEY (userId) REFERENCES users(id))";
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
            String insertArtistSql = "INSERT INTO artists(bio, genre, rating, userId) VALUES('" + artist.getBio() + "', '" + artist.getGenre() + "', " + artist.getRating() + ", " + UserRepository.getUserId(artist) + ")";
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


}
