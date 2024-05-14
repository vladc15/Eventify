package database;

import model.ConcertTicket;
import repository.*;

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
        LocationRepository.createTable();

        EventRepository.createTable();
        UserRepository.createTable();

        ConcertRepository.createTable();
        FilmScreeningRepository.createTable();
        TheatrePlayRepository.createTable();

        CustomerRepository.createTable();
        ArtistRepository.createTable();
        AdminRepository.createTable();

        ReviewRepository.createTable();

        TicketRepository.createTable();

        ConcertTicketRepository.createTable();
        FilmScreeningTicketRepository.createTable();
        TheatrePlayTicketRepository.createTable();

        EventArtistRepository.createTable();
        CustomerFollowedArtistsRepository.createTable();
        CustomerFavoritesRepository.createTable();
        CustomerHistoryRepository.createTable();
        CustomerTicketsRepository.createTable();
        MapEventRepository.createTable();
    }


}
