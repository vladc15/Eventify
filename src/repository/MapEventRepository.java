package repository;

import database.DatabaseConfiguration;

import java.sql.Connection;
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
                    "(eventId int, mapKey varchar(100), mapValue double, PRIMARY KEY (eventId, mapKey, mapValue))";
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

    public void addToMap(int eventId, String mapKey, double mapValue) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String insertLocationSql = "INSERT INTO map_events(eventId, mapKey, mapValue) VALUES(" +
                    eventId + ",'" + mapKey + "'," + mapValue + ")";
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

    public void deleteFromMap(int eventId, String mapKey, double mapValue) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteLocationSql = "DELETE FROM map_events WHERE eventId = " + eventId + " AND mapKey = '" + mapKey + "' AND mapValue = " + mapValue;
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

    public void updateMap(int eventId, String mapKey, double mapValue) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateLocationSql = "UPDATE map_events SET mapValue = " + mapValue + " WHERE eventId = " + eventId + " AND mapKey = '" + mapKey + "'";
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

    public double getMapValue(int eventId, String mapKey) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectLocationSql = "SELECT mapValue FROM map_events WHERE eventId = " + eventId + " AND mapKey = '" + mapKey + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectLocationSql);
            if (rs.next()) {
                return rs.getDouble("mapValue");
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
        }
        return 0;
    }

    public static Map<String, Double> getMap(int eventId) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        Map<String, Double> map = new HashMap<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            String selectLocationSql = "SELECT * FROM map_events WHERE eventId = " + eventId;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectLocationSql);
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
