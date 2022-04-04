package nl.avans.cinema.dataacces.api;

import nl.avans.cinema.dataacces.api.calls.AccessTokenRequest;
import nl.avans.cinema.dataacces.api.calls.AccessTokenResult;
import nl.avans.cinema.dataacces.api.calls.CreditResults;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.dataacces.api.calls.RequestTokenResult;
import nl.avans.cinema.dataacces.api.calls.VideoResults;
import nl.avans.cinema.domain.Movie;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabaseAPI {

    @GET("3/movie/popular?api_key=cfe22f85d9a2f5199dddc1eca8fa2e60")
    Call<MovieResults> getMovieIds();

    @GET("3/movie/{movie_id}?api_key=cfe22f85d9a2f5199dddc1eca8fa2e60")
    Call<Movie> getMoveById(@Path("movie_id") int movieId);

    @GET("3/search/movie?api_key=cfe22f85d9a2f5199dddc1eca8fa2e60")
    Call<MovieResults> searchMovie(@Query("query") String query);

    @GET("3/movie/{movie_id}/videos?api_key=cfe22f85d9a2f5199dddc1eca8fa2e60")
    Call<VideoResults> getVideosForMovie(@Path("movie_id") int movieId);

    @GET("3/movie/{movie_id}/credits?api_key=cfe22f85d9a2f5199dddc1eca8fa2e60")
    Call<CreditResults> getCrewAndCastFromMovie(@Path("movie_id") int movieId);

    @GET("3/movie/{filter}?api_key=cfe22f85d9a2f5199dddc1eca8fa2e60")
    Call<MovieResults> getMoviesByFilter(@Path("filter") String filter,
                                         @Query("page") int page);

    @POST("4/auth/request_token")
    Call<RequestTokenResult> generateRequestToken(@Header("Content-Type") String content_type,
                                                  @Header("Authorization") String authorization);

    @POST("4/auth/access_token")
    Call<AccessTokenResult> generateAccessToken(@Header("Content-Type") String content_type,
                                                @Header("Authorization") String authorization,
                                                @Body AccessTokenRequest request);

}
