package database;

import repository.*;

public class DatabaseManagerUtil {

    public static void initDatabase() {
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
