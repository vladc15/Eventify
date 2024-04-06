import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public abstract class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String email;
    protected String phone;
    protected String name;
    //protected String role; // customer, artist, admin
    protected int age;
    protected Location location;  // find nearest events
    // eventual puse doar pentru customer
    protected List<Review> reviews;

    public User() {
        this.userId = 0;
        this.username = "";
        this.password = "";
        this.email = "";
        this.phone = "";
        this.name = "";
        this.age = 0;
        this.location = new Location();
        this.reviews = new ArrayList<Review>();
    }

    public User(int userId, String username, String password, String email, String phone, String name, int age, Location location) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.age = age;
        //this.location = location;
        this.location = new Location(location);
        this.reviews = new ArrayList<Review>();
    }

    public User(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.name = user.getName();
        this.age = user.getAge();
        //this.location = user.getLocation();
        this.location = new Location(user.getLocation());
        this.reviews = new ArrayList<Review>(user.getReviews());
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public Location getLocation() { return location; }
    public List<Review> getReviews() { return reviews; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setLocation(Location location) { this.location = new Location(location); }
    public void setReviews(List<Review> reviews) { this.reviews = new ArrayList<Review>(reviews); }

    public String toString() { return "Username: " + username + ", Email: " + email + ", Phone: " + phone + ", Name: " + name + ", Age: " + age + ", Location: " + location; }
    public String toCSV() { return userId + "," + username + "," + password + "," + email + "," + phone + "," + name + "," + age + "," + location.toCSV(); }
    
    public boolean equals(User user) { return userId == user.getUserId() && username.equals(user.getUsername()) && password.equals(user.getPassword()) && email.equals(user.getEmail()) && phone.equals(user.getPhone()) && name.equals(user.getName()) && age == user.getAge() && location.equals(user.getLocation()); }
    
    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        //return new User(Integer.parseInt(values[0]), values[1], values[2], values[3], values[4], values[5], values[6], Integer.parseInt(values[7]), new Location(values[8], values[9], Integer.parseInt(values[10]));
        this.userId = Integer.parseInt(values[0]); this.username = values[1]; this.password = values[2]; this.email = values[3]; this.phone = values[4]; this.name = values[5]; this.age = Integer.parseInt(values[6]); this.location = new Location(Integer.parseInt(values[7]), values[8], values[9], Integer.parseInt(values[10]), Integer.parseInt(values[11]), Integer.parseInt(values[12]));
    }
    public void fromInput(Scanner scanner) {
        System.out.print("Enter username: ");
        //String username = scanner.nextLine();
        this.username = scanner.nextLine();
        System.out.print("Enter password: ");
        //String password = scanner.nextLine();
        this.password = scanner.nextLine();
        System.out.print("Enter email: ");
        //String email = scanner.nextLine();
        this.email = scanner.nextLine();
        System.out.print("Enter phone: ");
        //String phone = scanner.nextLine();
        this.phone = scanner.nextLine();
        System.out.print("Enter name: ");
        //String name = scanner.nextLine();
        this.name = scanner.nextLine();
        System.out.print("Enter age: ");
        //int age = scanner.nextInt();
        this.age = scanner.nextInt();
        System.out.print("Enter location: ");
        //Location location = new Location();
        //location.fromInput(scanner);
        this.location = new Location();
        this.location.fromInput(scanner);
        //return new User(username, password, email, phone, name, age, location);
    }
}
