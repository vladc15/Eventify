package model;

import java.util.*;

import user.Artist;

public abstract class Event implements Comparable<Event>{
    protected int eventId;
    protected String name;
    protected String description;
    protected String date;
    protected String time;
    protected int duration;
    protected int totalTickets;
    protected int availableTickets;
    //protected double price;
    protected Location location;
    protected List<Artist> artists;
    protected List<List<Character>> seats;

    // for past events
    protected List<Review> reviews;
    //protected double rating; // average rating from all reviews; se poate calcula ca medie a rating-urilor din reviews
    protected Map<String, Double> ticketPrices; // tipul de bilet si pretul lui
    protected String genre;

    public Event() {
        this.eventId = 0;
        this.name = "";
        this.description = "";
        this.date = "";
        this.time = "";
        this.duration = 0;
        this.totalTickets = 0;
        this.availableTickets = 0;
        this.location = new Location();
        this.artists = new ArrayList<Artist>();
        this.seats = new ArrayList<List<Character>>();
        this.reviews = new ArrayList<Review>();
        this.ticketPrices = new HashMap<String, Double>();
        this.genre = "";
    }


    public Event(int eventId, String name, String description, String date, String time, int duration, int totalTickets,
                 int availableTickets, Location location, List<Artist> artists, Map<String, Double> ticketPrices, String genre) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.totalTickets = totalTickets;
        this.availableTickets = availableTickets;
        this.location = new Location(location);
        this.artists = new ArrayList<Artist>(artists);

