package nl.avans.cinema.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.api.task.FetchMovieDetails;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.ui.DetailActivity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.FilmHolder> {

    private List<Movie> movies = new ArrayList<>();
    private Context mContext;

    public MovieAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public FilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.film_layout_main, parent, false);
        return new FilmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.FilmHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieRating.setText("Rating: " + movie.getVote_average());
        String imgURL = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + movie.getPoster_path();
        Glide.with(mContext).load(imgURL).into(holder.movieIMG);
    }

    @Override
    public int getItemCount() {
        if (movies.isEmpty()) {
            return 0;
        }
        return movies.size();
    }

    public void setMovies(List<Movie> movieList) {
        this.movies = movieList;
        notifyDataSetChanged();
    }

    class FilmHolder extends RecyclerView.ViewHolder implements View.OnClickListener, FetchMovieDetails.OnFetchMovieDetailsListener, Serializable{
        private TextView movieTitle;
        private ImageView movieIMG;
        private TextView movieRating;

        public FilmHolder (@NonNull View itemView) {
            super(itemView);
            this.movieTitle = itemView.findViewById(R.id.film_title);
            this.movieIMG = itemView.findViewById(R.id.film_img);
            this.movieRating = itemView.findViewById(R.id.rating);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FetchMovieDetails fetchMovieDetails = new FetchMovieDetails(this);
            fetchMovieDetails.execute(movies.get(getAdapterPosition()).getId());
        }

        @Override
        public void onReceivingMovieDetails(Movie response) {
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("movie", response);
            mContext.startActivity(detailIntent);
        }
    }
}
