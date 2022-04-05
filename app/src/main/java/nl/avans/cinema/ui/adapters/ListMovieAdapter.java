package nl.avans.cinema.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.domain.Movie;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListMovieHolder> {

    private List<Movie> movies = new ArrayList<>();
    private Context mContext;
    private ViewModel mViewModel;

    public ListMovieAdapter(Context context, ViewModel viewModel) {
        this.mContext = context;
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    public ListMovieAdapter.ListMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.film_layout_list, parent, false);
        return new ListMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieAdapter.ListMovieHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieTitle.setText(movie.getTitle());
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

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }


    public class ListMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieIMG;
        private TextView movieTitle;
        private ImageButton movieDeleteBtn;

        public ListMovieHolder(@NonNull View itemView) {
            super(itemView);
            this.movieIMG = itemView.findViewById(R.id.movieIMG);
            this.movieTitle = itemView.findViewById(R.id.movieTitle);
            this.movieDeleteBtn = itemView.findViewById(R.id.deleteFilm_btn);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
