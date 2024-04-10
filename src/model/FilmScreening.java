package model;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import user.Artist;

public class FilmScreening extends Event {
    private String dimension;
    private boolean imax;
    private int releaseYear;
    private boolean premiere;
    private int appropriateAge;
    private boolean qa;
    
    public FilmScreening() {
        super();
        this.dimension = "";
        this.imax = false;
        this.releaseYear = 0;
        this.premiere = false;
        this.appropriateAge = 0;
        this.qa = false;
    }
    
    public FilmScreening(int eventId, String name, String description, String date, String time, int duration, int totalTickets,
                         int availableTickets, Location location, List<Artist> artists, Map<String, Double> ticketPrices, String genre,
                         String dimension, boolean imax, int releaseYear, boolean premiere, int appropriateAge, boolean qa) {
        super(eventId, name, description, date, time, duration, totalTickets, availableTickets, location, artists, ticketPrices, genre);
        this.dimension = dimension;
        this.imax = imax;
        this.releaseYear = releaseYear;
        this.premiere = premiere;
        this.appropriateAge = appropriateAge;
        this.qa = qa;
    }
    
    public FilmScreening(FilmScreening filmScreening) {
        super(filmScreening);
        this.dimension = filmScreening.getDimension();
        this.imax = filmScreening.getImax();
        this.releaseYear = filmScreening.getReleaseYear();
        this.premiere = filmScreening.getPremiere();
        this.appropriateAge = filmScreening.getAppropriateAge();
        this.qa = filmScreening.getQA();
    }
    
    public String getDimension() { return dimension; }
    public boolean getImax() { return imax; }
    public int getReleaseYear() { return releaseYear; }
    public boolean getPremiere() { return premiere; }   
    public int getAppropriateAge() { return appropriateAge; }
    public boolean getQA() { return qa; }
    
    public void setDimension(String dimension) { this.dimension = dimension; }
    public void setImax(boolean imax) { this.imax = imax; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public void setPremiere(boolean premiere) { this.premiere = premiere; }
    public void setAppropriateAge(int appropriateAge) { this.appropriateAge = appropriateAge; }
    public void setQA(boolean qa) { this.qa = qa; }
    
    public String toString() { return super.toString() + ", Dimension: " + dimension + ", IMAX: " + imax + ", Release Year: " + releaseYear + ", Premiere: " + premiere + ", Appropriate Age: " + appropriateAge + ", QA: " + qa;}
    
    public String toCSV() { return super.toCSV() + "," + dimension + "," + imax + "," + releaseYear + "," + premiere + "," + appropriateAge + "," + qa;}
    
    public boolean equals(FilmScreening filmScreening) { return super.equals(filmScreening) && dimension.equals(filmScreening.getDimension()) && imax == filmScreening.getImax() && releaseYear == filmScreening.getReleaseYear() && premiere == filmScreening.getPremiere() && appropriateAge == filmScreening.getAppropriateAge() && qa == filmScreening.getQA();}
    
    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        super.fromCSV(values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4] + "," + values[5] + "," + values[6] + "," + values[7] + "," + values[8] + "," + values[9] + "," + values[10]);
        this.dimension = values[11];
        this.imax = Boolean.parseBoolean(values[12]);
        this.releaseYear = Integer.parseInt(values[13]);
        this.premiere = Boolean.parseBoolean(values[14]);
        this.appropriateAge = Integer.parseInt(values[15]);
        this.qa = Boolean.parseBoolean(values[16]);
    }
    
    public void fromInput(Scanner scanner) {
        super.fromInput(scanner);
        System.out.print("Enter dimension: ");
        this.dimension = scanner.nextLine();
        System.out.print("Is IMAX? (true/false): ");
        this.imax = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Enter release year: ");
        this.releaseYear = scanner.nextInt();
        System.out.print("Is premiere? (true/false): ");
        this.premiere = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Enter appropriate age: ");
        this.appropriateAge = scanner.nextInt();
        System.out.print("Is QA? (true/false): ");
        this.qa = Boolean.parseBoolean(scanner.nextLine());
    }
    
    public Artist getDirector() { return artists.get(0); }
    public List<Artist> getActors() { return artists.subList(1, artists.size()); }
    
}
