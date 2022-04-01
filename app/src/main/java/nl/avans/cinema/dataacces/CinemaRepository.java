package nl.avans.cinema.dataacces;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Locale;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.TheMovieDatabaseAPI;
import nl.avans.cinema.dataacces.api.calls.CreditResults;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.dataacces.api.calls.VideoResults;
import nl.avans.cinema.dataacces.dao.MovieDAO;
import nl.avans.cinema.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CinemaRepository {
    private MovieDAO mMovieDao;
    private Context mContext;
    private TheMovieDatabaseAPI api;
    private LiveData<List<Movie>> mAllMovies;
    private MutableLiveData<VideoResults> mListOfVideos = new MutableLiveData<>();
    private MutableLiveData<CreditResults> mCrewAndCast = new MutableLiveData<>();
    private MutableLiveData<MovieResults> mMovieFilteredResults = new MutableLiveData<>();


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

    public void insertMovie(Movie movie) {
        new insertAsyncTask(mMovieDao).execute(movie);
    }

    public Movie getMovie(int id) {
        try {
            return new getMovieAsyncTask(mMovieDao).execute(id).get();
        } catch (Exception e) {
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

    private static class getMovieAsyncTask extends AsyncTask<Integer, Void, Movie> {

        private MovieDAO mAsyncTaskDao;

        getMovieAsyncTask(MovieDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Movie doInBackground(Integer... integers) {

            return mAsyncTaskDao.getMovieById(integers[0]);
        }
    }

    public MutableLiveData<VideoResults> getListOfVideos(int movieId) {
        Call<VideoResults> call = api.getVideosForMovie(movieId);
        apiCallVideos(call);
        return mListOfVideos;
    }

    private void apiCallVideos(Call<VideoResults> call) {
        call.enqueue(new Callback<VideoResults>() {
            @Override
            public void onResponse(Call<VideoResults> call, Response<VideoResults> response) {
                mListOfVideos.setValue(response.body());
            }

            @Override
            public void onFailure(Call<VideoResults> call, Throwable t) {
                //TODO errors
            }
        });
    }

    public MutableLiveData<CreditResults> getCrewAndCastFromMovie(int movieId){
        Call<CreditResults> call = api.getCrewAndCastFromMovie(movieId);
        apiCallCrewAndCast(call);
        return mCrewAndCast;
    }

    private void apiCallCrewAndCast(Call<CreditResults> call) {
        call.enqueue(new Callback<CreditResults>() {
            @Override
            public void onResponse(Call<CreditResults> call, Response<CreditResults> response) {
                mCrewAndCast.setValue(response.body());

            }

            @Override
            public void onFailure(Call<CreditResults> call, Throwable t) {
                //TODO errors
            }
        });
    }

    public MutableLiveData<MovieResults> getFilteredMovieList(String filter, int page){
        Call<MovieResults> call = api.getMoviesByFilter(filter, page);
        apiCallMovieResults(call);
        return mMovieFilteredResults;
    }
    private void apiCallMovieResults(Call<MovieResults> call) {
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                mMovieFilteredResults.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                //TODO errors
            }
        });

    }

    public MutableLiveData<MovieResults> getSearchResults(String query){
        Call<MovieResults> call = api.searchMovie(query);
        apiCallMovieResults(call);
        return mMovieFilteredResults;
    }


}


//TODO voeg meerdere CRUD functies toe

