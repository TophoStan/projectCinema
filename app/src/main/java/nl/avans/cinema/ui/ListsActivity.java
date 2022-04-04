package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.LinkedList;

import nl.avans.cinema.R;
import nl.avans.cinema.databinding.ActivityListsBinding;
import nl.avans.cinema.domain.MovieList;
import nl.avans.cinema.ui.adapters.ListAdapter;

public class ListsActivity extends AppCompatActivity {

    private final LinkedList<String> mListList = new LinkedList<>();
    private ActivityListsBinding binding;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MovieList Favourites = new MovieList("Favourites");
        MovieList Plan_to_Watch = new MovieList("Plan to watch");

        mListList.add(Favourites.getName());
        mListList.add(Plan_to_Watch.getName());

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.yourList_list);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new ListAdapter(this, mListList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        binding.toAList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListsActivity.this, SingleListActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lists_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.lists_home) {
            startActivity(new Intent(ListsActivity.this, MainActivity.class));
        }  else if (item.getItemId() == R.id.lists_logout) {
            startActivity(new Intent(ListsActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
