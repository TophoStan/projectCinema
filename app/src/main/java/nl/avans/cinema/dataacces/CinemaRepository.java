package nl.avans.cinema.dataacces;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.TheMovieDatabaseAPI;
import nl.avans.cinema.dataacces.api.calls.AccessTokenRequest;
import nl.avans.cinema.dataacces.api.calls.AccessTokenResult;
import nl.avans.cinema.dataacces.api.calls.Convert4To3Result;
import nl.avans.cinema.dataacces.api.calls.CreditResults;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.dataacces.api.calls.RequestTokenResult;
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
    private MutableLiveData<RequestTokenResult> mRequestTokenResult = new MutableLiveData<>();
    private MutableLiveData<AccessTokenResult> mAccessTokenResult = new MutableLiveData<>();
    private MutableLiveData<Convert4To3Result> convertedResult = new MutableLiveData<>();
    private static final String LOG_TAG = CinemaRepository.class.getSimpleName();


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
                Log.e(LOG_TAG, t.getMessage());
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
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public MutableLiveData<MovieResults> getFilteredMovieList(String filter, int page){
        Call<MovieResults> call = api.getMoviesByFilter(filter, page);
        apiCallMovieResults(call);
        return mMovieFilteredResults;
    }
    public MutableLiveData<MovieResults> getSearchResults(String query){
        Call<MovieResults> call = api.searchMovie(query);
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
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }

    public MutableLiveData<RequestTokenResult> generateRequestToken(){
        Call<RequestTokenResult> call = api.generateRequestToken("application/json;charset=utf-8","Bearer " + mContext.getResources().getString(R.string.bearer));
        apiCallRequestToken(call);
        return mRequestTokenResult;
    }
    private void apiCallRequestToken(Call<RequestTokenResult> call) {
        call.enqueue(new Callback<RequestTokenResult>() {
            @Override
            public void onResponse(Call<RequestTokenResult> call, Response<RequestTokenResult> response) {
                mRequestTokenResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RequestTokenResult> call, Throwable t) {
                //TODO errors
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }

    public MutableLiveData<AccessTokenResult> generateAccessToken(String request){
        Call<AccessTokenResult> call = api.generateAccessToken("application/json;charset=utf-8", "Bearer " + mContext.getResources().getString(R.string.bearer), new AccessTokenRequest(request));
        apiCallAccessToken(call);
        return mAccessTokenResult;
    }

    private void apiCallAccessToken(Call<AccessTokenResult> call){
        call.enqueue(new Callback<AccessTokenResult>() {
            @Override
            public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
                mAccessTokenResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AccessTokenResult> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public void setMovieRating(int movieId, double rating, boolean isUser, String sessionId) {
        String ratingRequest = "{'value': "+ rating + "}";
        String sessionType = "session_id";

        if (!isUser) {
            sessionType = "guest_" + sessionType;
        }
        api.setMovieRating(movieId, sessionType, sessionId, "application/json;charset=utf-8", ratingRequest);
    }

    public MutableLiveData<Convert4To3Result> convertV4SessionToV3(AccessTokenResult access_token){
        Call<Convert4To3Result> call = api.convertV4To3(access_token);
        apiCallConvertSessionId(call);
        return convertedResult;
    }
    private void apiCallConvertSessionId(Call<Convert4To3Result> call){
        call.enqueue(new Callback<Convert4To3Result>() {
            @Override
            public void onResponse(Call<Convert4To3Result> call, Response<Convert4To3Result> response) {
                convertedResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Convert4To3Result> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}


//TODO voeg meerdere CRUD functies toe

