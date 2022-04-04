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
import nl.avans.cinema.dataacces.api.calls.GuestResult;
import nl.avans.cinema.dataacces.api.calls.ListResult;
import nl.avans.cinema.dataacces.api.calls.ListsResult;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.dataacces.api.calls.RatingRequest;
import nl.avans.cinema.dataacces.api.calls.RatingResult;
import nl.avans.cinema.dataacces.api.calls.RequestTokenResult;
import nl.avans.cinema.dataacces.api.calls.VideoResults;
import nl.avans.cinema.dataacces.dao.CinemaDAO;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.User;
import nl.avans.cinema.ui.DetailActivity;
import nl.avans.cinema.ui.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CinemaRepository {
    private CinemaDAO mMovieDao;
    private Context mContext;
    private TheMovieDatabaseAPI api;
    private LiveData<List<Movie>> mAllMovies;
    private MutableLiveData<VideoResults> mListOfVideos = new MutableLiveData<>();
    private MutableLiveData<CreditResults> mCrewAndCast = new MutableLiveData<>();
    private MutableLiveData<MovieResults> mMovieFilteredResults = new MutableLiveData<>();
    private MutableLiveData<RequestTokenResult> mRequestTokenResult = new MutableLiveData<>();
    private MutableLiveData<AccessTokenResult> mAccessTokenResult = new MutableLiveData<>();
    private MutableLiveData<Convert4To3Result> convertedResult = new MutableLiveData<>();
    private MutableLiveData<RatingResult> mRatingResult = new MutableLiveData<>();
    private MutableLiveData<MovieResults> mRatedMovies = new MutableLiveData<>();
    private MutableLiveData<GuestResult> mGuestData = new MutableLiveData<>();
    private MutableLiveData<ListResult> mListResult = new MutableLiveData<>();
    private MutableLiveData<ListsResult> mListsResult = new MutableLiveData<>();
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

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private CinemaDAO mAsyncTaskDao;

        insertAsyncTask(CinemaDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.insertMovie(params[0]);
            return null;
        }
    }

    public Movie getMovie(int id) {
        try {
            return new getMovieAsyncTask(mMovieDao).execute(id).get();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
            return null;
        }
    }

    private static class getMovieAsyncTask extends AsyncTask<Integer, Void, Movie> {

        private CinemaDAO mAsyncTaskDao;

        getMovieAsyncTask(CinemaDAO dao) {
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

    public MutableLiveData<CreditResults> getCrewAndCastFromMovie(int movieId) {
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

    public MutableLiveData<MovieResults> getFilteredMovieList(String filter, int page) {
        Call<MovieResults> call = api.getMoviesByFilter(filter, page);
        apiCallMovieResults(call);
        return mMovieFilteredResults;
    }

    public MutableLiveData<MovieResults> getSearchResults(String query) {
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

    public MutableLiveData<RequestTokenResult> generateRequestToken() {
        Call<RequestTokenResult> call = api.generateRequestToken("application/json;charset=utf-8", "Bearer " + mContext.getResources().getString(R.string.bearer));
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

    public MutableLiveData<AccessTokenResult> generateAccessToken(String request) {
        Call<AccessTokenResult> call = api.generateAccessToken("application/json;charset=utf-8", "Bearer " + mContext.getResources().getString(R.string.bearer), new AccessTokenRequest(request));
        apiCallAccessToken(call);
        return mAccessTokenResult;
    }

    private void apiCallAccessToken(Call<AccessTokenResult> call) {
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

    public MutableLiveData<RatingResult> setMovieRating(int movieId, int rating, String sessionId, boolean isGuest) {

        if (!isGuest) {
            Call<RatingResult> call = api.setMovieRatingLoggedInUser(movieId, sessionId, "application/json;charset=utf-8", new RatingRequest(rating));
            apiCallMovieRating(call);
        } else {
            Call<RatingResult> call = api.setMovieRatingGuest(movieId, sessionId, "application/json;charset=utf-8", new RatingRequest(rating));
            apiCallMovieRating(call);
        }
        return mRatingResult;
    }

    private void apiCallMovieRating(Call<RatingResult> call) {
        call.enqueue(new Callback<RatingResult>() {
            @Override
            public void onResponse(Call<RatingResult> call, Response<RatingResult> response) {
                mRatingResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RatingResult> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<Convert4To3Result> convertV4SessionToV3(AccessTokenResult access_token) {
        Call<Convert4To3Result> call = api.convertV4To3(access_token);
        apiCallConvertSessionId(call);
        return convertedResult;
    }

    private void apiCallConvertSessionId(Call<Convert4To3Result> call) {
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

    public void insertUser(User user) {
        new insertUserToDatabase(mMovieDao).execute(user);
    }

    private static class insertUserToDatabase extends AsyncTask<User, Void, Void> {
        private CinemaDAO dao;

        public insertUserToDatabase(CinemaDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            dao.insertUser(users[0]);
            return null;
        }
    }

    public void deleteUsers() {
        new deleteUsersFromDatabase(mMovieDao).execute();
    }

    private static class deleteUsersFromDatabase extends AsyncTask<Void, Void, Void> {
        private CinemaDAO dao;

        public deleteUsersFromDatabase(CinemaDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteUsers();
            return null;
        }
    }

    public User getUserInfo() {
        try {
            return new getUserInfoAsyncTask(mMovieDao).execute().get();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        }
    }

    private static class getUserInfoAsyncTask extends AsyncTask<Void, Void, User> {

        private CinemaDAO asyncDao;

        public getUserInfoAsyncTask(CinemaDAO cinemaDAO) {
            asyncDao = cinemaDAO;
        }

        @Override
        protected User doInBackground(Void... users) {
            return asyncDao.getUser();
        }
    }


    public MutableLiveData<MovieResults> getRatedMoviesByUser(String account_id, String session_id) {
        Call<MovieResults> call = api.getRatedMoviesByUser(account_id,"Bearer " + session_id);
        apiCallGetRatedMoviesByUser(call);
        return mRatedMovies;
    }

    private void apiCallGetRatedMoviesByUser(Call<MovieResults> call) {
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                mRatedMovies.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public MutableLiveData<GuestResult> generateGuestSession() {
        Call<GuestResult> call = api.generateGuestSession();
        apiCallGenerateGuest(call);
        return mGuestData;
    }

    private void apiCallGenerateGuest(Call<GuestResult> call) {
        call.enqueue(new Callback<GuestResult>() {
            @Override
            public void onResponse(Call<GuestResult> call, Response<GuestResult> response) {
                mGuestData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GuestResult> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public MutableLiveData<ListResult> getListFromUser(int listId) {
        Call<ListResult> call = api.getList(listId, "application/json;charset=utf-8");
        apiCallGetListFromUser(call);
        return mListResult;
    }

    private void apiCallGetListFromUser(Call<ListResult> call) {
        call.enqueue(new Callback<ListResult>() {
            @Override
            public void onResponse(Call<ListResult> call, Response<ListResult> response) {
                mListResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ListResult> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public MutableLiveData<ListsResult> getListsFromUser(String accountId, String session_id) {
        Call<ListsResult> call = api.getLists(accountId, "Bearer " + session_id);
        apiCallGetListsFromUser(call);
        return mListsResult;
    }

    private void apiCallGetListsFromUser(Call<ListsResult> call) {
        call.enqueue(new Callback<ListsResult>() {
            @Override
            public void onResponse(Call<ListsResult> call, Response<ListsResult> response) {
                mListsResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ListsResult> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}


//TODO voeg meerdere CRUD functies toe

