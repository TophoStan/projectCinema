package nl.avans.cinema.activities;

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
        switch (item.getItemId()) {
            case R.id.filter:
                Toast.makeText(this, "Filter knop", Toast.LENGTH_SHORT).show();

            case R.id.deleteList_btn:
                Toast.makeText(this, "Delete knop", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}