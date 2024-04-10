package model;

import java.util.Scanner;

public abstract class Ticket implements Comparable<Ticket> {
    protected int ticketId;
    //private int eventId;
    protected Event event;
    protected String type;
    //protected double price;
    //protected Map<String, Double> ticketPrices; // in event
    protected String seat;

    public Ticket() {
        this.ticketId = 0;
        this.event = null;
        this.type = "";
        this.seat = "";
    }

    public Ticket(int ticketId, Event event, String type, String seat) {
        this.ticketId = ticketId;
        this.event = event;
        this.type = type;
        this.seat = seat;
    }

    public Ticket(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.event = ticket.getEvent();
        this.type = ticket.getType();
        this.seat = ticket.getSeat();
    }

    public int getTicketId() { return ticketId; }
    public Event getEvent() { return event; }
    public String getType() { return type; }
    public String getSeat() { return seat; }

    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    public void setEvent(Event event) { this.event = event; }
    public void setType(String type) { this.type = type; }
    public void setSeat(String seat) { this.seat = seat; }

    public String toString() { return "model.Ticket ID: " + ticketId + ", model.Event: " + event.toString() + ", Type: " + type + ", Seat: " + seat;}
    public String toCSV() { return ticketId + "," + event.toCSV() + "," + type + "," + seat;}

    public boolean equals(Ticket ticket) { return ticketId == ticket.getTicketId() && event.equals(ticket.getEvent()) && type.equals(ticket.getType()) && seat.equals(ticket.getSeat());}

    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        this.ticketId = Integer.parseInt(values[0]);
        this.type = values[1];
        this.seat = values[2];
        this.event.fromCSV(csv.substring(csv.indexOf(",") + 1));
        //this.type = values[values.length - 1];
    }
    
    public void fromInput(Scanner scanner) {
        System.out.print("Enter ticket ID: ");
        this.ticketId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter event: ");
        this.event.fromInput(scanner);
        System.out.print("Enter type: ");
        this.type = scanner.nextLine();
        System.out.print("Enter seat: ");
        this.seat = scanner.nextLine();
    }

    public void fromInputEvent(Scanner scanner, Event event) {
        System.out.print("Enter ticket ID: ");
        this.ticketId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter type: ");
        this.type = scanner.nextLine();
        System.out.print("Enter seat: ");
        this.seat = scanner.nextLine();
    }
    
    public double getPrice() {
        return event.getTicketPrices().get(type);
    }

    public int compareTo(Ticket ticket) {
        int dateComparison = event.getDate().compareTo(ticket.getEvent().getDate());
        if (dateComparison == 0) {
            return event.getTime().compareTo(ticket.getEvent().getTime());
        }
        return dateComparison;
    }
}
