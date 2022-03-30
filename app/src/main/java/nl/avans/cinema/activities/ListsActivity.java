package nl.avans.cinema.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import nl.avans.cinema.R;
import nl.avans.cinema.databinding.ActivityListsBinding;

public class ListsActivity extends AppCompatActivity {

    private ActivityListsBinding binding;


    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityListsBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            setContentView(R.layout.activity_main);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.lists_menu, menu);
            return true;
        }
    }
}