package nl.avans.cinema.ui;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.databinding.ActivityDetailBinding;
import nl.avans.cinema.domain.Company;
import nl.avans.cinema.domain.Genre;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.Video;
import nl.avans.cinema.ui.adapters.CompanyAdapter;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private ContentViewModel mViewModel;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addToList.setOnClickListener(new View.OnClickListener() {
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
        });



        mMovie = (Movie) getIntent().getSerializableExtra("movie");
        mViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        String imgURL = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + mMovie.getPoster_path();
        Glide.with(this).load(imgURL).into(binding.detailImage);
        binding.detailTitle.setText(mMovie.getTitle());
        // binding.detailDate.setText(movie.getReleaseDate().toString()
        StringBuilder genresString = new StringBuilder();
        for (Genre g : mMovie.getGenres()) {
            genresString.append(g.getName());
            genresString.append(", ");
        }

        binding.detailGenre.setText(genresString);
        binding.detailDescription.setText(mMovie.getOverview());

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
    public void loadVideo(){
        mViewModel.getVideoFromMovie(mMovie.getId()).observe(this, videoResults -> {
            for (Video video: videoResults.getResults()) {
                //TODO STIJN HIER KRIJG JE DE VIDEOS BINNEN!!!


            }
        });
    }


}
