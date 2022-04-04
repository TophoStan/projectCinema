package nl.avans.cinema.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import nl.avans.cinema.R;

public class ListAdapter extends RecyclerView.Adapter <ListAdapter.ListViewHolder> {

    private final LinkedList<String> mListList;
    private final LayoutInflater mInflater;

    class ListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView listItemView;
        final ListAdapter mAdapter;


        public ListViewHolder(View itemView, ListAdapter adapter) {
            super(itemView);
            listItemView = itemView.findViewById(R.id.list);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int mPosition = getLayoutPosition();


            String element = mListList.get(mPosition);
            mAdapter.notifyDataSetChanged();
        }
    }

    public ListAdapter(Context context, LinkedList<String> listList) {
        mInflater = LayoutInflater.from(context);
        this.mListList = listList;
    }


    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.single_list_item, parent, false);
        return new ListViewHolder(mItemView, this);
    }


    @Override
    public void onBindViewHolder(ListAdapter.ListViewHolder holder,
                                 int position) {
        String mCurrent = mListList.get(position);
        holder.listItemView.setText(mCurrent);
    }


    @Override
    public int getItemCount() {
        return mListList.size();
    }
}