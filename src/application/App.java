package application;

import model.Event;
import model.Ticket;
import service.RegistrationService;
import user.Artist;
import user.Customer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// singleton
public class App {
    private static App instance = null;

    /*private List<user.Customer> customers;
    private List<user.Artist> artists;
    private user.Admin admin;
    private Set<Integer> ids;*/
    private RegistrationService registrationService;

    private List<Event> futureEvents;
    private List<Event> pastEvents;
    private List<Ticket> tickets;

    private App() {
        this.futureEvents = new ArrayList<>();
        this.pastEvents = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.registrationService = new RegistrationService();
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

//    public List<user.Customer> getCustomers() { return registrationService.getCustomers(); }
//    public List<user.Artist> getArtists() { return registrationService.getArtists(); }
//    public user.Admin getAdmin() { return registrationService.getAdmin(); }
//    public Set<Integer> getIds() { return registrationService.getIds(); }
    public List<Event> getFutureEvents() { return futureEvents; }
    public List<Event> getPastEvents() { return pastEvents; }
    public List<Ticket> getTickets() { return tickets; }
    public RegistrationService getRegistrationService() { return registrationService; }
    
//    public void setCustomers(List<user.Customer> customers) { this.customers = new ArrayList<user.Customer>(customers); }
//    public void setArtists(List<user.Artist> artists) { this.artists = new ArrayList<user.Artist>(artists); }
//    public void setAdmin(user.Admin admin) { this.admin = admin; }
//    public void setIds(Set<Integer> ids) { this.ids = new HashSet<Integer>(ids); }
    public void setFutureEvents(List<Event> futureEvents) { this.futureEvents = new ArrayList<>(futureEvents); }
    public void setPastEvents(List<Event> pastEvents) { this.pastEvents = new ArrayList<>(pastEvents); }
    public void setTickets(List<Ticket> tickets) { this.tickets = new ArrayList<>(tickets); }
    
//    public void addCustomer(user.Customer customer) { customers.add(customer); }
//    public void addArtist(user.Artist artist) { artists.add(artist); }
    public void addFutureEvent(Event event) { futureEvents.add(event); }
    public void addPastEvent(Event event) { pastEvents.add(event); }
    public void addTicket(Ticket ticket) { tickets.add(ticket); }
    
//    public void removeCustomer(user.Customer customer) { customers.remove(customer); }
//    public void removeArtist(user.Artist artist) { artists.remove(artist); }
    public void removeFutureEvent(Event event) { futureEvents.remove(event); }
    public void removePastEvent(Event event) { pastEvents.remove(event); }
    public void removeTicket(Ticket ticket) { tickets.remove(ticket); }
    
    public void showFutureEvents() {
        for (Event event : futureEvents)
            System.out.println(event);
    }
    
    public void showPastEvents() {
        for (Event event : pastEvents)
            System.out.println(event);
    }
    
    public void showTickets() {
        for (Ticket ticket : tickets)
            System.out.println(ticket);
    }
    
    public void moveEventToPast(Event event) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate eventDate = LocalDate.parse(event.getDate(), formatter);
        if (eventDate.isBefore(currentDate)) {
            futureEvents.remove(event);
            pastEvents.add(event);
        }
    }

    public void moveEventsToPast() {
        /*for (Event event : futureEvents)
            moveEventToPast(event);*/
        Iterator<Event> iterator = futureEvents.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate eventDate = LocalDate.parse(event.getDate(), formatter);
            if (eventDate.isBefore(currentDate)) {
                iterator.remove();
                pastEvents.add(event);
            }
        }
    }

    public Event findFutureEvent(Event event) {
        for (Event e : futureEvents)
            if (e.equals(event))
                return e;
        return null;
    }

    public Event findPastEvent(Event event) {
        for (Event e : pastEvents)
            if (e.equals(event))
                return e;
        return null;
    }

    public Event findFutureEventById(int id) {
        for (Event event : futureEvents)
            if (event.getEventId() == id)
                return event;
        return null;
    }

    public Event findPastEventById(int id) {
        for (Event event : pastEvents)
            if (event.getEventId() == id)
                return event;
        return null;
    }

    public int logIn(String username, String password) { return registrationService.logIn(username, password); }
    public int logIn(Scanner scanner) { return registrationService.logIn(scanner); }
    public int logOut() { return registrationService.logOut(); }
    //public void deleteCustomer(Customer customer) { registrationService.deleteCustomer(customer); }
    //public void deleteArtist(Artist artist) { registrationService.deleteArtist(artist); }
    //public void deleteAdmin() { registrationService.deleteAdmin(); }

    public void signUpAdmin(Scanner scanner) { registrationService.signUpAdmin(scanner); }
    public void signUpCustomer(Scanner scanner) { registrationService.signUpCustomer(scanner); }
    public void signUpArtist(Scanner scanner) { registrationService.signUpArtist(scanner); }

}
