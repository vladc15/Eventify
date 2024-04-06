import java.util.Scanner;

public class Artist extends User {
    private String bio;
    private String genre;
    private double rating; // average rating from all events where the artist was involved

    public Artist() {
        super();
        this.bio = "";
        this.genre = "";
        this.rating = 0.0;
    }

    public Artist(int userId, String username, String password, String email, String phone, String name, int age, Location location, String bio, String genre, double rating) {
        super(userId, username, password, email, phone, name, age, location);
        this.bio = bio;
        this.genre = genre;
        this.rating = rating;
    }

    public Artist(Artist artist) {
        super(artist);
        this.bio = artist.getBio();
        this.genre = artist.getGenre();
        this.rating = artist.getRating();
    }

    public String getBio() { return bio; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }

    public void setBio(String bio) { this.bio = bio; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setRating(double rating) { this.rating = rating; }

    public String toString() {
        return super.toString() + ", Bio: " + bio + ", Genre: " + genre + ", Rating: " + rating;
    }
    public String toCSV() {
        return super.toCSV() + "," + bio + "," + genre + "," + rating;
    }

    public boolean equals(Artist artist) {
        return super.equals(artist) && bio.equals(artist.getBio()) && genre.equals(artist.getGenre()) && rating == artist.getRating();
    }

    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        super.fromCSV(values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4] + "," + values[5] + "," + values[6] + "," + values[7] + "," + values[8]);
        this.bio = values[9];
        this.genre = values[10];
        this.rating = Double.parseDouble(values[11]);
    }

    public void fromInput(Scanner scanner) {
        super.fromInput(scanner);
        System.out.print("Enter bio: ");
        this.bio = scanner.nextLine();
        System.out.print("Enter genre: ");
        this.genre = scanner.nextLine();
        System.out.print("Enter rating: ");
        this.rating = scanner.nextDouble();
    }

    

}
