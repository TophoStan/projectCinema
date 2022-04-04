package nl.avans.cinema.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.dataacces.ContentViewModel;
import nl.avans.cinema.domain.MovieList;
import nl.avans.cinema.ui.ListsActivity;
import nl.avans.cinema.ui.SingleListActivity;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<MovieList> mListList = new ArrayList<>();
    private Context mContext;
    private ContentViewModel mViewModel;
    private ListsActivity mListActivity;

    public ListAdapter(Context context, ContentViewModel viewModel, ListsActivity listActivity) {
        this.mContext = context;
        this.mViewModel = viewModel;
        this.mListActivity = listActivity;
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
    }

    @Override
    public int getItemCount() {
        if (mListList.isEmpty()) {
            return 0;
        }
        return mListList.size();
    }

    public void setLists(List<MovieList> movieLists) {
        this.mListList = movieLists;
        notifyDataSetChanged();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView listItemView;

        public ListViewHolder(View itemView) {
            super(itemView);
            this.listItemView = itemView.findViewById(R.id.list);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //TODO van api lijst ophalen
            mViewModel.getListFromUser(mListList.get(getAdapterPosition()).getId()).observe(mListActivity, listResult -> {
                Intent listIntent = new Intent(mContext, SingleListActivity.class);
                listIntent.putExtra("list", listResult);
                mContext.startActivity(listIntent);
            });
            Toast.makeText(mContext, "List: " + mListList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
        }
    }
}