package nl.avans.cinema.dataacces.api.task;

import android.content.Context;
import android.util.Log;

import java.util.List;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.calls.MovieResponse;
import nl.avans.cinema.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchMovies {

    private static String LOG_TAG = FetchMovies.class.getSimpleName();

    private OnFetchData mListener;
    private MovieResponse movieResponse = new MovieResponse();

    public FetchMovies(OnFetchData listener){
        mListener = listener;
    }

    public void execute(){

        Call<MovieResponse> call = ApiClient.getUserService().getMovieIds();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(!response.isSuccessful()){
                    Log.d(LOG_TAG, response.message() + "!!");
                    return;
                }
                movieResponse = response.body();
                Log.d(LOG_TAG, movieResponse.getMovies()[0].getTitle());
                mListener.onRecievingMovie(movieResponse);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage() + "!!");
            }
        });
    }


    public interface OnFetchMovieIdListener {
        void onReceivingMovieId(MovieResponse response);
    }
}
