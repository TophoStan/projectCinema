package nl.avans.cinema.ui;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import android.view.Menu;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import nl.avans.cinema.R;
import nl.avans.cinema.databinding.ActivityDetailBinding;
import nl.avans.cinema.domain.Company;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.ui.adapters.CompanyAdapter;

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


        /*Crew List*/

        /*Company List*/
        CompanyAdapter companyAdapter = new CompanyAdapter(this);
        binding.companyRecyclerview.setAdapter(companyAdapter);
        binding.companyRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        companyAdapter.setCompanies(movie.getProduction_companies());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }


}
