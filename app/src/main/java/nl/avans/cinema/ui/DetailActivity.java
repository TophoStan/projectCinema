package nl.avans.cinema.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.view.Menu;
import android.webkit.WebSettings;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.databinding.ActivityDetailBinding;
import nl.avans.cinema.domain.Cast;
import nl.avans.cinema.domain.Crew;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.Video;
import nl.avans.cinema.ui.adapters.CompanyAdapter;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private ContentViewModel mViewModel;
    private Movie mMovie;
    private String trailerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        /*binding.addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addCardview.setVisibility(View.VISIBLE);
            }
        });

        binding.popupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addCardview.setVisibility(View.INVISIBLE);

            }
        });*/

        mMovie = (Movie) getIntent().getSerializableExtra("movie");
        mViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        setData(mMovie);
        loadCrewAndCast();

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
        if (movie.getGenres().size() > 1) {
            binding.genreTitle.setText(R.string.genres);
        }

        StringBuilder genresString = new StringBuilder();
        for (Genre g : movie.getGenres()) {
            genresString.append(g.getName());
            genresString.append(", ");
        }
        genresString.deleteCharAt(genresString.length() - 2);
        binding.detailGenre.setText(genresString);

        binding.detailDescription.setText(mMovie.getOverview());


        // trailer
        loadVideo();

        // description / overview
        binding.detailDescription.setText(movie.getOverview());

        /*Cast List*/


        /*Crew List*/

        /*Company List*/
        CompanyAdapter companyAdapter = new CompanyAdapter(this);
        binding.companyRecyclerview.setAdapter(companyAdapter);
        binding.companyRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        companyAdapter.setCompanies(mMovie.getProduction_companies());
        loadVideo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.detail_trailer) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink)));
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadVideo() {
        String link = "https://www.themoviedb.org/video/play?key=";
        mViewModel.getVideoFromMovie(mMovie.getId()).observe(this, videoResults -> {
            for (Video video : videoResults.getResults()) {
                if (video.isOfficial()) {
                    trailerLink = link + video.getKey();
                }
            }
        });
    }
    public void loadCrewAndCast(){
        mViewModel.getCrewAndCastFromMovie(mMovie.getId()).observe(this, creditResults -> {
            //Hier krijg je crew and cast binnen
            for (Crew crew: creditResults.getCrew()) {

            }
            for (Cast cast: creditResults.getCast()) {

            }
        });
    }
}
