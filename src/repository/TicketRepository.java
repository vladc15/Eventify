package repository;

import database.DatabaseConfiguration;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS tickets" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, eventID int, ticket_type varchar(100), seat varchar(100), FOREIGN KEY (eventID) REFERENCES events(id))";
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

    public static void addTicket(int eventID, String type, String seat) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String addTicketSql = "INSERT INTO tickets (eventID, ticket_type, seat) VALUES (" + eventID + ", '" + type + "', '" + seat + "')";
            //stmt = connection.createStatement();
            //stmt.execute(addTicketSql);
            String addTicketSql = "INSERT INTO tickets (eventID, ticket_type, seat) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(addTicketSql);
            stmt.setInt(1, eventID);
            stmt.setString(2, type);
            stmt.setString(3, seat);
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

    public static void addTicket(Ticket ticket) {
        addTicket(EventRepository.getEventId(ticket.getEvent()), ticket.getType(), ticket.getSeat());
    }

    public static void deleteTicket(int ticketID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String deleteTicketSql = "DELETE FROM tickets WHERE id = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(deleteTicketSql);
            String deleteTicketSql = "DELETE FROM tickets WHERE id = ?";
            stmt = connection.prepareStatement(deleteTicketSql);
            stmt.setInt(1, ticketID);
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

    public static int getTicketId(Ticket ticket) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int ticketID = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String getTicketIdSql = "SELECT id FROM tickets WHERE eventID = " + EventRepository.getEventId(ticket.getEvent()) + " AND ticket_type = '" + ticket.getType() + "' AND seat = '" + ticket.getSeat() + "'";
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(getTicketIdSql);
            String getTicketIdSql = "SELECT id FROM tickets WHERE eventID = ? AND ticket_type = ? AND seat = ?";
            stmt = connection.prepareStatement(getTicketIdSql);
            stmt.setInt(1, EventRepository.getEventId(ticket.getEvent()));
            stmt.setString(2, ticket.getType());
            stmt.setString(3, ticket.getSeat());
            rs = stmt.executeQuery();
            if (rs.next()) {
                ticketID = rs.getInt(1);
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
        return ticketID;
    }

    public static void deleteTicket(Ticket ticket) {
        deleteTicket(getTicketId(ticket));
    }

    public static void showEventTickets(int eventID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String showEventTicketsSql = "SELECT * FROM tickets WHERE eventID = " + eventID;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(showEventTicketsSql);
            String showEventTicketsSql = "SELECT * FROM tickets WHERE eventID = ?";
            stmt = connection.prepareStatement(showEventTicketsSql);
            stmt.setInt(1, eventID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Ticket ID: " + rs.getInt(1) + ", Event ID: " + rs.getInt(2) + ", ticket_type: " + rs.getString(3) + ", Seat: " + rs.getString(4));
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
    }

    public static void showEventTickets(Event event) {
        showEventTickets(EventRepository.getEventId(event));
    }

    public static Ticket getTicketById(int ticketID) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Ticket ticket = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String getTicketByIdSql = "SELECT * FROM tickets t " +
            //                            "LEFT JOIN concert_tickets ct ON t.id = ct.ticketID " +
            //                            "LEFT JOIN filmScreening_tickets ft ON t.id = ft.ticketID " +
            //                            "LEFT JOIN theatre_play_tickets tt ON t.id = tt.ticketID WHERE t.id = " + ticketID;
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery(getTicketByIdSql);
            String getTicketByIdSql = "SELECT * FROM tickets t " +
                    "LEFT JOIN concert_tickets ct ON t.id = ct.ticketID " +
                    "LEFT JOIN filmScreening_tickets ft ON t.id = ft.ticketID " +
                    "LEFT JOIN theatre_play_tickets tt ON t.id = tt.ticketID WHERE t.id = ?";
            stmt = connection.prepareStatement(getTicketByIdSql);
            stmt.setInt(1, ticketID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("ct.ticketID") != 0) {
                    ticket = new ConcertTicket(ticketID, EventRepository.getEventById(rs.getInt("t.eventID")), rs.getString("t.ticket_type"), rs.getString("t.seat"), rs.getDouble("ct.afterPartyPrice"), rs.getDouble("ct.meetAndGreetPrice"));
                } else if (rs.getInt("ft.ticketID") != 0) {
                    ticket = new FilmScreeningTicket(ticketID, EventRepository.getEventById(rs.getInt("t.eventID")), rs.getString("t.ticket_type"), rs.getString("t.seat"), rs.getDouble("ft.qaPrice"), rs.getDouble("ft.imaxPrice"));
                } else if (rs.getInt("tt.ticketID") != 0) {
                    ticket = new TheatrePlayTicket(ticketID, EventRepository.getEventById(rs.getInt("t.eventID")), rs.getString("t.ticket_type"), rs.getString("t.seat"), rs.getDouble("tt.qaPrice"));
                } else
                    ticket = new Ticket(ticketID, EventRepository.getEventById(rs.getInt("t.eventID")), rs.getString("t.ticket_type"), rs.getString("t.seat"));
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
        return ticket;
    }

    public void updateType(int ticketID, String type) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateTypeSql = "UPDATE tickets SET ticket_type = '" + type + "' WHERE id = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(updateTypeSql);
            String updateTypeSql = "UPDATE tickets SET ticket_type = ? WHERE id = ?";
            stmt = connection.prepareStatement(updateTypeSql);
            stmt.setString(1, type);
            stmt.setInt(2, ticketID);
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

    public void updateSeat(int ticketID, String seat) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //String updateSeatSql = "UPDATE tickets SET seat = '" + seat + "' WHERE id = " + ticketID;
            //stmt = connection.createStatement();
            //stmt.execute(updateSeatSql);
            String updateSeatSql = "UPDATE tickets SET seat = ? WHERE id = ?";
            stmt = connection.prepareStatement(updateSeatSql);
            stmt.setString(1, seat);
            stmt.setInt(2, ticketID);
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

    public List<Ticket> getTickets() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Ticket> tickets = new ArrayList<>();
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
        return tickets;
    }
}
