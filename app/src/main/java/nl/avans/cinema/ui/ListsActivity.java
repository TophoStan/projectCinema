package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.MakeListRequest;
import nl.avans.cinema.databinding.ActivityListsBinding;
import nl.avans.cinema.domain.MovieList;
import nl.avans.cinema.ui.adapters.ListAdapter;

public class ListsActivity extends AppCompatActivity {

    private List<MovieList> mListList = new ArrayList<>();
    private ActivityListsBinding binding;
    private ListAdapter mAdapter;
    private ContentViewModel contentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        // Create an adapter and supply the data to be displayed.
        mAdapter = new ListAdapter(this, contentViewModel, this);
        // Connect the adapter with the RecyclerView.
        binding.yourListList.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        binding.yourListList.setLayoutManager(new LinearLayoutManager(this));

        // commit data
        contentViewModel.getListsFromUser(contentViewModel.getUsers().getAccount_id(), contentViewModel.getUsers().getAccess_token()).observe(this, listsResult -> {
            mListList = listsResult.getResults();
            mAdapter.setLists(mListList);
        });

        binding.makeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeListRequest request = new MakeListRequest();
                request.setName(binding.nameOfNewList.getText().toString());
                request.setPublic(true);
                request.setIso_639_1("en");
                contentViewModel.makeAList(contentViewModel.getUsers().getAccess_token(), request).observe(ListsActivity.this, result -> {

                        Toast.makeText(ListsActivity.this, "HET LUKT AWOELEH", Toast.LENGTH_SHORT).show();

                });
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
