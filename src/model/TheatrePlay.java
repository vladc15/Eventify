package model;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import user.Artist;

public class TheatrePlay extends Event {
    private boolean intermission;
    private boolean qa;
    // primul din lista de artisti este regizorul
    // restul sunt actori

    public TheatrePlay() {
        super();
        this.intermission = false;
        this.qa = false;
    }
    
    public TheatrePlay(int eventId, String name, String description, String date, String time, int duration, int totalTickets,
                       int availableTickets, Location location, List<Artist> artists, Map<String, Double> ticketPrices, String genre,
                       boolean intermission, boolean qa) {
        super(eventId, name, description, date, time, duration, totalTickets, availableTickets, location, artists, ticketPrices, genre);
        this.intermission = intermission;
        this.qa = qa;
    }
    
    public TheatrePlay(TheatrePlay theatrePlay) {
        super(theatrePlay);
        this.intermission = theatrePlay.getIntermission();
        this.qa = theatrePlay.getQA();
    }
    
    public boolean getIntermission() { return intermission; }
    public boolean getQA() { return qa; }
    
    public void setIntermission(boolean intermission) { this.intermission = intermission; }
    public void setQA(boolean qa) { this.qa = qa; }
    
    public String toString() { return super.toString() + ", Intermission: " + intermission + ", Questions and Answers: " + qa;}
    
    public String toCSV() { return super.toCSV() + "," + intermission + "," + qa; }
    
    public boolean equals(TheatrePlay theatrePlay) { return super.equals(theatrePlay) && intermission == theatrePlay.getIntermission() && qa == theatrePlay.getQA(); }
    
    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        super.fromCSV(values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4] + "," + values[5] + "," + values[6] + "," + values[7] + "," + values[8] + "," + values[9] + "," + values[10]);
        this.intermission = Boolean.parseBoolean(values[11]);
        this.qa = Boolean.parseBoolean(values[12]);
    }
    
    public void fromInput(Scanner scanner) {
        super.fromInput(scanner);
        System.out.print("Enter intermission: ");
        this.intermission = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Enter questions and answers: ");
        this.qa = Boolean.parseBoolean(scanner.nextLine());
    }

    public void fromInputWithoutArtists(Scanner scanner) {
        super.fromInputWithoutArtists(scanner);
        System.out.print("Enter intermission: ");
        this.intermission = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Enter questions and answers: ");
        this.qa = Boolean.parseBoolean(scanner.nextLine());
    }
    
    public Artist getDirector() { return artists.get(0); }
    public List<Artist> getActors() { return artists.subList(1, artists.size()); }
    
}
