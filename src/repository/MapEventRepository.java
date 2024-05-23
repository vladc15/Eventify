package repository;

import database.DatabaseConfiguration;

import javax.naming.ldap.PagedResultsControl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MapEventRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS map_events" +
                    "(eventId int, mapKey varchar(100), mapValue double, FOREIGN KEY(eventId) REFERENCES events(id), PRIMARY KEY (eventId, mapKey, mapValue))";
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

    public void addToMap(int eventId, String mapKey, double mapValue) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String insertLocationSql = "INSERT INTO map_events(eventId, mapKey, mapValue) VALUES(" +
            //        eventId + ",'" + mapKey + "'," + mapValue + ")";
            //stmt = connection.createStatement();
            //stmt.execute(insertLocationSql);
            String insertLocationSql = "INSERT INTO map_events(eventId, mapKey, mapValue) VALUES(?, ?, ?)";
            stmt = connection.prepareStatement(insertLocationSql);
            stmt.setInt(1, eventId);
            stmt.setString(2, mapKey);
            stmt.setDouble(3, mapValue);
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

    public void deleteFromMap(int eventId, String mapKey, double mapValue) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteLocationSql = "DELETE FROM map_events WHERE eventId = " + eventId + " AND mapKey = '" + mapKey + "' AND mapValue = " + mapValue;
            //stmt = connection.createStatement();
            //stmt.execute(deleteLocationSql);
            String deleteLocationSql = "DELETE FROM map_events WHERE eventId = ? AND mapKey = ? AND mapValue = ?";
            stmt = connection.prepareStatement(deleteLocationSql);
            stmt.setInt(1, eventId);
            stmt.setString(2, mapKey);
            stmt.setDouble(3, mapValue);
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

    public void updateMap(int eventId, String mapKey, double mapValue) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateLocationSql = "UPDATE map_events SET mapValue = " + mapValue + " WHERE eventId = " + eventId + " AND mapKey = '" + mapKey + "'";
            //stmt = connection.createStatement();
            //stmt.execute(updateLocationSql);
            String updateLocationSql = "UPDATE map_events SET mapValue = ? WHERE eventId = ? AND mapKey = ?";
            stmt = connection.prepareStatement(updateLocationSql);
            stmt.setDouble(1, mapValue);
            stmt.setInt(2, eventId);
            stmt.setString(3, mapKey);
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

    public double getMapValue(int eventId, String mapKey) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double mapValue = 0;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectLocationSql = "SELECT mapValue FROM map_events WHERE eventId = " + eventId + " AND mapKey = '" + mapKey + "'";
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectLocationSql);
            String selectLocationSql = "SELECT mapValue FROM map_events WHERE eventId = ? AND mapKey = ?";
            stmt = connection.prepareStatement(selectLocationSql);
            stmt.setInt(1, eventId);
            stmt.setString(2, mapKey);
            rs = stmt.executeQuery();
            if (rs.next()) {
                mapValue = rs.getDouble("mapValue");
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
        return mapValue;
    }

    public static Map<String, Double> getMap(int eventId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Map<String, Double> map = new HashMap<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //String selectLocationSql = "SELECT * FROM map_events WHERE eventId = " + eventId;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(selectLocationSql);
            String selectLocationSql = "SELECT * FROM map_events WHERE eventId = ?";
            stmt = connection.prepareStatement(selectLocationSql);
            stmt.setInt(1, eventId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("mapKey"), rs.getDouble("mapValue"));
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
        }
        return map;
    }

    public void showMap(int eventId) {
        Map<String, Double> map = getMap(eventId);
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
