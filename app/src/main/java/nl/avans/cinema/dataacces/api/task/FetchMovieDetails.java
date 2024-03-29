package nl.avans.cinema.dataacces.api.task;

import android.util.Log;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.domain.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchMovieDetails {
    private static String LOG_TAG = FetchMovies.class.getSimpleName();

    private OnFetchMovieDetailsListener mListener;
    public FetchMovieDetails(OnFetchMovieDetailsListener listener){
        mListener = listener;
    }

    public void execute(int id){
        Call<Movie> call = ApiClient.getUserService().getMoveById(id);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(!response.isSuccessful()){
                    Log.d(LOG_TAG, response.message());
                    return;
                }
                mListener.onReceivingMovieDetails(response.body());
            }


            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public interface OnFetchMovieDetailsListener {
        void onReceivingMovieDetails(Movie response);
    }


}

