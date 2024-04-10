package service;

import application.App;
import model.*;
import user.User;

import java.util.List;
import java.util.Scanner;

public interface IUserService {
    void showMenu();
    void executeAction(Scanner scanner);

    default RegistrationService getRegistrationService() { return App.getInstance().getRegistrationService(); }
    default void logOut() { App.getInstance().logOut(); }


    default List<Event> getFutureEvents() { return App.getInstance().getFutureEvents(); }
    default List<Event> getPastEvents() { return App.getInstance().getPastEvents(); }

    default void setFutureEvents(List<Event> futureEvents) { App.getInstance().setFutureEvents(futureEvents); }
    default void setPastEvents(List<Event> pastEvents) { App.getInstance().setPastEvents(pastEvents); }

    default List<Ticket> getTickets() { return App.getInstance().getTickets(); }
    default void setTickets(List<Ticket> tickets) { App.getInstance().setTickets(tickets); }
    default void addTicket(Ticket ticket) { App.getInstance().addTicket(ticket); }

    default void moveEventsToPast() { App.getInstance().moveEventsToPast(); }

    default Event updateEvent(Event event, Scanner scanner) {
        System.out.println("Specify what do you want to update: ");
        System.out.println("1. Name");
        System.out.println("2. Description");
        System.out.println("3. Date");
        System.out.println("4. Time");
        System.out.println("5. Duration");
        System.out.println("6. model.Location");
        System.out.println("7. model.Ticket types and prices");
        System.out.println("9. Genre");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter new name: ");
                event.setName(scanner.nextLine());
                break;
            case 2:
                System.out.println("Enter new description: ");
                event.setDescription(scanner.nextLine());
                break;
            case 3:
                System.out.println("Enter new date: ");
                event.setDate(scanner.nextLine());
                break;
            case 4:
                System.out.println("Enter new time: ");
                event.setTime(scanner.nextLine());
                break;
            case 5:
                System.out.println("Enter new duration: ");
                event.setDuration(Integer.parseInt(scanner.nextLine()));
                break;
            case 6:
                System.out.println("Enter new location: ");
                Location newLocation = new Location();
                newLocation.fromInput(scanner);
                event.setLocation(newLocation);
                break;
            case 7:
                System.out.println("Do you want to update existing ticket prices or add new ones? (update/add)");
                String response = scanner.nextLine();
                if (response.equals("update")) {
                    System.out.println("Enter the type of the ticket you want to update: ");
                    String type = scanner.nextLine();
                    System.out.println("Enter new price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    event.updateTickets(type, price);
                }
                else if (response.equals("add")) {
                    System.out.println("Enter the number of new ticket types: ");
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < n; i++) {
                        System.out.println("Enter ticket type: ");
                        String type = scanner.nextLine();
                        System.out.println("Enter ticket price: ");
                        String price = scanner.nextLine();
                        event.updateTickets(type, Double.parseDouble(price));
                    }
                }
                else
                    System.out.println("Invalid choice");
                break;
            case 8:
                System.out.println("Enter new genre: ");
                event.setGenre(scanner.nextLine());
                break;
            default:
                System.out.println("Invalid choice");
        }
        return event;
    }

    default void addEvent(Scanner scanner, int eventType) {
        Event event = null;
        if (eventType == 1) {
            event = new Concert();
            event.fromInput(scanner);
        }
        else if (eventType == 2) {
            event = new FilmScreening();
            event.fromInput(scanner);
        }
        else if (eventType == 3) {
            event = new TheatrePlay();
            event.fromInput(scanner);
        }
        else
            System.out.println("Invalid event type");
        if (event != null) {
            getFutureEvents().add(event);
            System.out.println("model.Event added successfully!");
        }
    }


    default void updateTicket(int id) {
        List<Ticket> tickets = getTickets();
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == id) {
                ticket = updateTicket(ticket, new Scanner(System.in));
                break;
            }
        }
        setTickets(tickets);
    }

    default Ticket updateTicket(Ticket ticket, Scanner scanner) {
        System.out.println("Specify what do you want to update: ");
        System.out.println("1. model.Ticket type");
        System.out.println("2. Seat");
        System.out.println("3. Both");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter new ticket type: ");
                ticket.setType(scanner.nextLine());
                break;
            case 2:
                System.out.println("Enter new seat: ");
                ticket.setSeat(scanner.nextLine());
                break;
            case 3:
                System.out.println("Enter new ticket type: ");
                ticket.setType(scanner.nextLine());
                System.out.println("Enter new seat: ");
                ticket.setSeat(scanner.nextLine());
                break;
            default:
                System.out.println("Invalid choice");
        }
        return ticket;
    }

    default void manageProfile(Scanner scanner, User user) {
        System.out.println("1. Show profile");
        System.out.println("2. Change profile details");
        System.out.println("3. Back");
        System.out.print("Enter option: ");
        int option = scanner.nextInt();
        if (option == 1) {
            System.out.println(user);
        } else if (option == 2) {
            System.out.println("1. Change username");
            System.out.println("2. Change password");
            System.out.println("3. Change name");
            System.out.println("4. Change age");
            System.out.println("5. Change location");
            System.out.print("Enter option: ");
            int option2 = scanner.nextInt();
            scanner.nextLine();
            switch (option2) {
                case 1:
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    user.setUsername(newUsername);
                    break;
                case 2:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    user.setPassword(newPassword);
                    break;
                case 3:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    user.setName(newName);
                    break;
                case 4:
                    System.out.print("Enter new age: ");
                    int newAge = scanner.nextInt();
                    user.setAge(newAge);
                    break;
                case 5:
                    System.out.print("Enter new location: ");
                    Location newLocation = new Location();
                    newLocation.fromInput(scanner);
                    user.setLocation(newLocation);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        else if (option == 3)
            return;
        else
            System.out.println("Invalid option. Try again.");
    }

}
