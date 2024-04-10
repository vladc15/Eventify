package service;

import model.Location;
import user.Admin;
import user.Artist;
import user.Customer;
import user.User;

import java.util.*;

public class RegistrationService {
    private List<Customer> customers;
    private List<Artist> artists;
    private Admin admin;
    private Set<Integer> ids;

    private User currentUser;

    public RegistrationService() {
        this.customers = new ArrayList<Customer>();
        this.artists = new ArrayList<Artist>();
        this.admin = Admin.getInstance();
        this.ids = new HashSet<Integer>();
        this.currentUser = null;
    }

    public List<Customer> getCustomers() { return customers; }
    public List<Artist> getArtists() { return artists; }
    public Admin getAdmin() { return admin; }
    public Set<Integer> getIds() { return ids; }
    public User getCurrentUser() { return currentUser; }

    public void setCustomers(List<Customer> customers) { this.customers = new ArrayList<Customer>(customers); }
    public void setArtists(List<Artist> artists) { this.artists = new ArrayList<Artist>(artists); }
    public void setAdmin(Admin admin) { this.admin = admin; }
    public void setIds(Set<Integer> ids) { this.ids = new HashSet<Integer>(ids); }
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }

    public int logIn(String username, String password) {
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            currentUser = admin;
            return 0;
        }
        for (Customer customer : customers)
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                currentUser = customer;
                return 1;
            }
        for (Artist artist : artists)
            if (artist.getUsername().equals(username) && artist.getPassword().equals(password)) {
                currentUser = artist;
                return 2;
            }
        return -1;
    }

    public int logIn(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        return logIn(username, password);
    }

    public int logOut() {
        System.out.println("Goodbye, " + currentUser.getUsername() + "!");
        currentUser = null;
        return 0;
    }

    private Boolean checkUsername(String username) {
        if (admin != null && admin.getUsername().equals(username))
            return false;
        for (Customer customer : customers)
            if (customer.getUsername().equals(username))
                return false;
        for (Artist artist : artists)
            if (artist.getUsername().equals(username))
                return false;
        return true;
    }

    public void signUpAdmin(Scanner scanner) {
        if (this.admin != null) {
            System.out.println("user.Admin already exists! Only one admin is allowed.");
            return;
        }
        System.out.println("user.Admin username:");
        String username = scanner.nextLine();
        // check if username is already taken, even for admin
        while (checkUsername(username)) {
            System.out.println("Username already exists! Choose another one!");
            username = scanner.nextLine();
        }
        System.out.println("user.Admin password:");
        String password = scanner.nextLine();
        int allocated_id = allocateId();
        this.admin = Admin.getInstance(allocated_id, username, password);
        System.out.println("user.Admin account created successfully!");
    }

    public void signUpCustomer(Scanner scanner) {
        System.out.println("user.Customer username:");
        String username = scanner.nextLine();

        while (checkUsername(username)) {
            System.out.println("Username already exists! Choose another one!");
            username = scanner.nextLine();
        }

        System.out.println("user.Customer password:");
        String password = scanner.nextLine();

        System.out.println("Do you want to enter extra information about you? (yes/no)");
        String response = scanner.nextLine();
        if (response == "yes") {
            System.out.println("user.Customer name:");
            String name = scanner.nextLine();
            System.out.println("user.Customer age:");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.println("user.Customer location:");
            Location location = new Location();
            location.fromInput(scanner);
            int allocated_id = allocateId();
            Customer customer = new Customer(allocated_id, username, password, name, age, location);
            customers.add(customer);
        }
        else {
            // create account with basic info
            int allocated_id = allocateId();
            Customer customer = new Customer(allocated_id, username, password);
            customers.add(customer);
        }
        System.out.println("user.Customer account created successfully!");
    }

    public void signUpArtist(Scanner scanner) {
        System.out.println("user.Artist username:");
        String username = scanner.nextLine();

        while (checkUsername(username)) {
            System.out.println("Username already exists! Choose another one!");
            username = scanner.nextLine();
        }

        System.out.println("user.Artist password:");
        String password = scanner.nextLine();

        System.out.println("Do you want to enter extra information about you? (yes/no)");
        String response = scanner.nextLine();
        if (response == "yes") {
            System.out.println("user.Artist name:");
            String name = scanner.nextLine();
            System.out.println("user.Artist age:");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.println("user.Artist location:");
            Location location = new Location();
            location.fromInput(scanner);
            System.out.println("user.Artist bio:");
            String bio = scanner.nextLine();
            System.out.println("user.Artist genre:");
            String genre = scanner.nextLine();
            int allocated_id = allocateId();
            Artist artist = new Artist(allocated_id, username, password, name, age, location, bio, genre);
            artists.add(artist);
        }
        else {
            // create account with basic info
            int allocated_id = allocateId();
            Artist artist = new Artist(allocated_id, username, password);
            artists.add(artist);
        }
        System.out.println("user.Artist account created successfully!");
    }

    public void deleteCustomer(Customer customer) { customers.remove(customer); }
    public void deleteArtist(Artist artist) { artists.remove(artist); }
    public void deleteAdmin() { admin = null; }

    public int allocateId() {
        int id = ids.size();
        ids.add(id);
        return id;
    }

}
