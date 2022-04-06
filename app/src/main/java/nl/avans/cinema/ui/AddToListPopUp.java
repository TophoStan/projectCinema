package nl.avans.cinema.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.databinding.PopupAddtolistBinding;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.MovieList;

public class AddToListPopUp extends AppCompatActivity {

    private List<MovieList> movieListList = new ArrayList<>();
    private PopupAddtolistBinding binding;
    private nl.avans.cinema.ui.adapters.ListAdapter mAdapter;
    private ContentViewModel contentViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PopupAddtolistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        mAdapter = new nl.avans.cinema.ui.adapters.ListAdapter(this, contentViewModel, this);
        mAdapter.onAddToList(true, (Movie) getIntent().getSerializableExtra("movie"));

        binding.yourListList.setAdapter(mAdapter);
        binding.yourListList.setLayoutManager(new LinearLayoutManager(this));

        contentViewModel.getListsFromUser(contentViewModel.getUsers().getAccount_id(), contentViewModel.getUsers().getAccess_token()).observe(this, listsResult -> {
            movieListList = listsResult.getResults();
            mAdapter.setLists(movieListList);
        });
    }
}
