package nl.avans.cinema.ui.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.dataacces.api.calls.AddItemRequest;
import nl.avans.cinema.dataacces.api.calls.ListAddItems;
import nl.avans.cinema.domain.Movie;
import nl.avans.cinema.domain.MovieList;
import nl.avans.cinema.ui.AddToListPopUp;
import nl.avans.cinema.ui.DetailActivity;
import nl.avans.cinema.ui.ListsActivity;
import nl.avans.cinema.ui.SingleListActivity;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<MovieList> mListList = new ArrayList<>();
    private Context mContext;
    private ContentViewModel mViewModel;
    private ListsActivity mListActivity;
    private AddToListPopUp mAddToListPopUp;
    private boolean forAddToList = false;
    private Movie movie;

    public ListAdapter(Context context, ContentViewModel viewModel, ListsActivity listActivity) {
        this.mContext = context;
        this.mViewModel = viewModel;
        this.mListActivity = listActivity;
    }

    public ListAdapter(Context context, ContentViewModel viewModel, AddToListPopUp addToListPopUp) {
        this.mContext = context;
        this.mViewModel = viewModel;
        this.mAddToListPopUp = addToListPopUp;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mItemView = inflater.inflate(R.layout.single_list_item, parent, false);
        return new ListViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ListViewHolder holder, int position) {
        MovieList currentList = mListList.get(position);
        holder.listItemView.setText(currentList.getName());
        holder.listSize.setText(currentList.getTotal_results() + " movies");
    }

    @Override
    public int getItemCount() {
        if (mListList.isEmpty()) {
            return 0;
        }
        return mListList.size();
    }

    public void clear(){
        mListList.clear();
    }

    public void setLists(List<MovieList> movieLists) {
        this.mListList = movieLists;
        notifyDataSetChanged();
    }

    public void onAddToList(boolean onTheList, Movie movie) {
        this.forAddToList = onTheList;
        this.movie = movie;
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView listItemView;
        private TextView listSize;

        public ListViewHolder(View itemView) {
            super(itemView);
            this.listItemView = itemView.findViewById(R.id.listTitle);
            this.listSize = itemView.findViewById(R.id.listSize);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (forAddToList) {
                AddItemRequest addItemRequest = new AddItemRequest("movie", movie.getId());
                List<AddItemRequest> addItemRequestList = new ArrayList<>();
                addItemRequestList.add(addItemRequest);
                ListAddItems listAddItems = new ListAddItems();
                listAddItems.setItems(addItemRequestList);
                MovieList list = mListList.get(getAdapterPosition());
                mViewModel.addItemsToList(list.getId(), mViewModel.getUsers().getAccess_token(), listAddItems).observe(mAddToListPopUp, addItemResult -> {
                    if (addItemResult.isSuccess()) {
                        Toast.makeText(mContext, movie.getTitle() + " has been added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, movie.getTitle() + " has not been added", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                mViewModel.getListById(mListList.get(getAdapterPosition()).getId()).observe(mListActivity, listResult -> {
                    Intent listIntent = new Intent(mContext, SingleListActivity.class);
                    listIntent.putExtra("list", listResult);
                    mContext.startActivity(listIntent);
                });
                Toast.makeText(mContext, "List: " + mListList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}