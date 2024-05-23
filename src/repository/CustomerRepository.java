package repository;

import database.DatabaseConfiguration;
import model.Review;
import model.Ticket;
import user.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CustomerRepository {
    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            String createTableSql = "CREATE TABLE IF NOT EXISTS customers" +
                    "(id int PRIMARY KEY AUTO_INCREMENT, wallet double, userId int, FOREIGN KEY (userId) REFERENCES users(id))";
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

    public static void addCustomer(Customer customer) {
        UserRepository.addUser(customer);

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            stmt = connection.prepareStatement("INSERT INTO customers(wallet, userId) VALUES(?, ?)");
            stmt.setDouble(1, customer.getWallet());
            stmt.setInt(2, UserRepository.getUserId(customer));
            stmt.executeQuery();
            //String insertCustomerSql = "INSERT INTO customers(wallet, userId) VALUES(" + customer.getWallet() + ", " + UserRepository.getUserId(customer) + ")";
            //stmt.execute(insertCustomerSql);
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

    public static int getCustomerId(Customer customer) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int customerId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT id FROM customers WHERE userId = " + UserRepository.getUserId(customer));
            stmt = connection.prepareStatement("SELECT id FROM customers WHERE userId = ?");
            stmt.setInt(1, UserRepository.getUserId(customer));
            rs = stmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getInt("id");
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
        return customerId;
    }


    public List<Customer> getCustomers() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Customer> customers = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                int userId = rs.getInt("userId");
                Customer customer = (Customer)UserRepository.getUserById(userId);
                customers.add(customer);
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
        return customers;
    }

    public List<Review> getReviews(Customer customer) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * FROM reviews WHERE user_id = " + UserRepository.getUserId(customer));
            stmt = connection.prepareStatement("SELECT * FROM reviews WHERE user_id = ?");
            stmt.setInt(1, UserRepository.getUserId(customer));
            rs = stmt.executeQuery();
            while (rs.next()) {
                Review review = new Review(rs.getInt("id"), EventRepository.getEventById(rs.getInt("event_id")), UserRepository.getUserById(rs.getInt("user_id")), rs.getDouble("rating"), rs.getString("comment"));
                reviews.add(review);
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
        return reviews;
    }

    public TreeSet<Ticket> getTickets(Customer customer) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TreeSet<Ticket> tickets = new TreeSet<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * FROM customer_tickets WHERE customerId = " + getCustomerId(customer));
            stmt = connection.prepareStatement("SELECT * FROM customer_tickets WHERE customerId = ?");
            stmt.setInt(1, getCustomerId(customer));
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket ticket = TicketRepository.getTicketById(rs.getInt("ticketId"));
                tickets.add(ticket);
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
        return tickets;
    }

    public double getWallet(Customer customer) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double wallet = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT wallet FROM customers WHERE userId = " + UserRepository.getUserId(customer));
            stmt = connection.prepareStatement("SELECT wallet FROM customers WHERE userId = ?");
            stmt.setInt(1, UserRepository.getUserId(customer));
            rs = stmt.executeQuery();
            if (rs.next()) {
                wallet = rs.getDouble("wallet");
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
        return wallet;
    }

    public void updateWallet(Customer customer, double newWallet) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            //stmt = connection.createStatement();
            //stmt.executeUpdate("UPDATE customers SET wallet = " + newWallet + " WHERE userId = " + UserRepository.getUserId(customer));
            stmt = connection.prepareStatement("UPDATE customers SET wallet = ? WHERE userId = ?");
            stmt.setDouble(1, newWallet);
            stmt.setInt(2, UserRepository.getUserId(customer));
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
}