        //this.seats = new ArrayList<List<Character>>(seats);
        this.seats = new ArrayList<>();
        for (int i = 0; i < location.getRows(); i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < location.getColumns(); j++) {
                row.add('O');
            }
            seats.add(row);
        }
        this.reviews = new ArrayList<Review>();
        this.ticketPrices = new HashMap<String, Double>(ticketPrices);
        this.genre = genre;
    }

    public Event(Event event) {
        this.eventId = event.getEventId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.date = event.getDate();
        this.time = event.getTime();
        this.duration = event.getDuration();
        this.totalTickets = event.getTotalTickets();
        this.availableTickets = event.getAvailableTickets();
        this.location = new Location(event.getLocation());
        this.artists = new ArrayList<Artist>(event.getArtists());
        this.seats = new ArrayList<List<Character>>(event.getSeats());
        this.reviews = new ArrayList<Review>(event.getReviews());
        this.ticketPrices = new HashMap<String, Double>(event.getTicketPrices());
        this.genre = event.getGenre();
    }

    public int getEventId() { return eventId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getDuration() { return duration; }
    public int getTotalTickets() { return totalTickets; }
    public int getAvailableTickets() { return availableTickets; }
    public Location getLocation() { return location; }
    public List<Artist> getArtists() { return artists; }
    public List<List<Character>> getSeats() { return seats; }
    public List<Review> getReviews() { return reviews; }
    public Map<String, Double> getTicketPrices() { return ticketPrices; }
    public String getGenre() { return genre; }

    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }
    public void setAvailableTickets(int availableTickets) { this.availableTickets = availableTickets; }
    public void setLocation(Location location) { this.location = new Location(location); }
    public void setArtists(List<Artist> artists) { this.artists = new ArrayList<Artist>(artists); }
    public void setSeats(List<List<Character>> seats) { this.seats = new ArrayList<List<Character>>(seats); }
    public void setReviews(List<Review> reviews) { this.reviews = new ArrayList<Review>(reviews); }
    public void setTicketPrices(Map<String, Double> ticketPrices) { this.ticketPrices = new HashMap<String, Double>(ticketPrices); }
    public void setGenre(String genre) { this.genre = genre; }

    public String toString() {
        return "model.Event ID: " + eventId + ", Name: " + name + ", Description: " + description +
                ", Date: " + date + ", Time: " + time + ", Duration: " + duration + ", Total Tickets: " + totalTickets +
                ", Available Tickets: " + availableTickets + ", model.Location: " + location + ", Artists: " + artists + ", Seats: " + seats +
                ", Reviews: " + reviews + ", model.Ticket Prices: " + ticketPrices + ", Genre: " + genre;
    }

    public String toCSV() {
        String csv = eventId + "," + name + "," + description + "," + date + "," + time + "," +
                duration + "," + totalTickets + "," + availableTickets + "," + location.toCSV() + ",";
        for (Artist artist : artists) {
            csv += artist.toCSV() + ";";
        }
        csv += ",";
        for (List<Character> row : seats) {
            for (Character seat : row) {
                csv += seat;
            }
            csv += ";";
        }
        csv += ",";
        for (Review review : reviews) {
            csv += review.toCSV() + ";";
        }
        for (String type : ticketPrices.keySet()) {
            csv += type + "," + ticketPrices.get(type) + ";";
        }
        csv += ",";
        csv += genre;
        return csv;
    }

    public boolean equals(Event event) {
        if (eventId != event.getEventId() || !name.equals(event.getName()) || !description.equals(event.getDescription())
                || !date.equals(event.getDate()) || !time.equals(event.getTime()) || duration != event.getDuration() || totalTickets != event.getTotalTickets()
                || availableTickets != event.getAvailableTickets() || !location.equals(event.getLocation()) || artists.size() != event.getArtists().size()
                || seats.size() != event.getSeats().size() || reviews.size() != event.getReviews().size()
                || ticketPrices.size() != event.getTicketPrices().size() || !genre.equals(event.getGenre())) {
            return false;
        }
        return true;
    }

    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        eventId = Integer.parseInt(values[0]);
        name = values[1];
        description = values[2];
        date = values[3];
        time = values[4];
        duration = Integer.parseInt(values[5]);
        totalTickets = Integer.parseInt(values[6]);
        availableTickets = Integer.parseInt(values[7]);
        Location location = new Location();
        location.fromCSV(values[8] + "," + values[9] + "," + values[10]);
        this.location = location;
        artists = new ArrayList<Artist>();
        String[] artistsCSV = values[11].split(";");
        for (String artistCSV : artistsCSV) {
            Artist artist = new Artist();
            artist.fromCSV(artistCSV);
            artists.add(artist);
        }
        seats = new ArrayList<List<Character>>();
        String[] seatsCSV = values[12].split(";");
        for (String rowCSV : seatsCSV) {
            List<Character> row = new ArrayList<Character>();
            for (int i = 0; i < rowCSV.length(); i++) {
                row.add(rowCSV.charAt(i));
            }
            seats.add(row);
        }
        reviews = new ArrayList<Review>();
        String[] reviewsCSV = values[13].split(";");
        for (String reviewCSV : reviewsCSV) {
            Review review = new Review();
            review.fromCSV(reviewCSV);
            reviews.add(review);
        }
        ticketPrices = new HashMap<String, Double>();
        String[] ticketPricesCSV = values[14].split(";");
        for (String ticketPriceCSV : ticketPricesCSV) {
            String[] ticketPrice = ticketPriceCSV.split(",");
            ticketPrices.put(ticketPrice[0], Double.parseDouble(ticketPrice[1]));
        }
        genre = values[15];
    }

    public void fromInput(Scanner scanner) {
        System.out.print("Enter name: ");
        name = scanner.nextLine();
        System.out.print("Enter description: ");
        description = scanner.nextLine();
        System.out.print("Enter date: ");
        date = scanner.nextLine();
        System.out.print("Enter time: ");
        time = scanner.nextLine();
        System.out.print("Enter duration: ");
        duration = scanner.nextInt();
        System.out.print("Enter total tickets: ");
        totalTickets = scanner.nextInt();
        System.out.print("Enter available tickets: ");
        availableTickets = scanner.nextInt();
        Location location = new Location();
        location.fromInput(scanner);
        this.location = location;
        artists = new ArrayList<Artist>();
        System.out.print("Enter number of artists: ");
        int numberOfArtists = scanner.nextInt();
        for (int i = 0; i < numberOfArtists; i++) {
            Artist artist = new Artist();
            artist.fromInput(scanner);
            artists.add(artist);
        }
        seats = new ArrayList<List<Character>>();
        System.out.print("Enter number of rows: ");
        int numberOfRows = scanner.nextInt();
        for (int i = 0; i < numberOfRows; i++) {
            List<Character> row = new ArrayList<Character>();
            System.out.print("Enter number of seats in row " + (i + 1) + ": ");
            int numberOfSeats = scanner.nextInt();
            for (int j = 0; j < numberOfSeats; j++) {
                System.out.print("Enter seat " + (j + 1) + " in row " + (i + 1) + ": ");
                row.add(scanner.next().charAt(0));
            }
            seats.add(row);
        }
        reviews = new ArrayList<Review>();
        System.out.print("Enter number of reviews: ");
        int numberOfReviews = scanner.nextInt();
        for (int i = 0; i < numberOfReviews; i++) {
            Review review = new Review();
            review.fromInput(scanner);
            reviews.add(review);
        }
        ticketPrices = new HashMap<String, Double>();
        System.out.print("Enter number of ticket prices: ");
        int numberOfTicketPrices = scanner.nextInt();
        for (int i = 0; i < numberOfTicketPrices; i++) {
            System.out.print("Enter type of ticket " + (i + 1) + ": ");
            String type = scanner.nextLine();
            System.out.print("Enter price of ticket " + (i + 1) + ": ");
            double price = scanner.nextDouble();
            ticketPrices.put(type, price);
        }
        System.out.print("Enter genre: ");
        genre = scanner.nextLine();
    }

    public void showSeats() {
        if (seats.size() == 0) {
            System.out.println("Standing seats!");
            return;
        }

        System.out.println("         ");
        for (int i = 0; i < seats.size(); i++)
            System.out.print((char)('A' + i) + " ");

        for (int i = 0; i < seats.size(); i++) {
            System.out.print("Row " + (i + 1) + ": ");
            for (int j = 0; j < seats.get(i).size(); j++) {
                System.out.print(seats.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public void showArtists() {
        for (Artist artist : artists) {
            System.out.println(artist);
        }
    }

    public void showLocation() {
        System.out.println(location);
    }

    public void showEvent() {
        System.out.println(this);
        showArtists();
        showLocation();
        showSeats();
    }

    public void showReviews() {
        for (Review review : reviews) {
            System.out.println(review);
        }
    }

    public void showEventWithoutSeats() {
        System.out.println("model.Event ID: " + eventId + ", Name: " + name + ", Description: " + description + ", Date: " + date + ", Time: " + time + ", Duration: " + duration + ", Total Tickets: " + totalTickets + ", Available Tickets: " + availableTickets + ", model.Location: " + location + ", Artists: " + artists);
    }

    public Boolean isAvailable() {
        return availableTickets > 0;
    }

    //public abstract double calculatePrice(String seat);

    public void markSeat(String seat) {
        int row = seat.charAt(0) - 'A';
        int column = Integer.parseInt(seat.substring(1)) - 1;
        seats.get(row).set(column, 'X');
        availableTickets--;
    }

    public void unmarkSeat(String seat) {
        int row = seat.charAt(0) - 'A';
        int column = Integer.parseInt(seat.substring(1)) - 1;
        seats.get(row).set(column, 'O');
        availableTickets++;
    }

    public void addReview(Review review) { reviews.add(review); }

    public void removeReview(Review review) { reviews.remove(review); }

    public double calculateRating() {
        double rating = 0.0;
        for (Review review : reviews)
            rating += review.getRating();
        return rating / reviews.size();
    }

    public int compareTo(Event event) {
        double ratingComparison = calculateRating() - event.calculateRating();
        if (ratingComparison < 0)
            return -1;
        if (ratingComparison > 0)
            return 1;
        return reviews.size() - event.getReviews().size();
    }

    public void updateTickets(String type, double price) {
        ticketPrices.put(type, price);
    }
}
