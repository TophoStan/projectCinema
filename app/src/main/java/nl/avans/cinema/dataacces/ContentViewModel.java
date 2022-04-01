package nl.avans.cinema.dataacces;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import nl.avans.cinema.dataacces.api.calls.VideoResults;
import nl.avans.cinema.dataacces.api.task.FetchMovies;
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

    //TODO Crud functies uit repository hier aan toevoegen
}
