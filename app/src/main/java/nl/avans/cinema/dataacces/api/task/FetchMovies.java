package nl.avans.cinema.dataacces.api.task;

import android.util.Log;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.calls.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchMovies {

    private static String LOG_TAG = FetchMovies.class.getSimpleName();

    private OnFetchMovieIdListener mListener;

    public FetchMovies(OnFetchMovieIdListener listener){
        mListener = listener;
    }

    public void execute(){
        Call<MovieResponse> call = ApiClient.getUserService().getMovieIds();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(!response.isSuccessful()){
                    Log.d(LOG_TAG, response.message());
                    return;
                }
                mListener.onReceivingMovieId(response.body());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }


    public interface OnFetchMovieIdListener {
        void onReceivingMovieId(MovieResponse response);
    }
}
