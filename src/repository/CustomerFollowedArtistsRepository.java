package repository;

import database.DatabaseConfiguration;
import user.Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerFollowedArtistsRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS customer_followed_artists" +
                    "(customerId int, artistId int, FOREIGN KEY (customerId) REFERENCES customers(id), FOREIGN KEY (artistId) REFERENCES artists(id)), PRIMARY KEY (customerId, artistId)";
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

    public void addCustomerFollowedArtist(int customerId, int artistId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String insertCustomerFollowedArtistSql = "INSERT INTO customer_followed_artists(customerId, artistId) VALUES(" +
                    customerId + "," + artistId + ")";
            stmt = connection.createStatement();
            stmt.execute(insertCustomerFollowedArtistSql);
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

    public void removeCustomerFollowedArtist(int customerId, int artistId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteCustomerFollowedArtistSql = "DELETE FROM customer_followed_artists WHERE customerId = " + customerId + " AND artistId = " + artistId;
            stmt = connection.createStatement();
            stmt.execute(deleteCustomerFollowedArtistSql);
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

    public static List<Artist> getCustomerFollowedArtists(int customerId) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Artist> artists = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectCustomerFollowedArtistsSql = "SELECT * FROM customer_followed_artists cfa LEFT JOIN artists a ON cfa.artistId = a.id WHERE cfa.customerId = " + customerId;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectCustomerFollowedArtistsSql);
            artists = new ArrayList<>();
            while (rs.next()) {
                Artist artist = (Artist)UserRepository.getUserById(rs.getInt("id"));
                artists.add(artist);
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
        return artists;
    }

    public void showCustomerFollowedArtists(int customerId) {
        List<Artist> artists = getCustomerFollowedArtists(customerId);
        if (artists != null) {
            for (Artist artist : artists) {
                System.out.println(artist);
            }
        }
    }
}
