package service;

import model.Location;
import repository.AdminRepository;
import repository.ArtistRepository;
import repository.CustomerRepository;
import repository.UserRepository;
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
        this.customers = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.admin = Admin.getInstance();
        this.ids = new HashSet<>();
        this.currentUser = null;
    }

    public List<Customer> getCustomers() { return customers; }
    public List<Artist> getArtists() { return artists; }
    public Admin getAdmin() { return admin; }
    public Set<Integer> getIds() { return ids; }
    public User getCurrentUser() { return currentUser; }

    public void setCustomers(List<Customer> customers) { this.customers = new ArrayList<>(customers); }
    public void setArtists(List<Artist> artists) { this.artists = new ArrayList<>(artists); }
    public void setAdmin(Admin admin) { this.admin = admin; }
    public void setIds(Set<Integer> ids) { this.ids = new HashSet<>(ids); }
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
        UserRepository userRepository = new UserRepository();
        if (userRepository.getUserIdByUsername(username) != -1)
            return false;
        return true;

        /*if (admin != null && admin.getUsername().equals(username))
            return false;
        for (Customer customer : customers)
            if (customer.getUsername().equals(username))
                return false;
        for (Artist artist : artists)
            if (artist.getUsername().equals(username))
                return false;
        return true;*/
    }

    public void signUpAdmin(Scanner scanner) {
        if (this.admin != null) {
            System.out.println("Admin already exists! Only one admin is allowed.");
            return;
        }
        System.out.println("Admin username:");
        String username = scanner.nextLine();
        // check if username is already taken, even for admin
        while (!checkUsername(username)) {
            System.out.println("Username already exists! Choose another one!");
            username = scanner.nextLine();
        }
        System.out.println("Admin password:");
        String password = scanner.nextLine();
        int allocated_id = allocateId();
        this.admin = Admin.getInstance(allocated_id, username, password);
        AdminRepository adminRepository = new AdminRepository();
        adminRepository.addAdmin(this.admin);
        System.out.println("Admin account created successfully!");
    }

    public void signUpCustomer(Scanner scanner) {
        System.out.println("Customer username:");
        String username = scanner.nextLine();

        while (!checkUsername(username)) {
            System.out.println("Username already exists! Choose another one!");
            username = scanner.nextLine();
        }

        System.out.println("Customer password:");
        String password = scanner.nextLine();

        System.out.println("Do you want to enter extra information about you? (yes/no)");
        String response = scanner.nextLine();
        CustomerRepository customerRepository = new CustomerRepository();
        if (response.equals("yes")) {
            System.out.println("Customer name:");
            String name = scanner.nextLine();
            System.out.println("Customer age:");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Customer location:");
            Location location = new Location();
            location.fromInput(scanner);
            int allocated_id = allocateId();
            Customer customer = new Customer(allocated_id, username, password, name, age, location);
            customers.add(customer);
            customerRepository.addCustomer(customer);
        }
        else {
            // create account with basic info
            int allocated_id = allocateId();
            Customer customer = new Customer(allocated_id, username, password);
            customers.add(customer);
            customerRepository.addCustomer(customer);
        }
        System.out.println("Customer account created successfully!");
    }

    public void signUpArtist(Scanner scanner) {
        System.out.println("Artist username:");
        String username = scanner.nextLine();

        while (!checkUsername(username)) {
            System.out.println("Username already exists! Choose another one!");
            username = scanner.nextLine();
        }

        System.out.println("Artist password:");
        String password = scanner.nextLine();

        System.out.println("Do you want to enter extra information about you? (yes/no)");
        String response = scanner.nextLine();
        ArtistRepository artistRepository = new ArtistRepository();
        if (response.equals("yes")) {
            System.out.println("Artist name:");
            String name = scanner.nextLine();
            System.out.println("Artist age:");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Artist location:");
            Location location = new Location();
            location.fromInput(scanner);
            System.out.println("Artist bio:");
            String bio = scanner.nextLine();
            System.out.println("Artist genre:");
            String genre = scanner.nextLine();
            int allocated_id = allocateId();
            Artist artist = new Artist(allocated_id, username, password, name, age, location, bio, genre);
            artists.add(artist);
            artistRepository.addArtist(artist);
        }
        else {
            // create account with basic info
            int allocated_id = allocateId();
            Artist artist = new Artist(allocated_id, username, password);
            artists.add(artist);
            artistRepository.addArtist(artist);
        }
        System.out.println("Artist account created successfully!");
    }

    //public void deleteCustomer(Customer customer) { customers.remove(customer); }
    //public void deleteArtist(Artist artist) { artists.remove(artist); }
    //public void deleteAdmin() { admin = null; }

    public int allocateId() {
        int id = ids.size();
        ids.add(id);
        return id;
    }

}
