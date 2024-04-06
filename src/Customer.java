import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Customer extends User {
    private TreeSet<Ticket> tickets;
    private List<Event> wishlist;
    private List<Event> history;
    private TreeSet<Event> favorites;
    private List<Artist> followedArtists;
    //private List<Concert> recommendedConcerts;
    private List<Review> reviews;
    private double wallet;
    
    public Customer() {
        super();
        this.tickets = new TreeSet<Ticket>();
        this.wishlist = new ArrayList<Event>();
        this.history = new ArrayList<Event>();
        this.favorites = new TreeSet<Event>();
        this.followedArtists = new ArrayList<Artist>();
        this.reviews = new ArrayList<Review>();
        this.wallet = 0.0;
    }
    
    public Customer(int userId, String username, String password, String email, String phone, String name, int age, Location location, TreeSet<Ticket> tickets, List<Event> wishlist, List<Event> history, TreeSet<Event> favorites, List<Artist> followedArtists, List<Review> reviews, double wallet) {
        super(userId, username, password, email, phone, name, age, location);
        this.tickets = new TreeSet<Ticket>(tickets);
        this.wishlist = new ArrayList<Event>(wishlist);
        this.history = new ArrayList<Event>(history);
        this.favorites = new TreeSet<Event>(favorites);
        this.followedArtists = new ArrayList<Artist>(followedArtists);
        this.reviews = new ArrayList<Review>(reviews);
        this.wallet = wallet;
    }
    
    public Customer(Customer customer) {
        super(customer);
        this.tickets = new TreeSet<Ticket>(customer.getTickets());
        this.wishlist = new ArrayList<Event>(customer.getWishlist());
        this.history = new ArrayList<Event>(customer.getHistory());
        this.favorites = new TreeSet<Event>(customer.getFavorites());
        this.followedArtists = new ArrayList<Artist>(customer.getFollowedArtists());
        this.reviews = new ArrayList<Review>(customer.getReviews());
        this.wallet = customer.getWallet();
    }
    
    public TreeSet<Ticket> getTickets() { return tickets; }
    public List<Event> getWishlist() { return wishlist; }
    public List<Event> getHistory() { return history; }
    public TreeSet<Event> getFavorites() { return favorites; }
    public List<Artist> getFollowedArtists() { return followedArtists; }
    public List<Review> getReviews() { return reviews; }
    public double getWallet() { return wallet; }
    
    public void setTickets(TreeSet<Ticket> tickets) { this.tickets = new TreeSet<Ticket>(tickets); }
    public void setWishlist(List<Event> wishlist) { this.wishlist = new ArrayList<Event>(wishlist); }
    public void setHistory(List<Event> history) { this.history = new ArrayList<Event>(history); }
    public void setFavorites(TreeSet<Event> favorites) { this.favorites = new TreeSet<Event>(favorites); }
    public void setFollowedArtists(List<Artist> followedArtists) { this.followedArtists = new ArrayList<Artist>(followedArtists); }
    public void setReviews(List<Review> reviews) { this.reviews = new ArrayList<Review>(reviews); }
    public void setWallet(double wallet) { this.wallet = wallet; }
    
    public String toString() {
        return super.toString() + ", Tickets: " + tickets.toString() + ", Wishlist: " + wishlist.toString() + ", History: " + history.toString() + ", Favorites: " + favorites.toString() + ", Followed Artists: " + followedArtists.toString() + ", Reviews: " + reviews.toString() + ", Wallet: " + wallet;
    }
    
    public String toCSV() {
        return super.toCSV() + "," + tickets.toString() + "," + wishlist.toString() + "," + history.toString() + "," + favorites.toString() + "," + followedArtists.toString() + "," + reviews.toString() + "," + wallet;
    }
    
    public boolean equals(Customer customer) {
        return super.equals(customer) && tickets.equals(customer.getTickets()) && wishlist.equals(customer.getWishlist()) && history.equals(customer.getHistory()) && favorites.equals(customer.getFavorites()) && followedArtists.equals(customer.getFollowedArtists()) && reviews.equals(customer.getReviews()) && wallet == customer.getWallet();
    }
    
    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        super.fromCSV(values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4] + "," + values[5] + "," + values[6] + "," + values[7]);
        this.tickets = new TreeSet<Ticket>();
        this.wishlist = new ArrayList<Event>();
        this.history = new ArrayList<Event>();
        this.favorites = new TreeSet<Event>();
        this.followedArtists = new ArrayList<Artist>();
        this.reviews = new ArrayList<Review>();
        this.wallet = 0.0;
    }
    
    public void fromInput(Scanner scanner) {
        super.fromInput(scanner);
        System.out.print("Enter wallet: ");
        this.wallet = scanner.nextDouble();
    }
    
    public void addTicket(Ticket ticket) { tickets.add(ticket); }
    
    public void removeTicket(Ticket ticket) { tickets.remove(ticket); }
    
    public void addEventToWishlist(Event event) { wishlist.add(event); }
    
    public void removeEventFromWishlist(Event event) { wishlist.remove(event); }
    
    public void addEventToHistory(Event event) { history.add(event); }
    
    public void addEventToFavorites(Event event) { favorites.add(event); }
    
    public void removeEventFromFavorites(Event event) { favorites.remove(event); }
    
    public void addArtistToFollowedArtists(Artist artist) { followedArtists.add(artist); }
    
    public void removeArtistFromFollowedArtists(Artist artist) { followedArtists.remove(artist); }
    
    public void addReview(Review review) { reviews.add(review); }
    
    public void removeReview(Review review) { reviews.remove(review); }
    
    public void addFunds(double amount) { wallet += amount; }
    
    public void purchaseTicket(Ticket ticket) {
        if (wallet >= ticket.getPrice()) {
            tickets.add(ticket);
            wallet -= ticket.getPrice();
        }
        else
            System.out.println("Insufficient funds!");
    }
    
    public void rateEvent(Event event, double rating, String comment) {
        Review review = new Review(0, event, this, rating, comment);
        reviews.add(review);
        event.addReview(review);
    }

}
