package nl.avans.cinema.dataacces.api.task;

import android.util.Log;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchMovies {

    private static String LOG_TAG = FetchMovies.class.getSimpleName();

    private OnFetchData mListener;
    private MovieResults movieResults = new MovieResults();

    public FetchMovies(OnFetchData listener){
        mListener = listener;
    }

    public void execute(){

        Call<MovieResults> call = ApiClient.getUserService().getMovieIds();
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                if(!response.isSuccessful()){
                    Log.d(LOG_TAG, response.message() + "!!");
                    return;
                }
                movieResults = response.body();
                Log.d(LOG_TAG, movieResults.getMovies()[0].getTitle());
                mListener.onRecievingMovie(movieResults, "fetchPopular");
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage() + "!!");
            }
        });
    }


    public interface OnFetchMovieIdListener {
        void onReceivingMovieId(MovieResults response);
    }
}
