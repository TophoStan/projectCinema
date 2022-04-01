package nl.avans.cinema.dataacces;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.calls.MovieResponse;
import nl.avans.cinema.dataacces.dao.MovieDAO;
import nl.avans.cinema.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CinemaRepository {
    private MovieDAO mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

    public CinemaRepository(Application application) {
        // get database using the Room annotations
        CinemaDatabase db = CinemaDatabase.getDatabase(application);

        // get a Dao from de databases to be able to get words
        mMovieDao = db.movieDAO();

        //get all words from the Dao
        mAllMovies = mMovieDao.getAllMovies();
    }

    public LiveData<List<Movie>> getAllContentItems() {
        return mAllMovies;
    }

    public void insertMovie(Movie movie){
        new insertAsyncTask(mMovieDao).execute(movie);
    }

    public Movie getMovie(int id){
      try {
          return new getMovieAsyncTask(mMovieDao).execute(id).get();
      } catch (Exception e){
          Log.e("error", e.getMessage());
          return null;
      }
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDAO mAsyncTaskDao;

        insertAsyncTask(MovieDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.insertMovie(params[0]);
            return null;
        }
    }

    private static class getMovieAsyncTask extends AsyncTask<Integer, Void, Movie>{

        private MovieDAO mAsyncTaskDao;

        getMovieAsyncTask(MovieDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Movie doInBackground(Integer... integers) {

            return mAsyncTaskDao.getMovieById(integers[0]);
        }
    }


    }
    //TODO voeg meerdere CRUD functies toe

