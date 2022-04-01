package nl.avans.cinema.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;


import android.view.Menu;

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

import java.util.Locale;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.databinding.ActivityDetailBinding;
import nl.avans.cinema.domain.Cast;
import nl.avans.cinema.domain.Crew;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.Video;
import nl.avans.cinema.ui.adapters.CompanyAdapter;
import retrofit2.http.HEAD;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private ContentViewModel mViewModel;
    private Movie mMovie;
    private String trailerLink;
<<<<<<< HEAD
    private String moviePageLink;
=======
    private String link = "https://www.themoviedb.org/video/play?key=";
>>>>>>> e254a70f293122f9f6342c2cd3133578f79e1ff0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mMovie = (Movie) getIntent().getSerializableExtra("movie");
        mViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        setData(mMovie);

        mMovie = (Movie) getIntent().getSerializableExtra("movie");
        mViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        setData(mMovie);
        loadCrewAndCast();

    }




        /*Alternative Titles RecyclerView*/

        //alternateRecyclerView = findViewById(R.id.)
        //GridLayoutManager threeColumnLayoutManager = new GridLayoutManager(this, 3);
        //alternateRecyclerView.setLayoutManager(threeColumnLayoutManager);


//        Movie movie = (Movie) getIntent().getSerializableExtra("movie");


    public void setData(Movie movie) {
        // load trailer
        loadVideo();

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

<<<<<<< HEAD

        // trailer
        loadVideo();

        // get link to homepage
        loadPage();

=======
>>>>>>> e254a70f293122f9f6342c2cd3133578f79e1ff0
        // description / overview
        binding.detailDescription.setText(movie.getOverview());

        /*Cast List*/


        /*Crew List*/

        /*Company List*/
        CompanyAdapter companyAdapter = new CompanyAdapter(this);
        binding.companyRecyclerview.setAdapter(companyAdapter);
        binding.companyRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        companyAdapter.setCompanies(mMovie.getProduction_companies());

        // add to list
        binding.addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this, AddToListPopUp.class));
            }
        });

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
            if (trailerLink != null) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink)));
            } else {
                Toast.makeText(this, "This movie has no trailer", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.detail_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Come look at this cool movie I found\n" +
                    moviePageLink);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Send To"));
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadVideo() {
        mViewModel.getVideoFromMovie(mMovie.getId()).observe(this, videoResults -> {

            if (videoResults.getResults().length != 0) {
                for (Video video : videoResults.getResults()) {
                    if (video.isOfficial()) {
                        trailerLink = link + video.getKey();
                    }
                }
            }
        });
    }


    public void loadPage() {
        String link = "https://www.themoviedb.org/movie/";
        String movieTitle = mMovie.getTitle();
        movieTitle = movieTitle.toLowerCase();
        movieTitle = movieTitle.replace(" ", "-");
        moviePageLink = link + mMovie.getId() + "-" + movieTitle;
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
