package nl.avans.cinema.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.api.calls.ListResult;
import nl.avans.cinema.databinding.ActivitySingleListBinding;
import nl.avans.cinema.domain.MovieList;

public class SingleListActivity extends AppCompatActivity {

    private ActivitySingleListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ListResult movieList = (ListResult) getIntent().getSerializableExtra("list");
        binding.listNamePage.setText(movieList.getName());

        //TODO laat films zien in lijst
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu,  menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Context context;
        if (item.getItemId() == R.id.list_filter) {
            Toast.makeText(this, "Filter btn", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.list_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Come look at my movie list,\n" +
                    "the code is:");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Send To"));
        } else if (item.getItemId() == R.id.list_delete) {
            Toast.makeText(this, "Delete btn", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}