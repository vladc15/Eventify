import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// singleton
public class App {
    private static App instance = null;

    /*private List<Customer> customers;
    private List<Artist> artists;
    private Admin admin;
    private Set<Integer> ids;*/
    private RegistrationService registrationService;

    private List<Event> futureEvents;
    private List<Event> pastEvents;
    private List<Ticket> tickets;

    private App() {
        this.futureEvents = new ArrayList<Event>();
        this.pastEvents = new ArrayList<Event>();
        this.tickets = new ArrayList<Ticket>();
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

//    public List<Customer> getCustomers() { return registrationService.getCustomers(); }
//    public List<Artist> getArtists() { return registrationService.getArtists(); }
//    public Admin getAdmin() { return registrationService.getAdmin(); }
//    public Set<Integer> getIds() { return registrationService.getIds(); }
    public List<Event> getFutureEvents() { return futureEvents; }
    public List<Event> getPastEvents() { return pastEvents; }
    public List<Ticket> getTickets() { return tickets; }
    public RegistrationService getRegistrationService() { return registrationService; }
    
//    public void setCustomers(List<Customer> customers) { this.customers = new ArrayList<Customer>(customers); }
//    public void setArtists(List<Artist> artists) { this.artists = new ArrayList<Artist>(artists); }
//    public void setAdmin(Admin admin) { this.admin = admin; }
//    public void setIds(Set<Integer> ids) { this.ids = new HashSet<Integer>(ids); }
    public void setFutureEvents(List<Event> futureEvents) { this.futureEvents = new ArrayList<Event>(futureEvents); }
    public void setPastEvents(List<Event> pastEvents) { this.pastEvents = new ArrayList<Event>(pastEvents); }
    public void setTickets(List<Ticket> tickets) { this.tickets = new ArrayList<Ticket>(tickets); }
    
//    public void addCustomer(Customer customer) { customers.add(customer); }
//    public void addArtist(Artist artist) { artists.add(artist); }
    public void addFutureEvent(Event event) { futureEvents.add(event); }
    public void addPastEvent(Event event) { pastEvents.add(event); }
    public void addTicket(Ticket ticket) { tickets.add(ticket); }
    
//    public void removeCustomer(Customer customer) { customers.remove(customer); }
//    public void removeArtist(Artist artist) { artists.remove(artist); }
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

    public void signUpAdmin(Scanner scanner) { registrationService.signUpAdmin(scanner); }

    public void signUpCustomer(Scanner scanner) { registrationService.signUpCustomer(scanner); }

    public void signUpArtist(Scanner scanner) { registrationService.signUpArtist(scanner); }

}
