package service;

import java.util.List;
import java.util.Scanner;

import model.Event;
import model.Location;
import model.Review;
import model.Ticket;
import repository.LocationRepository;
import user.Admin;
import user.Artist;
import user.Customer;


public class AdminService implements UserService {
    private static AdminService instance = null;
    private Admin admin;

    private AdminService() {
        this.admin = Admin.getInstance();
    }

    private AdminService(Admin admin) {
        this.admin = admin;
    }

    public static AdminService getInstance() {
        if (instance == null) {
            instance = new AdminService();
        }
        return instance;
    }

    public static AdminService getInstance(Admin admin) {
        if (instance == null)
            instance = new AdminService(admin);
        return instance;
    }

    public Admin getAdmin() { return admin; }

    // CRUD operations on profile, events, customers, artists, reviews, tickets
    // profile, events(CRUD), customers(R, D), artists(R, D), reviews(R, U, D), tickets(R, D)
    public void showMenu() {
        System.out.println("\n----------Admin menu:----------");
        System.out.println("1. Access profile");
        System.out.println("2. Access events");
        System.out.println("3. Access customers");
        System.out.println("4. Access artists");
        System.out.println("5. Access reviews");
        System.out.println("6. Access tickets");
        System.out.println("7. Access locations");
        System.out.println("8. Log out");
    }

