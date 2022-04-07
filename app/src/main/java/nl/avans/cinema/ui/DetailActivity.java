package nl.avans.cinema.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;


import androidx.lifecycle.ViewModel;
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
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.AccessTokenResult;
import nl.avans.cinema.dataacces.api.calls.Convert4To3Result;
import nl.avans.cinema.dataacces.api.calls.MovieResults;
import nl.avans.cinema.databinding.ActivityDetailBinding;
import nl.avans.cinema.domain.Cast;
import nl.avans.cinema.domain.Crew;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.Video;
import nl.avans.cinema.ui.adapters.CastAdapter;
import nl.avans.cinema.ui.adapters.CompanyAdapter;
import nl.avans.cinema.ui.adapters.CrewAdapter;
import nl.avans.cinema.ui.adapters.ListMovieAdapter;
import retrofit2.http.HEAD;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private ActivityDetailBinding binding;
    private ContentViewModel mViewModel;
    private CrewAdapter mCrewAdapter;
    private CastAdapter mCastAdapter;
    private Movie mMovie;
    private String trailerLink;
    private String moviePageLink;
    private String link = "https://www.youtube.com/watch?v=";
    private String sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mMovie = (Movie) getIntent().getSerializableExtra("movie");
        mViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        setData(mMovie);
    }

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

        // rating bar
        if (!mViewModel.getUsers().isGuest()) {
            //session id
            loadSessionId();

            getMovieRating(movie);
        }

        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    int rating = (Float.valueOf(v*2).intValue());
                    mViewModel.convertV4ToV3SessionId(new AccessTokenResult(mViewModel.getUsers().getAccess_token())).observe(DetailActivity.this, convertedSessionId -> {


                        boolean isGuest = mViewModel.getUsers().isGuest();
                        if (isGuest) {
                            setRating(mMovie.getId(), rating, mViewModel.getUsers().getAccount_id(), true);
                        } else {
                            setRating(mMovie.getId(), rating, convertedSessionId.getSession_id(), false);
                        }
                        Toast.makeText(DetailActivity.this, "Your " + rating + " has been submitted!", Toast.LENGTH_SHORT).show();
                    });
                }

            }
        });

        // trailer
        loadVideo();

        // get link to homepage
        loadPage();

        // description / overview
        binding.detailDescription.setText(movie.getOverview());

        /*Cast List*/
        mCastAdapter = new CastAdapter(this);
        binding.castRecyclerview.setAdapter(mCastAdapter);
        binding.castRecyclerview.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_column_count)));

        /*Crew List*/
        mCrewAdapter = new CrewAdapter(this);
        binding.crewRecyclerview.setAdapter(mCrewAdapter);
        binding.crewRecyclerview.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.grid_column_count)));
        loadCrewAndCast();
        /*Company List*/
        CompanyAdapter companyAdapter = new CompanyAdapter(this);
        binding.companyRecyclerview.setAdapter(companyAdapter);
        binding.companyRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        companyAdapter.setCompanies(mMovie.getProduction_companies());

        // add to list
        binding.addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewModel.getUsers().isGuest()) {
                    Toast.makeText(DetailActivity.this, "Login to use lists!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent addToListIntent = new Intent(DetailActivity.this, AddToListPopUp.class);
                    addToListIntent.putExtra("movie", mMovie);
                    startActivity(addToListIntent);
                }
            }
        });

    }

    public void getMovieRating(Movie movie){
        mViewModel.getRatedMoviesByUser(mViewModel.getUsers().getAccount_id(), mViewModel.getUsers().getAccess_token()).observe(this, ratedMovies -> {
            Log.d(LOG_TAG, ratedMovies.getMovies().length + " rated movies");
            for (Movie m : ratedMovies.getMovies()) {
                if (m.getId() == movie.getId()) {
                    Log.d("movie", m.getTitle() + " with a " + m.getAccount_rating().getValue() + " rating");
                    float rating = calculateRating(m.getAccount_rating().getValue());
                    binding.ratingBar.setRating(rating);
                    break;
                }
            }
        });
    }

    public static Float calculateRating(int rating){
        return (float) rating/2;
    }

    private void loadSessionId() {
        mViewModel.convertV4ToV3SessionId(new AccessTokenResult(mViewModel.getUsers().getAccess_token())).observe(DetailActivity.this, convertedSessionId -> {
            Log.d(LOG_TAG, convertedSessionId.getSession_id());
            sessionID = convertedSessionId.getSession_id();
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

    public void loadCrewAndCast() {
        mViewModel.getCrewAndCastFromMovie(mMovie.getId()).observe(this, creditResults -> {
            Log.d("crewtest", "yoo!");
            mCrewAdapter.setCrewList(creditResults.getCrew());
            mCastAdapter.setCastList(creditResults.getCast());
        });
    }

    public void setRating(int movieId, int rating, String sessionId, boolean isGuest) {
        mViewModel.setMovieRating(movieId, rating, sessionId, isGuest).observe(this, ratingResults -> {
            Log.d(LOG_TAG, ratingResults.getStatus_message() +" with rating: " +rating);
        });
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getExtras().getBoolean("listPage")) {
            Intent listIntent = new Intent(DetailActivity.this, SingleListActivity.class);
            listIntent.putExtra("list", getIntent().getSerializableExtra("list"));
            startActivity(listIntent);
        } else {
            super.onBackPressed();
        }
    }
}
