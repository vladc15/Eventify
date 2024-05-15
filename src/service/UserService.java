package service;

import application.App;
import model.*;
import repository.*;
import user.Artist;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface UserService {
    default LocationRepository getLocationRepository() { return new LocationRepository(); }
    default EventRepository getEventRepository() { return new EventRepository(); }
    default UserRepository getUserRepository() { return new UserRepository(); }
    default ConcertRepository getConcertRepository() { return new ConcertRepository(); }
    default FilmScreeningRepository getFilmScreeningRepository() { return new FilmScreeningRepository(); }
    default TheatrePlayRepository getTheatrePlayRepository() { return new TheatrePlayRepository(); }
    default CustomerRepository getCustomerRepository() { return new CustomerRepository(); }
    default ArtistRepository getArtistRepository() { return new ArtistRepository(); }
    default AdminRepository getAdminRepository() { return new AdminRepository(); }
    default ReviewRepository getReviewRepository() { return new ReviewRepository(); }
    default TicketRepository getTicketRepository() { return new TicketRepository(); }
    default ConcertTicketRepository getConcertTicketRepository() { return new ConcertTicketRepository(); }
    default FilmScreeningTicketRepository getFilmScreeningTicketRepository() { return new FilmScreeningTicketRepository(); }
    default TheatrePlayTicketRepository getTheatrePlayTicketRepository() { return new TheatrePlayTicketRepository(); }
    default EventArtistRepository getEventArtistRepository() { return new EventArtistRepository(); }
    default CustomerFollowedArtistsRepository getCustomerFollowedArtistsRepository() { return new CustomerFollowedArtistsRepository(); }
    default CustomerFavoritesRepository getCustomerFavoritesRepository() { return new CustomerFavoritesRepository(); }
    default CustomerHistoryRepository getCustomerHistoryRepository() { return new CustomerHistoryRepository(); }
    default CustomerTicketsRepository getCustomerTicketsRepository() { return new CustomerTicketsRepository(); }
    default MapEventRepository getMapEventRepository() { return new MapEventRepository(); }

    void showMenu();
    void executeAction(Scanner scanner);

    default RegistrationService getRegistrationService() { return App.getInstance().getRegistrationService(); }
    default void logOut() { App.getInstance().logOut(); }


    default List<Event> getFutureEvents() { return App.getInstance().getFutureEvents(); }
    default List<Event> getPastEvents() { return App.getInstance().getPastEvents(); }

    default void setFutureEvents(List<Event> futureEvents) { App.getInstance().setFutureEvents(futureEvents); }
    default void setPastEvents(List<Event> pastEvents) { App.getInstance().setPastEvents(pastEvents); }

    default List<Ticket> getTickets() { /*return App.getInstance().getTickets();*/ return getTicketRepository().getTickets(); }
    //default void setTickets(List<Ticket> tickets) { App.getInstance().setTickets(tickets); }
    default void addTicket(Ticket ticket) { /*App.getInstance().addTicket(ticket);*/
        if (ticket instanceof ConcertTicket)
            getConcertTicketRepository().addConcertTicket((ConcertTicket) ticket);
        else if (ticket instanceof FilmScreeningTicket)
            getFilmScreeningTicketRepository().addFilmScreeningTicket((FilmScreeningTicket) ticket);
        else if (ticket instanceof TheatrePlayTicket)
            getTheatrePlayTicketRepository().addTheatrePlayTicket((TheatrePlayTicket) ticket);
    }

    default void moveEventsToPast() { App.getInstance().moveEventsToPast(); }

    default Event updateEvent(Event event, Scanner scanner) {
        System.out.println("Specify what do you want to update: ");
        System.out.println("1. Name");
        System.out.println("2. Description");
        System.out.println("3. Date");
        System.out.println("4. Time");
        System.out.println("5. Duration");
        System.out.println("6. Location");
        System.out.println("7. Ticket types and prices");
        System.out.println("9. Genre");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter new name: ");
                event.setName(scanner.nextLine());
                getEventRepository().updateEventById(event.getEventId(), event);
                AuditService.getInstance().logAction("Event name updated");
                break;
            case 2:
                System.out.println("Enter new description: ");
                event.setDescription(scanner.nextLine());
                getEventRepository().updateEventById(event.getEventId(), event);
                AuditService.getInstance().logAction("Event description updated");
                break;
            case 3:
                System.out.println("Enter new date: ");
                event.setDate(scanner.nextLine());
                getEventRepository().updateEventById(event.getEventId(), event);
                AuditService.getInstance().logAction("Event date updated");
                break;
            case 4:
                System.out.println("Enter new time: ");
                event.setTime(scanner.nextLine());
                getEventRepository().updateEventById(event.getEventId(), event);
                AuditService.getInstance().logAction("Event time updated");
                break;
            case 5:
                System.out.println("Enter new duration: ");
                event.setDuration(Integer.parseInt(scanner.nextLine()));
                getEventRepository().updateEventById(event.getEventId(), event);
                AuditService.getInstance().logAction("Event duration updated");
                break;
            case 6:
                System.out.println("Enter new location: ");
                Location newLocation = new Location();
                newLocation.fromInputUser(scanner);
                event.setLocation(newLocation);
                getEventRepository().updateEventById(event.getEventId(), event);
                AuditService.getInstance().logAction("Event location updated");
                break;
            case 7:
                System.out.println("Do you want to update existing ticket prices or add new ones? (update/add)");
                String response = scanner.nextLine();
                if (response.equals("update")) {
                    System.out.println("Enter the type of the ticket you want to update: ");
                    String type = scanner.nextLine();
                    System.out.println("Enter new price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    event.updateTickets(type, price);
                    getMapEventRepository().updateMap(event.getEventId(), type, price);
                    AuditService.getInstance().logAction("Event ticket updated");
                }
                else if (response.equals("add")) {
                    System.out.println("Enter the number of new ticket types: ");
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < n; i++) {
                        System.out.println("Enter ticket type: ");
                        String type = scanner.nextLine();
                        System.out.println("Enter ticket price: ");
                        String price = scanner.nextLine();
                        event.updateTickets(type, Double.parseDouble(price));
                        getMapEventRepository().addToMap(event.getEventId(), type, Double.parseDouble(price));
                        AuditService.getInstance().logAction("Event ticket added");
                    }
                }
                else
                    System.out.println("Invalid choice");
                break;
            case 8:
                System.out.println("Enter new genre: ");
                event.setGenre(scanner.nextLine());
                getEventRepository().updateEventById(event.getEventId(), event);
                AuditService.getInstance().logAction("Event genre updated");
                break;
            default:
                System.out.println("Invalid choice");
        }
        return event;
    }

    default void addEvent(Scanner scanner, int eventType) {
        System.out.println("These are the artists available: ");
        List<Artist> artists = getArtistRepository().getArtists();
        for (Artist artist : artists)
            System.out.println(artist);
        System.out.println("Enter the IDs of the artists you want to add to the event: ");
        scanner.nextLine();
        String[] artistIds = scanner.nextLine().split(" ");
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < artistIds.length; i++)
            ids.add(Integer.parseInt(artistIds[i]));
        List<Artist> eventArtists = new ArrayList<>();
        for (int id : ids)
            for (Artist artist : artists)
                if (artist.getUserId() == id) {
                    eventArtists.add(artist);
                    break;
                }
        Event event = null;
        if (eventType == 1) {
            event = new Concert();
            event.setArtists(eventArtists);
            event.fromInputWithoutArtists(scanner);
            ConcertRepository concertRepository = getConcertRepository();
            concertRepository.addConcert((Concert) event);
            AuditService.getInstance().logAction("Concert added");
        }
        else if (eventType == 2) {
            event = new FilmScreening();
            event.setArtists(eventArtists);
            event.fromInputWithoutArtists(scanner);
            FilmScreeningRepository filmScreeningRepository = getFilmScreeningRepository();
            filmScreeningRepository.addFilmScreening((FilmScreening) event);
            AuditService.getInstance().logAction("Film screening added");
        }
        else if (eventType == 3) {
            event = new TheatrePlay();
            event.setArtists(eventArtists);
            event.fromInputWithoutArtists(scanner);
            TheatrePlayRepository theatrePlayRepository = getTheatrePlayRepository();
            theatrePlayRepository.addTheatrePlay((TheatrePlay) event);
            AuditService.getInstance().logAction("Theatre play added");
        }
        else
            System.out.println("Invalid event type");
        if (event != null) {
            getFutureEvents().add(event);
            System.out.println("Event added successfully!");
        }
    }


    default void updateTicket(int id) {
        List<Ticket> tickets = getTickets();
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == id) {
                ticket = updateTicket(ticket, new Scanner(System.in));
                break;
            }
        }
        //setTickets(tickets);
    }

    default Ticket updateTicket(Ticket ticket, Scanner scanner) {
        System.out.println("Specify what do you want to update: ");
        System.out.println("1. Ticket type");
        System.out.println("2. Seat");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        TicketRepository ticketRepository = getTicketRepository();
        switch (choice) {
            case 1:
                System.out.println("Enter new ticket type: ");

                ticket.setType(scanner.nextLine());
                ticketRepository.updateType(ticketRepository.getTicketId(ticket), scanner.nextLine());
                AuditService.getInstance().logAction("Ticket type updated");

                break;
            case 2:
                System.out.println("Enter new seat: ");

                ticket.setSeat(scanner.nextLine());
                ticketRepository.updateSeat(ticketRepository.getTicketId(ticket), scanner.nextLine());
                AuditService.getInstance().logAction("Ticket seat updated");

                break;
            default:
                System.out.println("Invalid choice");
        }
        return ticket;
    }

    default void manageProfile(Scanner scanner, User user) {
        System.out.println("1. Show profile");
        System.out.println("2. Change profile details");
        System.out.println("3. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            System.out.println(user);
            AuditService.getInstance().logAction("Profile shown");
        } else if (option == 2) {
            System.out.println("1. Change name");
            System.out.println("2. Change age");
            System.out.println("3. Change location");
            System.out.print("Enter option: ");
            int option2 = scanner.nextInt();
            scanner.nextLine();
            UserRepository userRepository = getUserRepository();
            LocationRepository locationRepository = getLocationRepository();
            switch (option2) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();

                    user.setName(newName);
                    userRepository.updateName(newName, userRepository.getUserId(user));
                    AuditService.getInstance().logAction("Name updated");

                    break;
                case 2:
                    System.out.print("Enter new age: ");
                    int newAge = scanner.nextInt();

                    user.setAge(newAge);
                    userRepository.updateAge(newAge, userRepository.getUserId(user));
                    AuditService.getInstance().logAction("Age updated");

                    break;
                case 3:
                    System.out.print("Enter new location: ");
                    Location newLocation = new Location();
                    newLocation.fromInput(scanner);

                    user.setLocation(newLocation);
                    int locationId = locationRepository.getLocationId(newLocation);
                    if (locationId != -1)
                        userRepository.updateLocation(locationId, userRepository.getUserId(user));
                    else {
                        locationRepository.addLocation(newLocation);
                        userRepository.updateLocation(locationRepository.getLocationId(newLocation), userRepository.getUserId(user));
                    }
                    AuditService.getInstance().logAction("Location updated");

                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        else if (option == 3)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

}
