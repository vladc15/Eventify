package model;

import java.util.Scanner;

public class FilmScreeningTicket extends Ticket {
    private double qaPrice;
    private double imaxPrice;

    public FilmScreeningTicket() {
        super();
        this.qaPrice = 0.0;
        this.imaxPrice = 0.0;
    }
    
    public FilmScreeningTicket(int ticketId, Event event, String type, String seat, double qaPrice, double imaxPrice) {
        super(ticketId, event, type, seat);
        this.qaPrice = qaPrice;
        this.imaxPrice = imaxPrice;
    }
    
    public FilmScreeningTicket(FilmScreeningTicket filmScreeningTicket) {
        super(filmScreeningTicket);
        this.qaPrice = filmScreeningTicket.getQAPrice();
        this.imaxPrice = filmScreeningTicket.getImaxPrice();
    }
    
    public double getQAPrice() { return qaPrice; }
    public double getImaxPrice() { return imaxPrice; }
    
    public void setQAPrice(double qaPrice) { this.qaPrice = qaPrice; }
    public void setImaxPrice(double imaxPrice) { this.imaxPrice = imaxPrice; }
    
    public String toString() { return super.toString() + ", QA Price: " + qaPrice + ", IMAX Price: " + imaxPrice; }
    public String toCSV() { return super.toCSV() + "," + qaPrice + "," + imaxPrice; }
    
    public boolean equals(FilmScreeningTicket filmScreeningTicket) { return super.equals(filmScreeningTicket) && qaPrice == filmScreeningTicket.getQAPrice() && imaxPrice == filmScreeningTicket.getImaxPrice(); }
    
    public void fromCSV(String csv) {
        super.fromCSV(csv);
        String[] values = csv.split(",");
        this.qaPrice = Double.parseDouble(values[values.length - 2]);
        this.imaxPrice = Double.parseDouble(values[values.length - 1]);
    }
    
    public void fromInput(Scanner scanner) {
        super.fromInput(scanner);
        System.out.print("Enter QA price: ");
        this.qaPrice = scanner.nextDouble();
        System.out.print("Enter IMAX price: ");
        this.imaxPrice = scanner.nextDouble();
    }

    public void fromInputEvent(Scanner scanner, Event event) {
        super.fromInputEvent(scanner, event);
        System.out.print("Enter QA price: ");
        this.qaPrice = scanner.nextDouble();
        System.out.print("Enter IMAX price: ");
        this.imaxPrice = scanner.nextDouble();
    }
    
    public int compareTo(FilmScreeningTicket filmScreeningTicket) {
        return super.compareTo(filmScreeningTicket);
    }
    
    public double calculatePrice() {
        FilmScreening event = (FilmScreening) super.getEvent();
        return super.getPrice() + qaPrice * (event.getQA() ? 1 : 0) + imaxPrice * (event.getImax() ? 1 : 0);
    }
}
