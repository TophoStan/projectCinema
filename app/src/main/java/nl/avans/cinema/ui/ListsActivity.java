package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import nl.avans.cinema.R;
import nl.avans.cinema.databinding.ActivityListsBinding;

public class ListsActivity extends AppCompatActivity {

    private ActivityListsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
