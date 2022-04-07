package nl.avans.cinema.dataacces;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import nl.avans.cinema.dataacces.api.calls.AccessTokenRequest;
import nl.avans.cinema.dataacces.api.calls.AccessTokenResult;
import nl.avans.cinema.dataacces.api.calls.AddItemRequest;
import nl.avans.cinema.dataacces.api.calls.AddItemResult;
import nl.avans.cinema.dataacces.api.calls.Convert4To3Result;
import nl.avans.cinema.dataacces.api.calls.CreditResults;
import nl.avans.cinema.dataacces.api.calls.DeleteItemRequest;
import nl.avans.cinema.dataacces.api.calls.DeleteListResult;
import nl.avans.cinema.dataacces.api.calls.GuestResult;
import nl.avans.cinema.dataacces.api.calls.ListAddItems;
import nl.avans.cinema.dataacces.api.calls.ListRemoveItems;
import nl.avans.cinema.dataacces.api.calls.ListResult;
import nl.avans.cinema.dataacces.api.calls.ListsResult;
import nl.avans.cinema.dataacces.api.calls.MakeListRequest;
import nl.avans.cinema.dataacces.api.calls.MakeListResult;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.dataacces.api.calls.RatingResult;
import nl.avans.cinema.dataacces.api.calls.RequestTokenResult;
import nl.avans.cinema.dataacces.api.calls.VideoResults;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.User;

public class ContentViewModel extends AndroidViewModel {

    private CinemaRepository mRepository;
    private LiveData<List<Movie>> mAllMovies;
    private LiveData<String> currentFilter;


    public ContentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CinemaRepository(application);
        mAllMovies = mRepository.getAllContentItems();
    }

    public LiveData<List<Movie>> getAllContentItems(){
        return mAllMovies;

    }

    public Movie getMovie(int id){
       return mRepository.getMovie(id);
    }

    public void insertMovie(Movie movie){
        mRepository.insertMovie(movie);
    }

    public LiveData<List<Movie>> getSearchMovies(String query){
        return null;
    }

    public MutableLiveData<VideoResults> getVideoFromMovie(int movieId){
        return mRepository.getListOfVideos(movieId);
    }

    public MutableLiveData<CreditResults> getCrewAndCastFromMovie(int movieId){
        return mRepository.getCrewAndCastFromMovie(movieId);
    }

    public MutableLiveData<MovieResults> getMovieResultsWithFilter(String filter, int page){
        return mRepository.getFilteredMovieList(filter, page);
    }

    public MutableLiveData<MovieResults> getSearchResults(String query){
        return mRepository.getSearchResults(query);
    }

    public MutableLiveData<RequestTokenResult> generateRequestToken(){
        return mRepository.generateRequestToken();
    }

    public MutableLiveData<AccessTokenResult> generateLogin(String request){
        return mRepository.generateAccessToken(request);
    }

    public MutableLiveData<Convert4To3Result> convertV4ToV3SessionId(AccessTokenResult tokenResult){
        return mRepository.convertV4SessionToV3(tokenResult);
    }

    public MutableLiveData<RatingResult> setMovieRating(int movieId, int rating, String sessionId, boolean isGuest) {
       return mRepository.setMovieRating(movieId,rating, sessionId, isGuest);
    }

    public MutableLiveData<MovieResults> getRatedMoviesByUser(String account_id, String access_token){
        return mRepository.getRatedMoviesByUser(account_id, access_token);
    }

    public MutableLiveData<GuestResult> generateGuestSession(){
        return mRepository.generateGuestSession();
    }

    public User getUsers(){
        return mRepository.getUserInfo();
    }

    public void deleteUsers(){
        mRepository.deleteUsers();
    }

    public void insertUser(User user){
        mRepository.insertUser(user);
    }

    public MutableLiveData<ListResult> getListById(int listId) {
        return mRepository.getListFromUser(listId);
    }

    public MutableLiveData<ListsResult> getListsFromUser(String account_id, String session_id) {
        return mRepository.getListsFromUser(account_id, session_id);
    }

    public MutableLiveData<Movie> getMovieById(int id){
        return mRepository.getMovieById(id);
    }

    public Boolean deleteItemFromList(int listId, String authorization, ListRemoveItems deleteItemRequestList) {
        return mRepository.deleteItemFromList(listId, authorization, deleteItemRequestList);
    }

    public MutableLiveData<AddItemResult> addItemsToList(int listId, String authorization, ListAddItems addItemRequestList) {
        return mRepository.addItemsToList(listId, authorization, addItemRequestList);
    }

    public MutableLiveData<DeleteListResult> deleteList(int listId, String session_id) {
        return mRepository.deleteList(listId, session_id);
    }

    public MutableLiveData<MakeListResult> makeAList(String auth, MakeListRequest request){
        return  mRepository.makeAList(auth, request);
    }
    //TODO Crud functies uit repository hier aan toevoegen
}
