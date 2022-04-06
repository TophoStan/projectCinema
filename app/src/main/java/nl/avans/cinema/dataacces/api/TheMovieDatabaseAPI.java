package nl.avans.cinema.dataacces.api;

import nl.avans.cinema.dataacces.api.calls.AccessTokenRequest;
import nl.avans.cinema.dataacces.api.calls.AccessTokenResult;
import nl.avans.cinema.dataacces.api.calls.AddItemResult;
import nl.avans.cinema.dataacces.api.calls.Convert4To3Result;
import nl.avans.cinema.dataacces.api.calls.CreditResults;
import nl.avans.cinema.dataacces.api.calls.DeleteListResult;
import nl.avans.cinema.dataacces.api.calls.GuestResult;
import nl.avans.cinema.dataacces.api.calls.ListAddItems;
import nl.avans.cinema.dataacces.api.calls.ListRemoveItems;
import nl.avans.cinema.dataacces.api.calls.ListResult;
import nl.avans.cinema.dataacces.api.calls.ListsResult;
import nl.avans.cinema.dataacces.api.calls.MakeListResult;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.dataacces.api.calls.RatingRequest;
import nl.avans.cinema.dataacces.api.calls.RatingResult;
import nl.avans.cinema.dataacces.api.calls.MakeListRequest;
import nl.avans.cinema.dataacces.api.calls.RequestTokenResult;
import nl.avans.cinema.dataacces.api.calls.VideoResults;
import nl.avans.cinema.domain.Movie;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabaseAPI {
    String key = "cfe22f85d9a2f5199dddc1eca8fa2e60";

    @GET("3/movie/popular?api_key=" + key)
    Call<MovieResults> getMovieIds();

    @GET("3/movie/{movie_id}?api_key=" + key)
    Call<Movie> getMoveById(@Path("movie_id") int movieId);

    @GET("3/search/movie?api_key=" + key)
    Call<MovieResults> searchMovie(@Query("query") String query);

    @GET("3/movie/{movie_id}/videos?api_key=" + key)
    Call<VideoResults> getVideosForMovie(@Path("movie_id") int movieId);

    @GET("3/movie/{movie_id}/credits?api_key=" + key)
    Call<CreditResults> getCrewAndCastFromMovie(@Path("movie_id") int movieId);

    @GET("3/movie/{filter}?api_key=" + key)
    Call<MovieResults> getMoviesByFilter(@Path("filter") String filter,
                                         @Query("page") int page);

    @POST("4/auth/request_token")
    Call<RequestTokenResult> generateRequestToken(@Header("Content-Type") String content_type,
                                                  @Header("Authorization") String authorization);

    @POST("4/auth/access_token")
    Call<AccessTokenResult> generateAccessToken(@Header("Content-Type") String content_type,
                                                @Header("Authorization") String authorization,
                                                @Body AccessTokenRequest request);

    @POST("3/movie/{movie_id}/rating?api_key=" + key)
    Call<RatingResult> setMovieRatingLoggedInUser(@Path("movie_id") int movieId,
                                                  @Query("session_id") String sessionId,
                                                  @Header("Content-Type") String content_type,
                                                  @Body RatingRequest ratingRequest);

    @POST("3/movie/{movie_id}/rating?api_key=" + key)
    Call<RatingResult> setMovieRatingGuest (@Path("movie_id") int movieId,
                                                  @Query("guest_session_id") String sessionId,
                                                  @Header("Content-Type") String content_type,
                                                  @Body RatingRequest ratingRequest);

    @POST("3/authentication/session/convert/4?api_key=" + key)
    Call<Convert4To3Result> convertV4To3(@Body AccessTokenResult accessToken);

    @GET("4/account/{account_id}/movie/rated?api_key=" + key)
    Call<MovieResults> getRatedMoviesByUser(@Path("account_id") String account_id,
                                            @Header("authorization") String authorization);

    @GET("3/authentication/guest_session/new?api_key=" + key)
    Call<GuestResult> generateGuestSession();

    @GET("4/list/{list_id}?api_key=" + key)
    Call<ListResult> getList(@Path("list_id") int listId,
                             @Header("Content_Type") String content_type);

    @GET("4/account/{account_id}/lists")
    Call<ListsResult> getLists(@Path("account_id") String accountId,
                               @Header("authorization") String authorization);


    // werkt nog niet!! //
    @DELETE("4/list/{list_id}/items?api_key=" + key)
    Call<Boolean> deleteItemFromList(@Path("list_id") int listId,
                                     @Header("authorization") String authorization,
                                     @Header("Content_Type") String content_type,
                                     @Body ListRemoveItems items);
    // !!!!!!!!!!!!!!!!//

    @POST("4/list/{list_id}/items?api_key=" + key)
    Call<AddItemResult> addItemsToList(@Path("list_id") int listId,
                                       @Header("authorization") String authorization,
                                       @Header("Content_Type") String content_type,
                                       @Body ListAddItems items);

    @DELETE("4/list/{list_id}?api_key=" + key)
    Call<DeleteListResult> deleteList(@Path("list_id") int listId,
                                      @Header("authorization") String authorization,
                                      @Header("Content_Type") String content_type);

    @POST("4/list")
    Call<MakeListResult> makeList(@Header("authorization") String authorization,
                                  @Header("Content_Type") String content_type,
                                  @Body MakeListRequest body);

}
