package nl.avans.cinema.dataacces;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import nl.avans.cinema.dataacces.converters.Converter;
import nl.avans.cinema.dataacces.dao.CinemaDAO;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.User;

@Database(entities = {Movie.class, User.class}, version = 27, exportSchema = false)
@TypeConverters({Converter.class})
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
    public abstract CinemaDAO movieDAO();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final CinemaDAO mDao;

        public PopulateDbAsync(CinemaDatabase instance) {
            mDao = instance.movieDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
                mDao.deleteAll();
            return null;
        }
    }
}
