package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import nl.avans.cinema.R;
import nl.avans.cinema.databinding.ActivitySingleListBinding;

public class SingleListActivity extends AppCompatActivity {

    private ActivitySingleListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu,  menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.list_filter) {
            Toast.makeText(this, "Filter btn", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.list_share) {
            Toast.makeText(this, "Share btn", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.list_delete) {
            Toast.makeText(this, "Delete btn", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}