    public void executeAction(Scanner scanner) {
        while (true) {
            this.showMenu();
            moveEventsToPast();
            System.out.print("Enter option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    manageProfile(scanner, admin);
                    break;
                case 2:
                    manageEvents(scanner);
                    break;
                case 3:
                    manageCustomers(scanner);
                    break;
                case 4:
                    manageArtists(scanner);
                    break;
                case 5:
                    manageReviews(scanner);
                    break;
                case 6:
                    manageTickets(scanner);
                    break;
                case 7:
                    manageLocations(scanner);
                    break;
                case 8:
                    logOut();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void manageEvents(Scanner scanner) {
        System.out.println("1. Show events");
        System.out.println("2. Add event");
        System.out.println("3. Update event");
        System.out.println("4. Delete event");
        System.out.println("5. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            /*List<Event> events = getFutureEvents();
            List<Event> pastEvents = getPastEvents();
            events.addAll(pastEvents);*/
            List<Event> events = getEventRepository().getEvents();
            for (Event event : events)
                System.out.println(event);
            AuditService.getInstance().logAction("Showed events");
        } else if (option == 2) {
            System.out.println("Enter the type of event you want to add:");
            System.out.println("1. Concert");
            System.out.println("2. Film screening");
            System.out.println("3. Theater play");
            System.out.print("Enter option: ");
            int eventType = scanner.nextInt();
            addEvent(scanner, eventType);
        } else if (option == 3) {
            List<Event> futureEvents = getFutureEvents();
            List<Event> pastEvents = getPastEvents();
            for (Event event : futureEvents)
                System.out.println(event);
            for (Event event : pastEvents)
                System.out.println(event);
            System.out.println("Enter the id of the event you want to update:");
            int id = scanner.nextInt();
            for (Event event : futureEvents)
                if (event.getEventId() == id) {
                    updateEvent(event, scanner);
                    break;
                }
            for (Event event : pastEvents)
                if (event.getEventId() == id) {
                    updateEvent(event, scanner);
                    break;
                }
        } else if (option == 4) {
            List<Event> futureEvents = getFutureEvents();
            List<Event> pastEvents = getPastEvents();
            for (Event event : futureEvents)
                System.out.println(event);
            for (Event event : pastEvents)
                System.out.println(event);
            System.out.println("Enter the id of the event you want to delete:");
            int id = scanner.nextInt();
            for (Event event : futureEvents)
                if (event.getEventId() == id) {
                    futureEvents.remove(event);
                    getEventRepository().deleteEvent(event);
                    AuditService.getInstance().logAction("Deleted event");
                    break;
                }
            for (Event event : pastEvents)
                if (event.getEventId() == id) {
                    pastEvents.remove(event);
                    getEventRepository().deleteEvent(event);
                    AuditService.getInstance().logAction("Deleted event");
                    break;
                }
            setFutureEvents(futureEvents);
            setPastEvents(pastEvents);
        } else if (option == 5)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageCustomers(Scanner scanner) {
        System.out.println("1. Show customers");
        System.out.println("2. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            //List<Customer> customers = getRegistrationService().getCustomers();
            List<Customer> customers = getCustomerRepository().getCustomers();
            for (Customer customer : customers)
                System.out.println(customer);
            AuditService.getInstance().logAction("Showed customers");
        } else if (option == 2)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageArtists(Scanner scanner) {
        System.out.println("1. Show artists");
        System.out.println("2. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            //List<Artist> artists = getRegistrationService().getArtists();
            List<Artist> artists = getArtistRepository().getArtists();
            for (Artist artist : artists)
                System.out.println(artist);
            AuditService.getInstance().logAction("Showed artists");
        } else if (option == 2)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageReviews(Scanner scanner) {
        System.out.println("1. Show reviews");
        System.out.println("2. Update review");
        System.out.println("3. Delete review");
        System.out.println("4. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            List<Event> pastEvents = getPastEvents();
            for (Event event : pastEvents)
                System.out.println(event);
            System.out.println("Enter the id of the event you want to see reviews for:");
            int id = scanner.nextInt();
            for (Event event : pastEvents)
                if (event.getEventId() == id) {
                    //event.showReviews();
                    List<Review> reviews = getReviewRepository().getReviewsByEventId(getEventRepository().getEventId(event));
                    for (Review review : reviews)
                        System.out.println(review);
                    AuditService.getInstance().logAction("Showed reviews");
                    break;
                }
        } else if (option == 2) {
            List<Event> pastEvents = getPastEvents();
            for (Event event : pastEvents)
                System.out.println(event);
            System.out.println("Enter the id of the event you want to update a review for:");
            int id = scanner.nextInt();
            for (Event event : pastEvents)
                if (event.getEventId() == id) {
                    //event.showReviews();
                    List<Review> reviews = getReviewRepository().getReviewsByEventId(getEventRepository().getEventId(event));
                    for (Review review : reviews)
                        System.out.println(review);
                    System.out.println("Enter the id of the review you want to update:");
                    int reviewId = scanner.nextInt();
                    //List<Review> reviews = event.getReviews();
                    for (Review review : reviews)
                        if (review.getReviewId() == reviewId) {
                            System.out.print("Enter new review: ");
                            String newReview = scanner.nextLine();
                            System.out.println("Enter new rating: ");
                            double newRating = scanner.nextDouble();
                            getReviewRepository().updateReview(review, newRating, newReview);
                            review.setComment(newReview);
                            review.setRating(newRating);
                            AuditService.getInstance().logAction("Updated review");
                            break;
                        }
                    break;
                }
        } else if (option == 3) {
            List<Event> pastEvents = getPastEvents();
            for (Event event : pastEvents)
                System.out.println(event);
            System.out.println("Enter the id of the event you want to delete a review for:");
            int id = scanner.nextInt();
            for (Event event : pastEvents)
                if (event.getEventId() == id) {
                    //event.showReviews();
                    List<Review> reviews = getReviewRepository().getReviewsByEventId(getEventRepository().getEventId(event));
                    for (Review review : reviews)
                        System.out.println(review);
                    System.out.println("Enter the id of the review you want to delete:");
                    int reviewId = scanner.nextInt();
                    //List<Review> reviews = event.getReviews();
                    for (Review review : reviews)
                        if (review.getReviewId() == reviewId) {
                            reviews.remove(review);
                            getReviewRepository().deleteReview(review);
                            AuditService.getInstance().logAction("Deleted review");
                            break;
                        }
                    break;
                }
        } else if (option == 4)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageTickets(Scanner scanner) {
        System.out.println("1. Show tickets");
        System.out.println("2. Delete ticket");
        System.out.println("3. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            //List<Ticket> tickets = getTickets();
            List<Ticket> tickets = getTicketRepository().getTickets();
            for (Ticket ticket : tickets)
                System.out.println(ticket);
            AuditService.getInstance().logAction("Showed tickets");
        } else if (option == 2) {
            //List<Ticket> tickets = getTickets();
            List<Ticket> tickets = getTicketRepository().getTickets();
            for (Ticket ticket : tickets)
                System.out.println(ticket);
            System.out.println("Enter the id of the ticket you want to delete:");
            int id = scanner.nextInt();
            for (Ticket ticket : tickets)
                if (ticket.getTicketId() == id) {
                    tickets.remove(ticket);
                    getTicketRepository().deleteTicket(ticket);
                    AuditService.getInstance().logAction("Deleted ticket");
                    break;
                }
            //setTickets(tickets);
        } else if (option == 3)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageLocations(Scanner scanner) {
        System.out.println("1. Show locations");
        System.out.println("2. Add location");
        System.out.println("3. Update location");
        System.out.println("4. Delete location");
        System.out.println("5. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            List<Location> locations = getLocationRepository().getLocations();
            for (Location location : locations)
                System.out.println(location);
            AuditService.getInstance().logAction("Showed locations");
        } else if (option == 2) {
            System.out.print("Enter location: ");
            Location location = new Location();
            location.fromInputUser(scanner);
            getLocationRepository().addLocation(location);
            AuditService.getInstance().logAction("Added location");
        } else if (option == 3) {
            List<Location> locations = getLocationRepository().getLocations();
            for (Location location : locations)
                System.out.println(location);
            System.out.print("Enter location id: ");
            int locationId = scanner.nextInt();
            Location location = getLocationRepository().getLocationById(locationId);
            location.fromInputUser(scanner);
            getLocationRepository().updateLocation(location);
            AuditService.getInstance().logAction("Updated location");
        } else if (option == 4) {
            List<Location> locations = getLocationRepository().getLocations();
            for (Location location : locations)
                System.out.println(location);
            System.out.print("Enter location id: ");
            int locationId = scanner.nextInt();
            getLocationRepository().deleteLocation(locationId);
            AuditService.getInstance().logAction("Deleted location");
        } else if (option == 5)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }
}
