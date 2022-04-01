package nl.avans.cinema.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

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

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        setData(movie);
    }

    public void setData(Movie movie) {
        // image
        String imgURL = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + movie.getPoster_path();
        Glide.with(this).load(imgURL).into(binding.detailImage);

        // title
        binding.detailTitle.setText(movie.getTitle());

        // release date
        binding.detailDate.setText(movie.getRelease_date());

        // genres
        StringBuilder genresString = new StringBuilder();
        for (Genre g : movie.getGenres()) {
            genresString.append(g.getName());
            genresString.append(", ");
        }
        genresString.deleteCharAt(genresString.length() - 2);
        binding.detailGenre.setText(genresString);

        // trailer
        if (!movie.isHasMovie()) {
            binding.detailTrailer.setVisibility(View.GONE);
        }

        // description / overview
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
