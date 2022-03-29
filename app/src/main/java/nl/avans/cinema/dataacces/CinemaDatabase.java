package nl.avans.cinema.dataacces;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ContentDao.class, version = 2, exportSchema = false)
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
    public abstract ContentDao contentDao();


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final ContentDao mDao;

        public PopulateDbAsync(CinemaDatabase instance) {
            mDao = instance.contentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO Vul database
            mDao.deleteAll();
            return null;
        }
    }
}
