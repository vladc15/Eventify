package service;

import model.FilmScreening;
import model.TheatrePlay;
import model.Ticket;
import model.Concert;
import model.Event;
import model.FilmScreeningTicket;
import model.TheatrePlayTicket;
import model.ConcertTicket;
import user.Artist;

import java.util.List;
import java.util.Scanner;

public class ArtistService implements UserService {
    private static ArtistService instance = null;
    private Artist artist;

    private ArtistService() {
        this.artist = new Artist();
    }

    private ArtistService(Artist artist) {
        this.artist = artist;
    }

    public static ArtistService getInstance() {
        if (instance == null) {
            instance = new ArtistService();
        }
        return instance;
    }

    public static ArtistService getInstance(Artist artist) {
        if (instance == null)
            instance = new ArtistService(artist);
        return instance;
    }

    public Artist getArtist() { return artist; }

    public void showMenu() {
        System.out.println("\n----------Artist menu:----------");
        System.out.println("1. Access profile");
        System.out.println("2. Access bio");
        System.out.println("3. Access genre");
        System.out.println("4. Access rating");
        System.out.println("6. Access reviews");
        System.out.println("7. Access events");
        System.out.println("8. Access tickets");
        System.out.println("9. Log out");
    }

    public void executeAction(Scanner scanner) {
        while (true) {
            this.showMenu();
            moveEventsToPast();
            System.out.print("Enter option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    manageProfile(scanner, artist);
                    break;
                case 2:
                    manageBio(scanner);
                    break;
                case 3:
                    manageGenre(scanner);
                    break;
                case 4:
                    manageRating(scanner);
                    break;
                case 6:
                    manageReviews(scanner);
                    break;
                case 7:
                    manageEvents(scanner);
                    break;
                case 8:
                    manageTickets(scanner);
                    break;
                case 9:
                    logOut();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void manageBio(Scanner scanner) {
        System.out.println("1. Show bio");
        System.out.println("2. Change bio");
        System.out.println("3. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        if (option == 1) {
            System.out.println("Bio: " + artist.getBio());
        } else if (option == 2) {
            System.out.print("Enter new bio: ");
            String newBio = scanner.nextLine();
            artist.setBio(newBio);
        } else if (option == 3)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageGenre(Scanner scanner) {
        System.out.println("1. Show genre");
        System.out.println("2. Change genre");
        System.out.println("3. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        if (option == 1) {
            System.out.println("Genre: " + artist.getGenre());
        } else if (option == 2) {
            System.out.print("Enter new genre: ");
            String newGenre = scanner.nextLine();
            artist.setGenre(newGenre);
        } else if (option == 3)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageRating(Scanner scanner) {
        System.out.println("1. Show rating");
        System.out.println("2. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            System.out.println("Rating: " + artist.getRating());
        } else if (option == 2)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageReviews(Scanner scanner) {
        System.out.println("1. Show reviews");
        System.out.println("2. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            List<Event> pastEvents = getPastEvents();
            for (Event event : pastEvents)
                if (event.getArtists().contains(artist)) {
                    System.out.println("Reviews for event " + event.getName() + ":");
                    event.showReviews();
                }
        } else if (option == 2)
            return;
        else
            System.out.println("Invalid option. Try again.");
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
            System.out.println("These are your events:");
            List<Event> events = getFutureEvents();
            List<Event> pastEvents = getPastEvents();
            events.addAll(pastEvents);
            for (Event event : events)
                if (event.getArtists().contains(artist))
                    System.out.println(event);
        } else if (option == 2) {
            System.out.println("Enter the type of event you want to add:");
            System.out.println("1. Concert");
            System.out.println("2. Film screening");
            System.out.println("3. Theater play");
            System.out.print("Enter option: ");
            int eventType = scanner.nextInt();
            addEvent(scanner, eventType);
        } else if (option == 3) {
            List<Event> events = getFutureEvents();
            for (Event event : events)
                if (event.getArtists().contains(artist))
                    System.out.println(event);
            System.out.println("Enter the id of the event you want to update:");
            int id = scanner.nextInt();
            int okId = 0;
            for (Event event : events)
                if (event.getEventId() == id && event.getArtists().contains(artist)) {
                    event = updateEvent(event, scanner);
                    okId = 1;
                    break;
                }
            if (okId == 0)
                System.out.println("Invalid id.");
        } else if (option == 4) {
            System.out.println("These are your events:");
            List<Event> futureEvents = getFutureEvents();
            List<Event> pastEvents = getPastEvents();
            for (Event event : futureEvents)
                if (event.getArtists().contains(artist))
                    System.out.println(event);
            for (Event event : pastEvents)
                if (event.getArtists().contains(artist))
                    System.out.println(event);
            System.out.println("Enter the id of the event you want to delete:");
            int id = scanner.nextInt();
            for (Event event : futureEvents)
                if (event.getEventId() == id) {
                    futureEvents.remove(event);
                    break;
                }
            for (Event event : pastEvents)
                if (event.getEventId() == id) {
                    pastEvents.remove(event);
                    break;
                }
            setFutureEvents(futureEvents);
            setPastEvents(pastEvents);
        } else if (option == 5)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

    private void manageTickets(Scanner scanner) {
        System.out.println("1. Show tickets");
        System.out.println("2. Add ticket");
        System.out.println("3. Update ticket");
        System.out.println("4. Delete ticket");
        System.out.println("5. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            List<Ticket> tickets = getTickets();
            for (Ticket ticket : tickets)
                if (ticket.getEvent().getArtists().contains(artist))
                    System.out.println(ticket);
        } else if (option == 2) {
            System.out.println("These are your events:");
            List<Event> futureEvents = getFutureEvents();
            for (Event event : futureEvents)
                if (event.getArtists().contains(artist))
                    System.out.println(event);
            System.out.println("Enter the id of the event you want to add a ticket to:");
            int id = scanner.nextInt();
            Event eventChosen = null;
            int eventType = 0; // 0 - concert, 1 - film screening, 2 - theater play
            for (Event event : futureEvents)
                if (event.getEventId() == id) {
                    eventChosen = event;
                    if (event instanceof Concert)
                        eventType = 0;
                    else if (event instanceof FilmScreening)
                        eventType = 1;
                    else if (event instanceof TheatrePlay)
                        eventType = 2;
                    break;
                }
            switch (eventType) {
                case 0:
                    // de pus allocatedId in fromInput
                    // todo
                    ConcertTicket concertTicket = new ConcertTicket();
                    concertTicket.fromInputEvent(scanner, eventChosen);
                    addTicket(concertTicket);
                    break;
                case 1:
                    FilmScreeningTicket filmScreeningTicket = new FilmScreeningTicket();
                    filmScreeningTicket.fromInputEvent(scanner, eventChosen);
                    addTicket(filmScreeningTicket);
                    break;
                case 2:
                    TheatrePlayTicket theatrePlayTicket = new TheatrePlayTicket();
                    theatrePlayTicket.fromInputEvent(scanner, eventChosen);
                    addTicket(theatrePlayTicket);
                    break;
                default:
                    System.out.println("Invalid event type.");
            }

        } else if (option == 3) {
            List<Ticket> tickets = getTickets();
            for (Ticket ticket : tickets)
                if (ticket.getEvent().getArtists().contains(artist))
                    System.out.println(ticket);
            System.out.println("Enter the id of the ticket you want to update:");
            int id = scanner.nextInt();
            updateTicket(id);
        } else if (option == 4) {
            List<Ticket> tickets = getTickets();
            for (Ticket ticket : tickets)
                if (ticket.getEvent().getArtists().contains(artist))
                    System.out.println(ticket);
            System.out.println("Enter the id of the ticket you want to delete:");
            int id = scanner.nextInt();
            for (Ticket ticket : tickets)
                if (ticket.getTicketId() == id) {
                    tickets.remove(ticket);
                    break;
                }
            setTickets(tickets);
        } else if (option == 5)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }
}