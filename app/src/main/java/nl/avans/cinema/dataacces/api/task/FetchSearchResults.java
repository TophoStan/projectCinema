package nl.avans.cinema.dataacces.api.task;

import nl.avans.cinema.dataacces.api.ApiClient;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchSearchResults {

    private static String LOG_TAG = FetchMovies.class.getSimpleName();

    private OnFetchData mListener;
    private MovieResults movieResults = new MovieResults();

    public FetchSearchResults(OnFetchData listener){
        mListener = listener;
    }
    public void execute(String query){

        Call<MovieResults> call = ApiClient.getUserService().searchMovie(query);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                if(!response.isSuccessful()){
                    return;
                }
                mListener.onRecievingMovie(response.body(), "fetchSearch");
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });
    }
}
