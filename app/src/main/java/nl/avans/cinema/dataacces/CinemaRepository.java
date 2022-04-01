package nl.avans.cinema.dataacces;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.TheMovieDatabaseAPI;
import nl.avans.cinema.dataacces.api.calls.VideoResults;
import nl.avans.cinema.dataacces.dao.MovieDAO;
import nl.avans.cinema.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CinemaRepository {
    private MovieDAO mMovieDao;
    private LiveData<List<Movie>> mAllMovies;
    private Context mContext;
    private TheMovieDatabaseAPI api;
    private MutableLiveData<VideoResults> mListOfVideos = new MutableLiveData<>();

    public CinemaRepository(Application application) {
        // get database using the Room annotations
        CinemaDatabase db = CinemaDatabase.getDatabase(application);
        mMovieDao = db.movieDAO();

        this.api = ApiClient.getUserService();
        this.mContext = application.getApplicationContext();

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

    public MutableLiveData<VideoResults> getListOfVideos(int movieId){
        Call<VideoResults> call = api.getVideosForMovie(movieId);
        apiCallVideos(call);
        return mListOfVideos;
    }

    private void apiCallVideos(Call<VideoResults> call){
        call.enqueue(new Callback<VideoResults>() {
            @Override
            public void onResponse(Call<VideoResults> call, Response<VideoResults> response) {
                mListOfVideos.setValue(response.body());
                // Log.d("VideoName",response.body().getResults()[0].getName() + "!");
            }

            @Override
            public void onFailure(Call<VideoResults> call, Throwable t) {
                //TODO errors
            }
        });
    }

    }
    //TODO voeg meerdere CRUD functies toe

