package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.GenreListResult;
import nl.avans.cinema.dataacces.api.calls.ListResult;
import nl.avans.cinema.databinding.ActivitySingleListBinding;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.ui.adapters.ListMovieAdapter;
import nl.avans.cinema.ui.dialogs.DeleteListDialog;
import nl.avans.cinema.ui.dialogs.GenreDialogFilterFragment;

public class SingleListActivity extends AppCompatActivity implements GenreDialogFilterFragment.NoticeDialogListener {

    private static final String LOG_TAG = SingleListActivity.class.getSimpleName();
    private ActivitySingleListBinding binding;
    private ListMovieAdapter adapter;
    private ContentViewModel contentViewModel;
    private ListResult movieList;
    private DeleteListDialog mDeleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieList = (ListResult) getIntent().getSerializableExtra("list");
        binding.listNamePage.setText(movieList.getName());

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Zet methode in adapter die delete button visible zet
                changeDeleteButtonVisibility();
            }
        });

        if (movieList.getDescription().isEmpty()) {
            binding.listDesc.setVisibility(View.GONE);
        } else {
            binding.listDesc.setText(movieList.getDescription());
        }

        this.contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        adapter = new ListMovieAdapter(this, this.contentViewModel, this, this.movieList);
        binding.listRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_column_count)));
        binding.listRecyclerView.setAdapter(adapter);

        contentViewModel.getListById(movieList.getId()).observe(this, listResult -> {
            adapter.setMovies(listResult.getResults());

        });
        binding.removeFilterFab.setVisibility(View.INVISIBLE);
        binding.removeFilterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        for (int i = 0; i < binding.listRecyclerView.getChildCount(); i++) {
            binding.listRecyclerView.getChildAt(i).findViewById(R.id.deleteFilm_btn).setVisibility(View.INVISIBLE);
        }
    }

    public void changeDeleteButtonVisibility() {
        for (int i = 0; i < binding.listRecyclerView.getChildCount(); i++) {
            if (binding.listRecyclerView.getChildAt(i).findViewById(R.id.deleteFilm_btn).getVisibility() == View.VISIBLE) {
                binding.listRecyclerView.getChildAt(i).findViewById(R.id.deleteFilm_btn).setVisibility(View.INVISIBLE);
            } else {
                binding.listRecyclerView.getChildAt(i).findViewById(R.id.deleteFilm_btn).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.list_filter) {
        } else if (item.getItemId() == R.id.list_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Come look at my movie list,\n" +
                    "the code is: " + movieList.getId());
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Send To"));
        } else if (item.getItemId() == R.id.list_delete) {
            openDialog();
        } else if (item.getItemId() == R.id.filter_genre) {
            contentViewModel.getGenres().observe(this, genreListResult -> {
                GenreDialogFilterFragment filterFragment = new GenreDialogFilterFragment(genreListResult);
                filterFragment.show(getSupportFragmentManager(), "FilterGenreDialog");
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {

        mDeleteDialog = new DeleteListDialog();
        mDeleteDialog.setListId(movieList.getId());
        mDeleteDialog.setActivity(this);
        mDeleteDialog.show(getSupportFragmentManager(), "Delete dialog");
    }

    @Override
    public void onDialogPositiveClick(GenreListResult genres) {
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        for (Movie m : movieList.getResults()) {
            for (Genre g : genres.getGenres()) {
                for (Integer i : m.getGenre_ids()) {
                    if (g.getId() == i && !filteredMovies.contains(m)) {
                        filteredMovies.add(m);
                    }
                }
            }
        }
        if (!(filteredMovies.size() == 0)) {
            adapter.setMovies(filteredMovies);
            binding.removeFilterFab.setVisibility(View.VISIBLE);
            if(mDeleteDialog != null){
                mDeleteDialog.dismiss();
            }
            return;
        }
        Toast.makeText(this, "No movies with selected filter", Toast.LENGTH_SHORT).show();
    }

    public void reset() {
        adapter.setMovies(movieList.getResults());
        binding.removeFilterFab.setVisibility(View.INVISIBLE);
    }
}