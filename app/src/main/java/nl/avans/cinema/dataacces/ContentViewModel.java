package nl.avans.cinema.dataacces;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import nl.avans.cinema.domain.Content;

public class ContentViewModel extends AndroidViewModel {

    private CinemaRepository mRepository;
    private LiveData<List<Content>> mAllContentItems;

    public ContentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CinemaRepository(application);
        mAllContentItems = mRepository.getAllContentItems();
    }

    public LiveData<List<Content>> getAllContentItems(){
        return mAllContentItems;
    }

    public void insert(Content content){
        mRepository.insert(content);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    //TODO Crud functies uit repository hier aan toevoegen
}
