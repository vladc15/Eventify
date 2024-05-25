package repository;

import database.DatabaseConfiguration;
import model.Location;

import javax.naming.ldap.PagedResultsControl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS locations" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, address varchar(30) NOT NULL" +
                    ", city varchar(30), totalCapacity int, capacity_rows int, capacity_columns int)";
            stmt = connection.createStatement();
            stmt.executeUpdate(createTableSql);
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

    public static void addLocation(Location location) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String insertLocationSql = "INSERT INTO locations(address, city, totalCapacity, capacity_rows, capacity_columns) VALUES('" +
            //        location.getAddress() + "','" + location.getCity() + "'," + location.getTotalCapacity() + "," +
            //        location.getRows() + "," + location.getColumns() + ")";
            //stmt = connection.createStatement();
            //stmt.execute(insertLocationSql);
            String insertLocationSql = "INSERT INTO locations(address, city, totalCapacity, capacity_rows, capacity_columns) VALUES(?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(insertLocationSql);
            stmt.setString(1, location.getAddress());
            stmt.setString(2, location.getCity());
            stmt.setInt(3, location.getTotalCapacity());
            stmt.setInt(4, location.getRows());
            stmt.setInt(5, location.getColumns());
            stmt.executeUpdate();
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

    public static Location getLocationById(int id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Location location = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectLocationSql = "SELECT * FROM locations WHERE id=" + id;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectLocationSql);
            String selectLocationSql = "SELECT * FROM locations WHERE id=?";
            stmt = connection.prepareStatement(selectLocationSql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                location = new Location(rs.getInt("id"), rs.getString("address"), rs.getString("city"),
                        rs.getInt("totalCapacity"), rs.getInt("capacity_rows"), rs.getInt("capacity_columns"));
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
        return location;
    }

    public static int getLocationId(Location location) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int locationId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectLocationSql = "SELECT * FROM locations WHERE address='" + location.getAddress() + "' AND city='" + location.getCity() + "'";
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectLocationSql);
            String selectLocationSql = "SELECT * FROM locations WHERE address=? AND city=?";
            stmt = connection.prepareStatement(selectLocationSql);
            stmt.setString(1, location.getAddress());
            stmt.setString(2, location.getCity());
            rs = stmt.executeQuery();
            if (rs.next()) {
                locationId = rs.getInt("id");
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
        return locationId;
    }

    public static void updateLocation(Location location) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateLocationSql = "UPDATE locations SET address='" + location.getAddress() + "', city='" +
            //        location.getCity() + "', totalCapacity=" + location.getTotalCapacity() + ", capacity_rows=" + location.getRows() +
            //        ", capacity_columns=" + location.getColumns() + " WHERE id=" + location.getLocationId();
            //stmt = connection.createStatement();
            //stmt.execute(updateLocationSql);
            String updateLocationSql = "UPDATE locations SET address=?, city=?, totalCapacity=?, capacity_rows=?, capacity_columns=? WHERE id=?";
            stmt = connection.prepareStatement(updateLocationSql);
            stmt.setString(1, location.getAddress());
            stmt.setString(2, location.getCity());
            stmt.setInt(3, location.getTotalCapacity());
            stmt.setInt(4, location.getRows());
            stmt.setInt(5, location.getColumns());
            stmt.setInt(6, location.getLocationId());
            stmt.executeUpdate();
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

    public static void updateLocationWithId(int id, Location location) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateLocationSql = "UPDATE locations SET address='" + location.getAddress() + "', city='" +
            //        location.getCity() + "', totalCapacity=" + location.getTotalCapacity() + ", capacity_rows=" + location.getRows() +
            //        ", capacity_columns=" + location.getColumns() + " WHERE id=" + id;
            //stmt = connection.createStatement();
            //stmt.execute(updateLocationSql);
            String updateLocationSql = "UPDATE locations SET address=?, city=?, totalCapacity=?, capacity_rows=?, capacity_columns=? WHERE id=?";
            stmt = connection.prepareStatement(updateLocationSql);
            stmt.setString(1, location.getAddress());
            stmt.setString(2, location.getCity());
            stmt.setInt(3, location.getTotalCapacity());
            stmt.setInt(4, location.getRows());
            stmt.setInt(5, location.getColumns());
            stmt.setInt(6, id);
            stmt.executeUpdate();
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

    public static void deleteLocation(int id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteLocationSql = "DELETE FROM locations WHERE id=" + id;
            //stmt = connection.createStatement();
            //stmt.execute(deleteLocationSql);
            String deleteLocationSql = "DELETE FROM locations WHERE id=?";
            stmt = connection.prepareStatement(deleteLocationSql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
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

    public void deleteLocation(Location location) {
        deleteLocation(getLocationId(location));
    }

    public static void showLocations() {
        List<Location> locations = getLocations();
        for (Location location : locations) {
            System.out.println(location);
        }
    }

    public static List<Location> getLocations() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Location> locations = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectLocationSql = "SELECT * FROM locations";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectLocationSql);
            while (rs.next()) {
                Location location = new Location(rs.getInt("id"), rs.getString("address"), rs.getString("city"),
                        rs.getInt("totalCapacity"), rs.getInt("capacity_rows"), rs.getInt("capacity_columns"));
                locations.add(location);
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
        return locations;
    }
}
