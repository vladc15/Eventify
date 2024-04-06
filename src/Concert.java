import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Concert extends Event {
    //private String genre;
    private Boolean isSeated;
    //private Artist headliner; // conventie ca primul artist din lista de artisti sa fie headliner
    //private List<Artist> openers; // restul sa fie openeri
    private Boolean afterParty; // si daca este true, ultimul sa fie DJ
    private Boolean meetAndGreet;

    public Concert() {
        super();
        this.isSeated = false;
        this.afterParty = false;
        this.meetAndGreet = false;
    }
    
    public Concert(int eventId, String name, String description, String date, String time, int duration, int totalTickets,
                   int availableTickets, Location location, List<Artist> artists, Map<String, Double> ticketPrices, String genre,
                   Boolean isSeated, Boolean afterParty, Boolean meetAndGreet) {
        super(eventId, name, description, date, time, duration, totalTickets, availableTickets, location, artists, ticketPrices, genre);
        this.isSeated = isSeated;
        this.afterParty = afterParty;
        this.meetAndGreet = meetAndGreet;
    }
    
    public Concert(Concert concert) {
        super(concert);
        this.isSeated = concert.getIsSeated();
        this.afterParty = concert.getAfterParty();
        this.meetAndGreet = concert.getMeetAndGreet();
    }
    
    public Boolean getIsSeated() { return isSeated; }
    public Boolean getAfterParty() { return afterParty; }
    public Boolean getMeetAndGreet() { return meetAndGreet; }
    
    public void setIsSeated(Boolean isSeated) { this.isSeated = isSeated; }
    public void setAfterParty(Boolean afterParty) { this.afterParty = afterParty; }
    public void setMeetAndGreet(Boolean meetAndGreet) { this.meetAndGreet = meetAndGreet; }
    
    public String toString() { return super.toString() + ", Seated: " + isSeated + ", After Party: " + afterParty + ", Meet and Greet: " + meetAndGreet;}
    
    public String toCSV() { return super.toCSV() + "," + isSeated + "," + afterParty + "," + meetAndGreet; }
    
    public boolean equals(Concert concert) { return super.equals(concert) && isSeated == concert.getIsSeated() && afterParty == concert.getAfterParty() && meetAndGreet == concert.getMeetAndGreet(); }
    
    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        super.fromCSV(values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4] + "," + values[5] + "," + values[6] + "," + values[7] + "," + values[8] + "," + values[9] + "," + values[10]);
        this.isSeated = Boolean.parseBoolean(values[11]);
        this.afterParty = Boolean.parseBoolean(values[12]);
        this.meetAndGreet = Boolean.parseBoolean(values[13]);
    }
    
    public void fromInput(Scanner scanner) {
        super.fromInput(scanner);
        System.out.print("Is seated? (true/false): ");
        this.isSeated = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("After party? (true/false): ");
        this.afterParty = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Meet and greet? (true/false): ");
        this.meetAndGreet = Boolean.parseBoolean(scanner.nextLine());
    }
    
    public Artist getHeadliner() { return artists.get(0); }
    public List<Artist> getOpeners() {
        if (afterParty)
            return artists.subList(1, artists.size() - 2);
        else
            return artists.subList(1, artists.size() - 1);
    }
    public Artist getDJ() {
        if (afterParty)
            return artists.get(artists.size() - 1);
        else
            return null;
    }
    
}
