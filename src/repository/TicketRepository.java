package repository;

import database.DatabaseConfiguration;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class TicketRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS tickets" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, eventID int, type varchar(100), seat varchar(100), FOREIGN KEY (eventID) REFERENCES events(id))";
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

    public static void addTicket(int eventID, String type, String seat) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String addTicketSql = "INSERT INTO tickets (eventID, type, seat) VALUES (" + eventID + ", '" + type + "', '" + seat + "')";
            stmt = connection.createStatement();
            stmt.execute(addTicketSql);
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

    public static void addTicket(Ticket ticket) {
        addTicket(EventRepository.getEventId(ticket.getEvent()), ticket.getType(), ticket.getSeat());
    }

    public static void deleteTicket(int ticketID) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String deleteTicketSql = "DELETE FROM tickets WHERE id = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(deleteTicketSql);
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

    public static int getTicketId(Ticket ticket) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String getTicketIdSql = "SELECT id FROM tickets WHERE eventID = " + EventRepository.getEventId(ticket.getEvent()) + " AND type = '" + ticket.getType() + "' AND seat = '" + ticket.getSeat() + "'";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(getTicketIdSql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
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
            return -1;
        }
    }

    public static void deleteTicket(Ticket ticket) {
        deleteTicket(getTicketId(ticket));
    }

    public static void showEventTickets(int eventID) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String showEventTicketsSql = "SELECT * FROM tickets WHERE eventID = " + eventID;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(showEventTicketsSql);
            while (rs.next()) {
                System.out.println("Ticket ID: " + rs.getInt(1) + ", Event ID: " + rs.getInt(2) + ", Type: " + rs.getString(3) + ", Seat: " + rs.getString(4));
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
    }

    public static void showEventTickets(Event event) {
        showEventTickets(EventRepository.getEventId(event));
    }

    public static Ticket getTicketById(int ticketID) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String getTicketByIdSql = "SELECT * FROM tickets t LEFT JOIN concert_tickets ct LEFT JOIN filmScreening_tickets ft LEFT JOIN theatre_play_tickets tt ON t.id = ct.ticketID OR t.id = ft.ticketID OR t.id = tt.ticketID WHERE t.id = " + ticketID;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(getTicketByIdSql);
            if (rs.next()) {
                if (rs.getInt("ct.ticketID") != 0) {
                    return new ConcertTicket(ticketID, EventRepository.getEventById(rs.getInt("t.eventID")), rs.getString("t.type"), rs.getString("t.seat"), rs.getDouble("ct.afterPartyPrice"), rs.getDouble("ct.meetAndGreetPrice"));
                } else if (rs.getInt("ft.ticketID") != 0) {
                    return new FilmScreeningTicket(ticketID, EventRepository.getEventById(rs.getInt("t.eventID")), rs.getString("t.type"), rs.getString("t.seat"), rs.getDouble("ft.qaPrice"), rs.getDouble("ft.imaxPrice"));
                } else if (rs.getInt("tt.ticketID") != 0) {
                    return new TheatrePlayTicket(ticketID, EventRepository.getEventById(rs.getInt("t.eventID")), rs.getString("t.type"), rs.getString("t.seat"), rs.getDouble("tt.qaPrice"));
                }
            }
            return null;
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
            return null;
        }
    }

    public void updateType(int ticketID, String type) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateTypeSql = "UPDATE tickets SET type = '" + type + "' WHERE id = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(updateTypeSql);
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

    public void updateSeat(int ticketID, String seat) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String updateSeatSql = "UPDATE tickets SET seat = '" + seat + "' WHERE id = " + ticketID;
            stmt = connection.createStatement();
            stmt.execute(updateSeatSql);
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

    public List<Ticket> getTickets() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Ticket> tickets = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String getTicketsSql = "SELECT * FROM tickets";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(getTicketsSql);
            while (rs.next()) {
                int ticketId = rs.getInt(1);
                tickets.add(getTicketById(ticketId));
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
            e.printStackTrace();
        }
        return tickets;
    }
}
