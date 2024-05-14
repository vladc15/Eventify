package database;

public class DatabaseManager {
    private static DatabaseManager instance;

    private DatabaseManager() { initDatabase(); }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initDatabase() {
        // init din toate repository-urile
    }


}
