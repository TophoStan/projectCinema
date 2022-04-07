package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.MakeListRequest;
import nl.avans.cinema.databinding.ActivityListsBinding;
import nl.avans.cinema.domain.MovieList;
import nl.avans.cinema.ui.adapters.ListAdapter;
import nl.avans.cinema.ui.dialogs.AddSharedListDialog;
import nl.avans.cinema.ui.dialogs.ChoiseDialog;
import nl.avans.cinema.ui.dialogs.MakeListDialogFragment;

public class ListsActivity extends AppCompatActivity implements MakeListDialogFragment.NoticeDialogListener {

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
        AddSharedListDialog dialog = new AddSharedListDialog();
        binding.makeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(getSupportFragmentManager(), "MakeListDialog");
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
        } else if (item.getItemId() == R.id.lists_logout) {
            startActivity(new Intent(ListsActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(MakeListRequest request) {
        contentViewModel.makeAList(contentViewModel.getUsers().getAccess_token(), request).observe(this, result -> {
            Toast.makeText(this, "Added " + request.getName(), Toast.LENGTH_SHORT).show();
            reloadPage();
        });

    }

    public void reloadPage(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}
