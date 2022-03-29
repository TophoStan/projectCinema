package nl.avans.cinema.dataacces;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import nl.avans.cinema.domain.Content;

public class CinemaRepository {
    private ContentDao mContentDao;
    private LiveData<List<Content>> mAllContentItems;

    public CinemaRepository(Application application) {
        // get database using the Room annotations
        CinemaDatabase db = CinemaDatabase.getDatabase(application);

        // get a Dao from de databases to be able to get words
        mContentDao = db.contentDao();

        //get all words from the Dao
        mAllContentItems = mContentDao.getAllContent();
    }

    public LiveData<List<Content>> getAllContentItems() {
        return mAllContentItems;
    }

    public void deleteAll() {
        new deleteAllContentItemsAsyncTask(mContentDao).execute();
    }

    private static class deleteAllContentItemsAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContentDao mContentDaoAsyncTask;

        public deleteAllContentItemsAsyncTask(ContentDao contentDao) {
            mContentDaoAsyncTask = contentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mContentDaoAsyncTask.deleteAll();
            return null;
        }
    }

    public void insert(Content content) {
        new insertContentAsyncTask(mContentDao).execute(content);
    }

    private static class insertContentAsyncTask extends AsyncTask<Content, Void, Void> {

        private ContentDao myAsyncTaskDao;

        public insertContentAsyncTask(ContentDao contentDao) {
            myAsyncTaskDao = contentDao;
        }

        @Override
        protected Void doInBackground(Content... contents) {
            myAsyncTaskDao.insertContent(contents[0]);
            return null;
        }
    }

    //TODO voeg meerdere CRUD functies toe


}
