package repository;

import database.DatabaseConfiguration;
import model.Review;
import model.Ticket;
import user.Customer;

import java.sql.Connection;
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
        }
    }

    public static void addCustomer(Customer customer) {
        UserRepository.addUser(customer);

        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            String insertCustomerSql = "INSERT INTO customers(wallet, userId) VALUES(" + customer.getWallet() + ", " + UserRepository.getUserId(customer) + ")";
            stmt.execute(insertCustomerSql);
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

    public static int getCustomerId(Customer customer) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        int customerId = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT id FROM customers WHERE userId = " + UserRepository.getUserId(customer));
            if (rs.next()) {
                customerId = rs.getInt("id");
            }
            return customerId;
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
            return -1;
        }
    }

    /*public static Customer getCustomerById(int id) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        Customer customer = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customers WHERE id = " + id);
            if (rs.next()) {
                customer = new Customer(rs.getDouble("wallet"));
            }
            return customer;
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
            return null;
        }
    }*/

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
            return customers;
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
            return null;
        }
    }

    public List<Review> getReviews(Customer customer) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM reviews WHERE customerId = " + getCustomerId(customer));
            while (rs.next()) {
                Review review = new Review(rs.getInt("id"), EventRepository.getEventById(rs.getInt("event_id")), UserRepository.getUserById(rs.getInt("user_id")), rs.getDouble("rating"), rs.getString("comment"));
                reviews.add(review);
            }
            connection.close();
            return reviews;
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
            return null;
        }
    }

    public TreeSet<Ticket> getTickets(Customer customer) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        TreeSet<Ticket> tickets = new TreeSet<>();
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM tickets WHERE customerId = " + getCustomerId(customer));
            while (rs.next()) {
                Ticket ticket = TicketRepository.getTicketById(rs.getInt("id"));
                tickets.add(ticket);
            }
            connection.close();
            return tickets;
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

    public double getWallet(Customer customer) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        double wallet = -1;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT wallet FROM customers WHERE userId = " + UserRepository.getUserId(customer));
            if (rs.next()) {
                wallet = rs.getDouble("wallet");
            }
            return wallet;
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

    public void updateWallet(Customer customer, double newWallet) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseConfiguration.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE customers SET wallet = " + newWallet + " WHERE userId = " + UserRepository.getUserId(customer));
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
}
