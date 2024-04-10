package model;

import java.util.Scanner;

public class TheatrePlayTicket extends Ticket {
    private double qaPrice;

    public TheatrePlayTicket() {
        super();
        this.qaPrice = 0.0;
    }

    public TheatrePlayTicket(int ticketId, Event event, String type, String seat, double qaPrice) {
        super(ticketId, event, type, seat);
        this.qaPrice = qaPrice;
    }

    public TheatrePlayTicket(TheatrePlayTicket theatrePlayTicket) {
        super(theatrePlayTicket);
        this.qaPrice = theatrePlayTicket.getQaPrice();
    }

    public double getQaPrice() { return qaPrice; }

    public void setQaPrice(double qaPrice) { this.qaPrice = qaPrice; }

    public String toString() { return super.toString() + ", QA Price: " + qaPrice; }
    public String toCSV() { return super.toCSV() + "," + qaPrice; }

    public boolean equals(TheatrePlayTicket theatrePlayTicket) { return super.equals(theatrePlayTicket) && qaPrice == theatrePlayTicket.getQaPrice(); }

    public void fromCSV(String csv) {
        super.fromCSV(csv);
        String[] values = csv.split(",");
        this.qaPrice = Double.parseDouble(values[values.length - 1]);
    }

    public void fromInput(Scanner scanner) {
        super.fromInput(scanner);
        System.out.print("Enter QA price: ");
        this.qaPrice = scanner.nextDouble();
    }

    public void fromInputEvent(Scanner scanner, Event event) {
        super.fromInputEvent(scanner, event);
        System.out.print("Enter QA price: ");
        this.qaPrice = scanner.nextDouble();
    }

    public int compareTo(TheatrePlayTicket theatrePlayTicket) {
        return super.compareTo(theatrePlayTicket);
    }

    public double calculatePrice() {
        TheatrePlay event = (TheatrePlay) super.getEvent();
        return super.getPrice() + qaPrice * (event.getQA() ? 1 : 0);
    }
}