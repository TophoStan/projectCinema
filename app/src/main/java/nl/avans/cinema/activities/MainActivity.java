package nl.avans.cinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.databinding.ActivityMainBinding;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SingleListActivity.class));
            }
        });
        ContentViewModel contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        contentViewModel.getAllContentItems();
        Movie movie = contentViewModel.getMovie(1);
        Log.d("test", movie.getTitle());

        for (Genre genre: movie.getGenres()) {
            Log.d("genre", genre.getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,  menu);
        return true;
    }
}