package model;

import java.util.Scanner;

public class ConcertTicket extends Ticket {
    private double afterPartyPrice;
    private double meetAndGreetPrice;

    public ConcertTicket() {
        super();
        this.afterPartyPrice = 0.0;
        this.meetAndGreetPrice = 0.0;
    }

    public ConcertTicket(int ticketId, Event event, String type, String seat, double afterPartyPrice, double meetAndGreetPrice) {
        super(ticketId, event, type, seat);
        this.afterPartyPrice = afterPartyPrice;
        this.meetAndGreetPrice = meetAndGreetPrice;
    }

    public ConcertTicket(ConcertTicket concertTicket) {
        super(concertTicket);
        this.afterPartyPrice = concertTicket.getAfterPartyPrice();
        this.meetAndGreetPrice = concertTicket.getMeetAndGreetPrice();
    }

    public double getAfterPartyPrice() { return afterPartyPrice; }
    public double getMeetAndGreetPrice() { return meetAndGreetPrice; }

    public void setAfterPartyPrice(double afterPartyPrice) { this.afterPartyPrice = afterPartyPrice; }
    public void setMeetAndGreetPrice(double meetAndGreetPrice) { this.meetAndGreetPrice = meetAndGreetPrice; }

    public String toString() { return super.toString() + ", After Party Price: " + afterPartyPrice + ", Meet and Greet Price: " + meetAndGreetPrice; }
    public String toCSV() { return super.toCSV() + "," + afterPartyPrice + "," + meetAndGreetPrice; }

    public boolean equals(ConcertTicket concertTicket) { return super.equals(concertTicket) && afterPartyPrice == concertTicket.getAfterPartyPrice() && meetAndGreetPrice == concertTicket.getMeetAndGreetPrice(); }

    public void fromCSV(String csv) {
        super.fromCSV(csv);
        String[] values = csv.split(",");
        this.afterPartyPrice = Double.parseDouble(values[values.length - 2]);
        this.meetAndGreetPrice = Double.parseDouble(values[values.length - 1]);
    }

    public void fromInput(Scanner scanner) {
        super.fromInput(scanner);
        System.out.print("Enter after party price: ");
        this.afterPartyPrice = scanner.nextDouble();
        System.out.print("Enter meet and greet price: ");
        this.meetAndGreetPrice = scanner.nextDouble();
    }

    public void fromInputEvent(Scanner scanner, Event event) {
        super.fromInputEvent(scanner, event);
        System.out.print("Enter after party price: ");
        this.afterPartyPrice = scanner.nextDouble();
        System.out.print("Enter meet and greet price: ");
        this.meetAndGreetPrice = scanner.nextDouble();
    }

    public int compareTo(ConcertTicket concertTicket) {
        return super.compareTo(concertTicket);
    }

    public double calculatePrice() {
        Concert event = (Concert) super.getEvent();
        return super.getPrice() + afterPartyPrice * (event.getAfterParty() ? 1 : 0) + meetAndGreetPrice * (event.getMeetAndGreet() ? 1 : 0);
    }

}