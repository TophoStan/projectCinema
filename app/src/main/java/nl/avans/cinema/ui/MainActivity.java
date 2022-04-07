package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.view.KeyEvent;
=======
>>>>>>> origin/SortMainActivity
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
<<<<<<< HEAD
import java.util.Iterator;
=======
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
>>>>>>> origin/SortMainActivity
import java.util.List;

import nl.avans.cinema.R;

import nl.avans.cinema.databinding.ActivityMainBinding;
import nl.avans.cinema.ui.adapters.MovieAdapter;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.domain.Movie;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private ContentViewModel contentViewModel;
    private MovieAdapter adapter;
    private String currentFilter;
    private int currentPageNumber;
<<<<<<< HEAD
    private boolean isSearching;
    private boolean buttonsAreEnabled;
=======
    private ArrayList<Movie> movieList = new ArrayList<>();
>>>>>>> origin/SortMainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MovieAdapter(this);
        binding.filmsRecyclerView.setAdapter(adapter);

        binding.filmsRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_column_count)));

        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        binding.fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        setHomeButtonVisibility(false);
        currentPageNumber = 1;

        currentFilter = "popular";
        loadFilteredMovie(currentFilter, 1);
        binding.currentPageNumberView.setText(String.valueOf(currentPageNumber));
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPageBack(binding.filmsRecyclerView);
                binding.filmsRecyclerView.scrollToPosition(0);
            }
        });
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPageNext(binding.filmsRecyclerView);
            }
        });
        isSearching = false;

        binding.currentPageNumberView.setOnKeyListener(this);

        binding.filmsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isSearching) {
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        goPageNext(recyclerView);
                    }
                    if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        goPageBack(recyclerView);
                    }
                }
            }
        });

    }

    public void goPageBack(RecyclerView recyclerView) {

        if (currentPageNumber - 1 > 0) {
            currentPageNumber -= 1;
            recyclerView.scrollToPosition(19);
        }
        loadAPage(currentPageNumber);
    }

    public void goPageNext(RecyclerView recyclerView) {
        if (currentPageNumber + 1 <= 500) {
            currentPageNumber += 1;
        }
        recyclerView.scrollToPosition(0);
        loadAPage(currentPageNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.home_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        MenuItem itemSwitch = menu.findItem(R.id.toggle_buttons);
        itemSwitch.setActionView(R.layout.switch_item);

        Switch sw = (Switch) menu.findItem(R.id.toggle_buttons).getActionView().findViewById(R.id.switch1);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonsAreEnabled = !buttonsAreEnabled;
                setVisibilityOfView(binding.buttonBack, buttonsAreEnabled);
                setVisibilityOfView(binding.buttonNext, buttonsAreEnabled);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                contentViewModel.getSearchResults(s).observe(MainActivity.this, searchResults -> {
                    adapter.setMovies(Arrays.asList(searchResults.getMovies()));
                    setHomeButtonVisibility(true);
                    isSearching = true;
                    setVisibilityOfView(binding.buttonBack, true);
                    setVisibilityOfView(binding.buttonNext, true);
                    setVisibilityOfView(binding.currentPageNumberView, true);
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
<<<<<<< HEAD
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
=======
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getTitle());
        switch (item.getItemId()){
            case R.id.title_asc:
                Collections.sort(movieList, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        return movie.getTitle().compareTo(t1.getTitle());
                    }
                });
                adapter.setMovies(movieList);
                return true;
            case R.id.title_desc:
                Collections.reverse(movieList);
                adapter.setMovies(movieList);
                return true;
        }

        if (item.getItemId() == R.id.home_lists) {
            startActivity(new Intent(MainActivity.this, ListsActivity.class));
>>>>>>> origin/SortMainActivity
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
        } else if (item.getItemId() == R.id.genre) {
            currentFilter = "genre";
            loadFilteredMovie(currentFilter, currentPageNumber);
            setHomeButtonVisibility(true);
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFilteredMovie(String filter, int page) {

        contentViewModel.getMovieResultsWithFilter(filter, page).observe(this, movieResults -> {
            if (movieResults.getMovies().length != 0) {
                adapter.setMovies(Arrays.asList(movieResults.getMovies()));
            } else {
                Log.e(LOG_TAG, "movielist is empty");
            }
        });
    }

    public void setHomeButtonVisibility(boolean isVisible) {
        if (isVisible) {
            binding.fabHome.setVisibility(View.VISIBLE);
            return;
        }
        binding.fabHome.setVisibility(View.INVISIBLE);
    }

    public void setVisibilityOfView(View view, boolean visibility) {
        if (visibility) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            view.setEnabled(false);
            view.setEnabled(true);
            int enteredPageNumber = Integer.parseInt(binding.currentPageNumberView.getText().toString());
            loadAPage(enteredPageNumber);
            binding.filmsRecyclerView.scrollToPosition(0);
        }
        return false;
    }

    public void loadAPage(int enteredPageNumber) {
        if (enteredPageNumber > 0) {
            if (enteredPageNumber >= 500) {
                enteredPageNumber = 500;
                //binding.buttonNext.setVisibility(View.INVISIBLE);
            } else {
                //binding.buttonNext.setVisibility(View.VISIBLE);
            }
            setVisibilityOfView(binding.buttonNext, buttonsAreEnabled);
            currentPageNumber = enteredPageNumber;
            binding.currentPageNumberView.setText(String.valueOf(currentPageNumber));
            loadFilteredMovie(currentFilter, currentPageNumber);
        }
    }
}