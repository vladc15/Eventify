package repository;

import database.DatabaseConfiguration;
import model.Location;

import java.sql.Connection;
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
                    ", city varchar(30), totalCapacity int, rows int, columns int)";
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

    public static void addLocation(Location location) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String insertLocationSql = "INSERT INTO locations(address, city, totalCapacity, rows, columns) VALUES('" +
                    location.getAddress() + "','" + location.getCity() + "'," + location.getTotalCapacity() + "," +
                    location.getRows() + "," + location.getColumns() + ")";
            stmt = connection.createStatement();
            stmt.execute(insertLocationSql);
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

    public static Location getLocationById(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectLocationSql = "SELECT * FROM locations WHERE id=" + id;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectLocationSql);
            if (rs.next()) {
                Location location = new Location(rs.getInt("id"), rs.getString("address"), rs.getString("city"),
                        rs.getInt("totalCapacity"), rs.getInt("rows"), rs.getInt("columns"));
                return location;
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

    public static int getLocationId(Location location) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectLocationSql = "SELECT * FROM locations WHERE address='" + location.getAddress() + "' AND city='" + location.getCity() + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectLocationSql);
            if (rs.next()) {
                return rs.getInt("id");
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
        return -1;
    }

    public static void updateLocation(Location location) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateLocationSql = "UPDATE locations SET address='" + location.getAddress() + "', city='" +
                    location.getCity() + "', totalCapacity=" + location.getTotalCapacity() + ", rows=" + location.getRows() +
                    ", columns=" + location.getColumns() + " WHERE id=" + location.getLocationId();
            stmt = connection.createStatement();
            stmt.execute(updateLocationSql);
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

    public static void updateLocationWithId(int id, Location location) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateLocationSql = "UPDATE locations SET address='" + location.getAddress() + "', city='" +
                    location.getCity() + "', totalCapacity=" + location.getTotalCapacity() + ", rows=" + location.getRows() +
                    ", columns=" + location.getColumns() + " WHERE id=" + id;
            stmt = connection.createStatement();
            stmt.execute(updateLocationSql);
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

    public static void deleteLocation(int id) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteLocationSql = "DELETE FROM locations WHERE id=" + id;
            stmt = connection.createStatement();
            stmt.execute(deleteLocationSql);
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

    public static void showLocations() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectLocationSql = "SELECT * FROM locations";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectLocationSql);
            while (rs.next()) {
                System.out.println("Location id: " + rs.getInt("id") + ", address: " + rs.getString("address") +
                        ", city: " + rs.getString("city") + ", total capacity: " + rs.getInt("totalCapacity") +
                        ", rows: " + rs.getInt("rows") + ", columns: " + rs.getInt("columns"));
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
                        rs.getInt("totalCapacity"), rs.getInt("rows"), rs.getInt("columns"));
                locations.add(location);
            }
            connection.close();
            return locations;
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
        }
        return null;
    }
}
