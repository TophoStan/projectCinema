package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.avans.cinema.R;

import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.dataacces.api.calls.RequestTokenResult;
import nl.avans.cinema.dataacces.api.task.FetchMovies;

import nl.avans.cinema.dataacces.api.task.FetchSearchResults;
import nl.avans.cinema.dataacces.api.task.OnFetchData;
import nl.avans.cinema.databinding.ActivityMainBinding;
import nl.avans.cinema.ui.adapters.MovieAdapter;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.domain.Movie;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private ContentViewModel contentViewModel;
    private MovieAdapter adapter;
    private String currentFilter;
    private int currentPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MovieAdapter(this);
        binding.filmsRecyclerView.setAdapter(adapter);

        binding.filmsRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_column_count)));

        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        contentViewModel.getAllContentItems().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovies(movies);
            }
        });

        binding.fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        setHomeButtonVisibility(false);
        currentPageNumber = 1;

        loadFilteredMovie("popular", 1);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.home_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                contentViewModel.getSearchResults(s).observe(MainActivity.this, searchResults -> {
                    adapter.setMovies(Arrays.asList(searchResults.getMovies()));
                    setHomeButtonVisibility(true);
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_search) {
            Toast.makeText(this, "Search btn", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.home_sort) {
            Toast.makeText(this, "Sort btn", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.home_lists) {
            if (contentViewModel.getUsers().isGuest()) {
                Toast.makeText(this, "Login to use lists!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(MainActivity.this, ListsActivity.class));
            }
        } else if (item.getItemId() == R.id.home_logout) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (item.getItemId() == R.id.popular) {
            currentFilter = "popular";
            loadFilteredMovie(currentFilter, currentPageNumber);
            setHomeButtonVisibility(true);
        } else if (item.getItemId() == R.id.top_rated) {
            currentFilter = "top_rated";
            loadFilteredMovie(currentFilter, currentPageNumber);
            setHomeButtonVisibility(true);
        } else if (item.getItemId() == R.id.playing_now) {
            currentFilter = "now_playing";
            loadFilteredMovie(currentFilter, currentPageNumber);
            setHomeButtonVisibility(true);
        } else if (item.getItemId() == R.id.upcoming) {
            currentFilter = "upcoming";
            loadFilteredMovie(currentFilter, currentPageNumber);
            setHomeButtonVisibility(true);
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFilteredMovie(String filter, int page) {

        contentViewModel.getMovieResultsWithFilter(filter, page).observe(this, movieResults -> {
            adapter.setMovies(Arrays.asList(movieResults.getMovies()));
        });
    }

    public void setHomeButtonVisibility(boolean isVisible) {
        if (isVisible) {
            binding.fabHome.setVisibility(View.VISIBLE);
            return;
        }
        binding.fabHome.setVisibility(View.INVISIBLE);
    }
}