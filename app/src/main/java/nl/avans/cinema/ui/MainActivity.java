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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import nl.avans.cinema.R;

import nl.avans.cinema.dataacces.api.calls.MovieResponse;
import nl.avans.cinema.dataacces.api.task.FetchMovieDetails;
import nl.avans.cinema.dataacces.api.task.FetchMovies;

import nl.avans.cinema.dataacces.api.task.FetchSearchResults;
import nl.avans.cinema.dataacces.api.task.OnFetchData;
import nl.avans.cinema.databinding.ActivityMainBinding;
import nl.avans.cinema.ui.adapters.MovieAdapter;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.MovieResponse;
import nl.avans.cinema.dataacces.api.task.FetchMovies;
import nl.avans.cinema.domain.Movie;

public class MainActivity extends AppCompatActivity implements OnFetchData {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private ContentViewModel contentViewModel;
    private MovieResponse mMovieResponse;
    private MovieAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MovieAdapter(this);
        binding.filmsRecyclerView.setAdapter(adapter);

        binding.filmsRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_column_count)));


        FetchMovies fetchMovies = new FetchMovies(this);
        fetchMovies.execute();

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
                Toast.makeText(MainActivity.this, "Input: " + s, Toast.LENGTH_SHORT).show();
                FetchSearchResults fetchSearchResults = new FetchSearchResults(MainActivity.this);
                fetchSearchResults.execute(s);
                binding.fabHome.setVisibility(View.VISIBLE);
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
        if (item.getItemId() == R.id.home_filter) {
            Toast.makeText(this, "Filter btn", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.home_search) {
            Toast.makeText(this, "Search btn", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.home_sort) {
            Toast.makeText(this, "Sort btn", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.home_lists) {
            startActivity(new Intent(MainActivity.this, ListsActivity.class));
        } else if (item.getItemId() == R.id.home_logout) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecievingMovie(MovieResponse movieResponse, String action) {
        mMovieResponse = movieResponse;
        if(action.equals("fetchPopular")){
            for (Movie movie : mMovieResponse.getMovies()) {

                contentViewModel.insertMovie(movie);
            }
        } else if(action.equals("fetchSearch")){
            adapter.setMovies(Arrays.asList(movieResponse.getMovies()));
        }

    }
}