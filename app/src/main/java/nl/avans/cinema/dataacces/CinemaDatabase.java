package nl.avans.cinema.dataacces;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import nl.avans.cinema.dataacces.dao.MovieDAO;
import nl.avans.cinema.domain.Movie;

@Database(entities = Movie.class, version = 2, exportSchema = false)
public abstract class CinemaDatabase extends RoomDatabase {

    private static CinemaDatabase INSTANCE;

    public static CinemaDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (CinemaDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CinemaDatabase.class, "cinema_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        new PopulateDbAsync(INSTANCE).execute();
        return INSTANCE;
    }
    public abstract MovieDAO movieDAO();


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final MovieDAO mDao;

        public PopulateDbAsync(CinemaDatabase instance) {
            mDao = instance.movieDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO Vul database
            Movie movie = new Movie();
            movie.setAdult(true);
            movie.setAmountOfSeasons(2);
            movie.setAlternativeTitle("Cool");
            movie.setId(1);
            movie.setTitle("Test");
            mDao.insertMovie(movie);
            return null;
        }
    }
}
