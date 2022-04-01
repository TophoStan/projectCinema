package nl.avans.cinema.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;


import com.bumptech.glide.Glide;

import nl.avans.cinema.R;
import nl.avans.cinema.databinding.ActivityDetailBinding;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;

public class DetailActivity extends Activity {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addCardview.setVisibility(View.VISIBLE);
        }});

        binding.popupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addCardview.setVisibility(View.INVISIBLE);
            }});



        /*Alternative Titles RecyclerView*/

        //alternateRecyclerView = findViewById(R.id.)
        //GridLayoutManager threeColumnLayoutManager = new GridLayoutManager(this, 3);
        //alternateRecyclerView.setLayoutManager(threeColumnLayoutManager);


        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        String imgURL = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + movie.getPoster_path();
        Glide.with(this).load(imgURL).into(binding.detailImage);
        binding.detailTitle.setText(movie.getTitle());
        // binding.detailDate.setText(movie.getReleaseDate().toString());
        StringBuilder genresString = new StringBuilder();
        for (Genre g: movie.getGenres()) {
            genresString.append(g.getName());
            genresString.append(", ");
        }

        binding.detailGenre.setText(genresString);
        binding.detailDescription.setText(movie.getOverview());

        /*Cast List*/
        binding.crewList.setText(movie.getProduction_companies().get(0).getName());
        /*Crew List*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }


}
