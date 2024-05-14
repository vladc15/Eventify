package repository;

import database.DatabaseConfiguration;
import user.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
}
