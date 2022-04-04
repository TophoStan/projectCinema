package nl.avans.cinema.dataacces;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import nl.avans.cinema.dataacces.api.calls.AccessTokenRequest;
import nl.avans.cinema.dataacces.api.calls.AccessTokenResult;
import nl.avans.cinema.dataacces.api.calls.CreditResults;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.dataacces.api.calls.RequestTokenResult;
import nl.avans.cinema.dataacces.api.calls.VideoResults;
import nl.avans.cinema.domain.Movie;

public class ContentViewModel extends AndroidViewModel {

    private CinemaRepository mRepository;
    private LiveData<List<Movie>> mAllMovies;

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

    public void setMovieRating(int movieId, double rating, boolean isUser, String sessionId) {
        mRepository.setMovieRating(movieId,rating, isUser, sessionId);
    }

    //TODO Crud functies uit repository hier aan toevoegen
}
