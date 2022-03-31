package nl.avans.cinema.dataacces.api.task;

import android.util.Log;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.calls.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchSearchResults {

    private static String LOG_TAG = FetchMovies.class.getSimpleName();

    private FetchMovies.OnFetchMovieIdListener mListener;
    private MovieResponse movieResponse = new MovieResponse();

    public FetchSearchResults(FetchMovies.OnFetchMovieIdListener listener){
        mListener = listener;
    }
    public void execute(String query){

        Call<MovieResponse> call = ApiClient.getUserService().searchMovie(query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(!response.isSuccessful()){
                    Log.d(LOG_TAG, response.message() + "!!");
                    return;
                }
                movieResponse = response.body();
                Log.d(LOG_TAG, movieResponse.getMovies()[0].getTitle());
                mListener.onReceivingMovieId(movieResponse);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage() + "!!");
            }
        });
    }
}
