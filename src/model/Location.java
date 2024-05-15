package model;

import java.util.Scanner;

public class Location {
    private int locationId;
    private String address;
    private String city;
    private int totalCapacity;

    private int rows;
    private int columns;

    public Location() {
        this.locationId = 0;
        this.address = "";
        this.city = "";
        this.totalCapacity = 0;
        this.rows = 0;
        this.columns = 0;
    }

    public Location(int locationId, String address, String city, int totalCapacity, int rows, int columns) {
        this.locationId = locationId;
        this.address = address;
        this.city = city;
        this.totalCapacity = totalCapacity;
        this.rows = rows;
        this.columns = columns;
    }

    public Location(Location location) {
        this.locationId = location.getLocationId();
        this.address = location.getAddress();
        this.city = location.getCity();
        this.totalCapacity = location.getTotalCapacity();
        this.rows = location.getRows();
        this.columns = location.getColumns();
    }

    public int getLocationId() { return locationId; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public int getTotalCapacity() { return totalCapacity; }
    public int getRows() { return rows; }
    public int getColumns() { return columns; }

    public void setLocationId(int locationId) { this.locationId = locationId; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setTotalCapacity(int totalCapacity) { this.totalCapacity = totalCapacity; }
    public void setRows(int rows) { this.rows = rows; }
    public void setColumns(int columns) { this.columns = columns; }

    public String toString() { return "LocationId: " + locationId + "Address: " + address + ", City: " + city + ", Total Capacity: " + totalCapacity + ", Rows: " + rows + ", Columns: " + columns; }
    public String toCSV() { return locationId + "," + address + "," + city + "," + totalCapacity + "," + rows + "," + columns; }

    public boolean equals(Location location) { return address.equals(location.getAddress()) && city.equals(location.getCity()) && totalCapacity == location.getTotalCapacity() && rows == location.getRows() && columns == location.getColumns(); }


    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        //return new model.Location(values[0], values[1], Integer.parseInt(values[2]));
        this.locationId = Integer.parseInt(values[0]);
        this.address = values[1];
        this.city = values[2];
        this.totalCapacity = Integer.parseInt(values[3]);
        this.rows = Integer.parseInt(values[4]);
        this.columns = Integer.parseInt(values[5]);
    }
    public void fromInput(Scanner scanner) {
        System.out.print("Enter address: ");
        //String address = scanner.nextLine();
        this.address = scanner.next();
        System.out.print("Enter city: ");
        //String city = scanner.nextLine();
        this.city = scanner.next();
        System.out.print("Enter total capacity: ");
        //int totalCapacity = scanner.nextInt();
        //return new model.Location(address, city, totalCapacity);
        this.totalCapacity = scanner.nextInt();
        System.out.print("Enter rows: ");
        //int rows = scanner.nextInt();
        this.rows = scanner.nextInt();
        System.out.print("Enter columns: ");
        //int columns = scanner.nextInt();
        this.columns = scanner.nextInt();
    }

    public void fromInputUser(Scanner scanner) {
        System.out.print("Enter address: ");
        //String address = scanner.nextLine();
        this.address = scanner.next();
        System.out.print("Enter city: ");
        //String city = scanner.nextLine();
        this.city = scanner.next();
    }

}
