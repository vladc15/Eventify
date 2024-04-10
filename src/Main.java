import application.App;
import model.ConcertTicket;
import service.AdminService;
import service.ArtistService;
import service.CustomerService;
import service.RegistrationService;
import user.Admin;
import user.Artist;
import user.Customer;
import user.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App app = App.getInstance();
        RegistrationService registrationService = app.getRegistrationService();

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
                System.out.println("Welcome into the customer account, " + currentUser.getName() + "!");
                CustomerService customerService = CustomerService.getInstance(currentCustomer);
                customerService.executeAction(scanner);
                break;
            } else if (accountType == 2) {
                Artist currentArtist = (Artist) currentUser;
                System.out.println("Welcome into the artist account, " + currentUser.getName() + "!");
                ArtistService artistService = ArtistService.getInstance(currentArtist);
                artistService.executeAction(scanner);
                break;
            } else {
                Admin currentAdmin = (Admin) currentUser;
                System.out.println("Welcome into the admin account, " + currentUser.getName() + "!");
                AdminService adminService = AdminService.getInstance(currentAdmin);
                adminService.executeAction(scanner);
                break;
            }
        }
    }
}