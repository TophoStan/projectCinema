package nl.avans.cinema.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.DeleteItemRequest;
import nl.avans.cinema.dataacces.api.calls.ListRemoveItems;
import nl.avans.cinema.dataacces.api.calls.ListResult;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.ui.DetailActivity;
import nl.avans.cinema.ui.SingleListActivity;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListMovieHolder> {

    private List<Movie> mMovies = new ArrayList<>();
    private Context mContext;
    private ContentViewModel mViewModel;
    private SingleListActivity mActivity;
    private ListResult mListResult;
    private ListMovieHolder mHolder;

    public ListMovieAdapter(Context context, ContentViewModel viewModel, SingleListActivity activity, ListResult listResult) {
        this.mContext = context;
        this.mViewModel = viewModel;
        this.mActivity = activity;
        this.mListResult = listResult;
    }

    @NonNull
    @Override
    public ListMovieAdapter.ListMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.film_layout_list, parent, false);
        return new ListMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieHolder holder, int position) {
        mHolder = holder;
        Movie movie = mMovies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        String imgURL = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + movie.getPoster_path();

        if (movie.getPoster_path() == null) {
            Glide.with(mContext).load(AppCompatResources.getDrawable(mContext, R.drawable.placeholderimage)).into(holder.movieIMG);
        } else {
            Glide.with(mContext).load(imgURL).into(holder.movieIMG);
        }

    }

    @Override
    public int getItemCount() {
        if (mMovies.isEmpty()) {
            return 0;
        }
        return mMovies.size();
    }

    public void setMovies(List<Movie> mMovies) {
        this.mMovies = mMovies;
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
            this.movieDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<DeleteItemRequest> removeList = new ArrayList<>();
                    DeleteItemRequest deleteItemRequest = new DeleteItemRequest("movie", mMovies.get(getAdapterPosition()).getId());
                    removeList.add(deleteItemRequest);
                    ListRemoveItems listRemoveItems = new ListRemoveItems();
                    listRemoveItems.setItems(removeList);
                    mViewModel.deleteItemFromList(mListResult.getId(), mViewModel.getUsers().getAccess_token(), listRemoveItems);
                    mContext.startActivity(new Intent(mContext, SingleListActivity.class).putExtra("list", mListResult));
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mViewModel.getMovieById(mMovies.get(getAdapterPosition()).getId()).observe(mActivity, movie -> {
                Intent detailIntent = new Intent(mContext, DetailActivity.class);
                detailIntent.putExtra("movie", movie);
                detailIntent.putExtra("listPage", true);
                detailIntent.putExtra("list", mListResult);
                mContext.startActivity(detailIntent);
            });
        }
    }
}
