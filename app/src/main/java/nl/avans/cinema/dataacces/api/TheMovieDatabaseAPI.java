package nl.avans.cinema.dataacces.api;

import nl.avans.cinema.dataacces.api.calls.MovieResponse;
import nl.avans.cinema.domain.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TheMovieDatabaseAPI {

    @GET("3/movie/popular?api_key=cfe22f85d9a2f5199dddc1eca8fa2e60")
    Call<MovieResponse> getMovieIds();

    @GET("3/movie/{movie_id}")
    Call<Movie> getMoveById(@Path("movie_id") int movieId);

}
