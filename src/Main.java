import application.App;
import database.DatabaseManagerUtil;
import model.*;
import service.*;
import user.Admin;
import user.Artist;
import user.Customer;
import user.User;

import javax.tools.ForwardingJavaFileManager;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App app = App.getInstance();
        RegistrationService registrationService = app.getRegistrationService();
        DatabaseManagerUtil.initDatabase();

        initApp(app); // add some initial data

        while (true) {
            System.out.println("Are you already a member? (y/n)");
            String answer = scanner.nextLine();

            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Invalid input!");
                continue;
            }

            if (answer.equals("n")) {
                System.out.println("-------Register-------");
                System.out.println("Choose the type of account:");
                System.out.println("1. User");
                System.out.println("2. Artist");
                System.out.println("3. Admin");
                System.out.println("Enter your choice:");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1)
                    registrationService.signUpCustomer(scanner);
                else if (choice == 2)
                    registrationService.signUpArtist(scanner);
                else if (choice == 3)
                    registrationService.signUpAdmin(scanner);
                else
                    System.out.println("Invalid input!");
            }

            System.out.println("-------Login-------");
            int accountType = registrationService.logIn(scanner);
            User currentUser = registrationService.getCurrentUser();

            if (accountType == -1) {
                System.out.println("Invalid username or password!");
                continue;
            }

            if (accountType == 1) {
                Customer currentCustomer = (Customer) currentUser;
                AuditService.getInstance().logAction("Customer login");
                System.out.println("Welcome into the customer account, " + currentUser.getName() + "!");
                CustomerService customerService = CustomerService.getInstance(currentCustomer);
                customerService.executeAction(scanner);
                break;
            } else if (accountType == 2) {
                Artist currentArtist = (Artist) currentUser;
                AuditService.getInstance().logAction("Artist login");
                System.out.println("Welcome into the artist account, " + currentUser.getName() + "!");
                ArtistService artistService = ArtistService.getInstance(currentArtist);
                artistService.executeAction(scanner);
                break;
            } else {
                Admin currentAdmin = (Admin) currentUser;
                AuditService.getInstance().logAction("Admin login");
                System.out.println("Welcome into the admin account, " + currentUser.getName() + "!");
                AdminService adminService = AdminService.getInstance(currentAdmin);
                adminService.executeAction(scanner);
                break;
            }
        }
        AuditService.getInstance().close();
    }

    private static void initApp(App app) {
        // add some future events, past events and some tickets
        Location location1 = new Location(1, "Address1", "City1", 1000, 10, 10);
        Location location2 = new Location(2, "Address2", "City2", 2000, 20, 20);
        Location location3 = new Location(3, "Address3", "City3", 3000, 30, 30);

        Map<String, Double> ticketPrices1 = new HashMap<>();
        ticketPrices1.put("VIP", 100.0);
        ticketPrices1.put("Normal", 50.0);

        Map<String, Double> ticketPrices2 = new HashMap<>();
        ticketPrices2.put("Normal", 20.0);

        Concert concert1 = new Concert(1, "Concert1", "Description1", "01/06/2024", "20:00", 120, 100, 100, location1, new ArrayList<>(), ticketPrices1, "Jazz", false, false, false);
        Concert concert2 = new Concert(2, "Concert2", "Description2", "02/06/2023", "21:00", 120, 100, 100, location2, new ArrayList<>(), ticketPrices2, "Rock", true, true, true);
        Concert concert3 = new Concert(3, "Concert3", "Description3", "03/06/2024", "23:30", 120, 100, 100, location3, new ArrayList<>(), ticketPrices1, "Techno", false, false, false);
        FilmScreening filmScreening1 = new FilmScreening(2, "FilmScreening1", "Description2", "02/06/2022", "21:00", 120, 100, 100, location2, new ArrayList<>(), ticketPrices2, "Adventure", "3D", false, 2021, false, 12, false);
        FilmScreening filmScreening2 = new FilmScreening(3, "FilmScreening2", "Description3", "03/06/2024", "23:30", 120, 100, 100, location3, new ArrayList<>(), ticketPrices1, "Documentary", "2D", true, 2024, true, 16, true);
        TheatrePlay theatrePlay1 = new TheatrePlay(1, "TheatrePlay1", "Description1", "01/06/2024", "20:00", 120, 100, 100, location1, new ArrayList<>(), ticketPrices1, "Drama", false, false);

        ConcertTicket concertTicket1 = new ConcertTicket(1, concert1, "VIP", null, 0.0, 0.0);
        ConcertTicket concertTicket2 = new ConcertTicket(2, concert1, "Normal", null, 0.0, 50.0);
        ConcertTicket concertTicket3 = new ConcertTicket(3, concert2, "Normal", null, 50.0, 50.0);
        FilmScreeningTicket filmScreeningTicket1 = new FilmScreeningTicket(5, filmScreening1, "Normal", "A3", 20.0, 10.0);
        FilmScreeningTicket filmScreeningTicket2 = new FilmScreeningTicket(6, filmScreening2, "Normal", "B2", 20.5, 15.0);
        TheatrePlayTicket theatrePlayTicket1 = new TheatrePlayTicket(7, theatrePlay1, "Normal", "C7", 30.0);

        app.addFutureEvent(concert1);
        app.addFutureEvent(concert2);
        app.addFutureEvent(concert3);
        app.addFutureEvent(filmScreening1);
        app.addFutureEvent(filmScreening2);
        app.addFutureEvent(theatrePlay1);

        app.addTicket(concertTicket1);
        app.addTicket(concertTicket2);
        app.addTicket(concertTicket3);
        app.addTicket(filmScreeningTicket1);
        app.addTicket(filmScreeningTicket2);
        app.addTicket(theatrePlayTicket1);

    }
}