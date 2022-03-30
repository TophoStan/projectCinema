package nl.avans.cinema.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.api.calls.MovieResponse;
import nl.avans.cinema.dataacces.api.task.FetchMovieDetails;
import nl.avans.cinema.dataacces.api.task.FetchMovies;
import nl.avans.cinema.databinding.ActivityMainBinding;
import nl.avans.cinema.domain.Movie;

public class MainActivity extends AppCompatActivity implements FetchMovies.OnFetchMovieIdListener {

    private ActivityMainBinding binding;
    private Movie[] mMovies;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FetchMovies fetchMovies = new FetchMovies(this);
        fetchMovies.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,  menu);
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
    public void onReceivingMovieId(MovieResponse response) {
        Log.d("maintest", response.getMovies().length + "");
        mMovies = response.getMovies();
    }

}