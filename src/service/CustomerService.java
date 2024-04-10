package service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import model.Event;
import model.Location;
import model.Review;
import model.Ticket;
import user.Artist;
import user.Customer;


// singleton
public class CustomerService implements IUserService {
    private static CustomerService instance = null;
    private Customer customer;

    private CustomerService() {
        this.customer = new Customer();
    }

    private CustomerService(Customer customer) {
        this.customer = customer;
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public static CustomerService getInstance(Customer customer) {
        if (instance == null)
            instance = new CustomerService(customer);
        return instance;
    }

    public Customer getCustomer() { return customer; }

    public void showMenu() {
        System.out.println("\n----------Customer menu:----------");
        System.out.println("1. Access profile");
        System.out.println("2. Access history");
        System.out.println("3. Access favorites");
        System.out.println("4. Access followed artists");
        System.out.println("5. Access reviews");
        System.out.println("6. Access ordered tickets");
        System.out.println("7. Access wallet");
        System.out.println("8. Search events");
        System.out.println("9. Log out");

        /*System.out.println("1. Show wishlist");
        System.out.println("2. Show history");
        System.out.println("3. Show favorites");
        System.out.println("4. Show followed artists");
        System.out.println("5. Show reviews");
        System.out.println("6. Show ordered tickets");
        System.out.println("7. Show wallet");
        System.out.println("8. Add to wishlist");
        System.out.println("9. Add to favorites");
        System.out.println("10. Add to followed artists");
        System.out.println("11. Add review");
        System.out.println("12. Add more funds to wallet");
        System.out.println("13. Buy ticket");
        System.out.println("14. Remove from wishlist");
        System.out.println("15. Remove from favorites");
        System.out.println("16. Remove from followed artists");
        System.out.println("17. Remove review");
        System.out.println("18. Search events");
        System.out.println("19. Back");
        System.out.println("20. Log out");*/
        // delete account
        // search event: find upcoming events; find nearest events;
        //               find event by location, date, price, artist, genre
        //               see details of an event
        //               see reviews of an event (past event)
        //               see tickets available for an event
        //               reserve a ticket + choose seats!
    }



    public void executeAction(Scanner scanner) {
        while (true) {
            this.showMenu();
            moveEventsToPast(); // check constantly if the date of an event has passed
            System.out.print("Enter option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    manageProfile(scanner, customer);
                    break;
                case 2:
                    manageHistory(scanner);
                    break;
                case 3:
                    manageFavorites(scanner);
                    break;
                case 4:
                    manageFollowedArtists(scanner);
                    break;
                case 5:
                    manageReviews(scanner);
                    break;
                case 6:
                    manageOrderedTickets(scanner);
                    break;
                case 7:
                    manageWallet(scanner);
                    break;
                case 8:
                    searchEvents(scanner);
                    break;
                case 9:
                    logOut();
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void manageHistory(Scanner scanner) {
        System.out.println("1. Show history");
        System.out.println("2. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            customer.showHistory();
        } else if (option == 2)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageFavorites(Scanner scanner) {
        System.out.println("1. Show favorites");
        System.out.println("2. Add to favorites");
        System.out.println("3. Remove from favorites");
        System.out.println("4. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            customer.showFavorites();
        } else if (option == 2) {
            System.out.println("These are your past events:");
            customer.showHistory();
            System.out.println("Enter the id of the event you want to add to favorites:");
            int id = scanner.nextInt();
            List<Event> history = customer.getHistory();
            for (Event event : history)
                if (event.getEventId() == id) {
                    customer.addEventToFavorites(event);
                    break;
                }
        } else if (option == 3) {
            System.out.println("These are your favorites:");
            customer.showFavorites();
            System.out.println("Enter the id of the event you want to remove from favorites:");
            int id = scanner.nextInt();
            TreeSet<Event> favorites = customer.getFavorites();
            for (Event event : favorites)
                if (event.getEventId() == id) {
                    favorites.remove(event);
                    break;
                }
        } else if (option == 4)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageFollowedArtists(Scanner scanner) {
        System.out.println("1. Show followed artists");
        System.out.println("2. Add to followed artists");
        System.out.println("3. Remove from followed artists");
        System.out.println("4. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            customer.showFollowedArtists();
        } else if (option == 2) {
            System.out.println("These are all the current artists with events:");
            List<Artist> artists = getRegistrationService().getArtists();
            for (Artist artist : artists)
                System.out.println(artist);
            System.out.println("Enter the id of the artist you want to follow:");
            int id = scanner.nextInt();
            for (Artist artist : artists)
                if (artist.getUserId() == id) {
                    customer.addArtistToFollowedArtists(artist);
                    break;
                }
        } else if (option == 3) {
            System.out.println("These are the artists you follow:");
            customer.showFollowedArtists();
            System.out.println("Enter the id of the artist you want to remove from followed artists:");
            int id = scanner.nextInt();
            List<Artist> followedArtists = customer.getFollowedArtists();
            for (Artist artist : followedArtists)
                if (artist.getUserId() == id) {
                    followedArtists.remove(artist);
                    break;
                }
        } else if (option == 4)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageReviews(Scanner scanner) {
        System.out.println("1. Show reviews");
        System.out.println("2. Add review");
        System.out.println("3. Remove review");
        System.out.println("4. Update review");
        System.out.println("5. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            customer.showReviews();
        } else if (option == 2) {
            System.out.println("These are your past events:");
            customer.showHistory();
            System.out.println("Enter the id of the event you want to review:");
            int id = scanner.nextInt();
            List<Event> history = customer.getHistory();
            for (Event event : history)
                if (event.getEventId() == id) {
                    System.out.print("Enter your review: ");
                    String review = scanner.nextLine();
                    System.out.println("Rating (1-5): ");
                    double rating = scanner.nextDouble();
                    int allocatedId = getRegistrationService().allocateId();
                    customer.addReview(new Review(allocatedId, event, customer, rating, review));
                    break;
                }
        } else if (option == 3) {
            System.out.println("These are your reviews:");
            customer.showReviews();
            System.out.println("Enter the id of the review you want to remove:");
            int id = scanner.nextInt();
            List<Review> reviews = customer.getReviews();
            for (Review review : reviews)
                if (review.getReviewId() == id) {
                    reviews.remove(review);
                    break;
                }
        } else if (option == 4) {
            System.out.println("These are your reviews:");
            customer.showReviews();
            System.out.println("Enter the id of the review you want to update:");
            int id = scanner.nextInt();
            List<Review> reviews = customer.getReviews();
            for (Review review : reviews)
                if (review.getReviewId() == id) {
                    System.out.print("Enter your review: ");
                    String newReview = scanner.nextLine();
                    System.out.println("Rating (1-5): ");
                    double newRating = scanner.nextDouble();
                    review.setComment(newReview);
                    review.setRating(newRating);
                    break;
                }
        } else if (option == 5)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageOrderedTickets(Scanner scanner) {
        System.out.println("1. Show ordered tickets");
        System.out.println("2. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            customer.showTickets();
        } else if (option == 2)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageWallet(Scanner scanner) {
        System.out.println("1. Show wallet");
        System.out.println("2. Add more funds to wallet");
        System.out.println("3. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            System.out.println("Wallet: " + customer.getWallet());
        } else if (option == 2) {
            System.out.print("Enter amount to add: ");
            double amount = scanner.nextDouble();
            customer.setWallet(customer.getWallet() + amount);
        } else if (option == 3)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    // plus adaugare la favorite, follow artist, review(?)
    // plus reserve seat
    private void searchEvents(Scanner scanner) {
        System.out.println("1. Find upcoming events");
        System.out.println("2. Find nearest events");
        System.out.println("3. Find event by date");
        System.out.println("4. Find event by price");
        System.out.println("5. Find event by artist");
        System.out.println("6. Find event by genre");
        System.out.println("7. See reviews of an event");
        System.out.println("8. See tickets available for an event");
        System.out.println("9. Reserve a ticket");
        System.out.println("10. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            List<Event> futureEvents = getFutureEvents();
            for (Event event : futureEvents)
                System.out.println(event);
        } else if (option == 2) {
            List<Event> futureEvents = getFutureEvents();
            Location userLocation = customer.getLocation();
            for (Event event : futureEvents)
                if (event.getLocation().getCity().equals(userLocation.getCity()))
                    System.out.println(event);
        } else if (option == 3) {
            System.out.println("Enter date: ");
            String date = scanner.nextLine();
            List<Event> futureEvents = getFutureEvents();
            for (Event event : futureEvents)
                if (event.getDate().equals(date))
                    System.out.println(event);
        } else if (option == 4) {
            System.out.println("Enter price range: ");
            double minPrice = scanner.nextDouble();
            double maxPrice = scanner.nextDouble();
            List<Event> futureEvents = getFutureEvents();
            for (Event event : futureEvents) {
                Map<String, Double> prices = event.getTicketPrices();
                for (Map.Entry<String, Double> entry : prices.entrySet())
                    if (entry.getValue() >= minPrice && entry.getValue() <= maxPrice) {
                        System.out.println(event);
                        break;
                    }
            }
        } else if (option == 5) {
            System.out.println("These are all the current artists with events:");
            List<Artist> artists = getRegistrationService().getArtists();
            for (Artist artist : artists)
                System.out.println(artist);
            System.out.println("Enter the id of the artist whose events you want to see:");
            int id = scanner.nextInt();
            for (Artist artist : artists)
                if (artist.getUserId() == id) {
                    List<Event> futureEvents = getFutureEvents();
                    for (Event event : futureEvents)
                        if (event.getArtists().contains(artist))
                            System.out.println(event);
                    break;
                }
        } else if (option == 6) {
            System.out.println("Enter genre: ");
            String genre = scanner.nextLine();
            List<Event> futureEvents = getFutureEvents();
            for (Event event : futureEvents)
                if (event.getGenre().equals(genre))
                    System.out.println(event);
        } else if (option == 7) {
            List<Event> pastEvents = getPastEvents();
            for (Event event : pastEvents)
                System.out.println(event);
            System.out.println("Enter the id of the event you want to see reviews for:");
            int id = scanner.nextInt();
            for (Event event : pastEvents)
                if (event.getEventId() == id) {
                    event.showReviews();
                    break;
                }
        } else if (option == 8) {
            List<Event> futureEvents = getFutureEvents();
            for (Event event : futureEvents)
                System.out.println(event);
            System.out.println("Enter the id of the event you want to see tickets for:");
            int id = scanner.nextInt();
            List<Ticket> tickets = getTickets();
            for (Ticket ticket : tickets)
                if (ticket.getEvent().getEventId() == id)
                    System.out.println(ticket);
        } else if (option == 9) {
            List<Event> futureEvents = getFutureEvents();
            for (Event event : futureEvents)
                System.out.println(event);
            System.out.println("Enter the id of the event you want to reserve a ticket for:");
            int id = scanner.nextInt();
            System.out.println("These are the tickets available for this event:");
            List<Ticket> tickets = getTickets();
            for (Ticket ticket : tickets)
                if (ticket.getEvent().getEventId() == id)
                    System.out.println(ticket);
            System.out.println("Enter the number of tickets you want to reserve:");
            int number = scanner.nextInt();
            System.out.println("Enter the ids of the tickets you want to reserve:");
            for (int i = 0; i < number; i++) {
                int ticketId = scanner.nextInt();
                for (Ticket ticket : tickets)
                    if (ticket.getTicketId() == ticketId) {
                        customer.purchaseTicket(ticket);
                        tickets.remove(ticket);
                        break;
                    }
            }
            setTickets(tickets);
        } else if (option == 10)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }
}
