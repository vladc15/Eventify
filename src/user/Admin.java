package user;

import model.Location;

// singleton, only one admin
public class Admin extends User {
    private static Admin instance = null;

    private Admin() { super(); }

    private Admin(int userId, String username, String password) {
        super(userId, username, password);
    }

    private Admin(int userId, String username, String password, String name, int age, Location location) {
        super(userId, username, password, name, age, location);
    }

    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin(0, "admin", "adminpa55", "user.Admin", 0, new Location());
        }
        return instance;
    }

    public static Admin getInstance(int userId, String username, String password) {
        if (instance == null) {
            instance = new Admin(userId, username, password);
        }
        return instance;
    }

}
