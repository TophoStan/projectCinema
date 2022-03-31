package nl.avans.cinema.dataacces;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.dataacces.converters.GithubTypeConverter;
import nl.avans.cinema.dataacces.dao.MovieDAO;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;

@Database(entities = Movie.class, version = 7, exportSchema = false)
@TypeConverters({GithubTypeConverter.class})
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

            return null;
        }
    }
}
