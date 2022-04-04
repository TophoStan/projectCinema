package nl.avans.cinema.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.domain.Cast;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

    private List<Cast> castList = new ArrayList<>();
    private Context mContext;

    public CastAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.single_cast_item, parent, false);
        return new CastAdapter.CastHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastHolder holder, int position) {
        Cast cast = castList.get(position);
        holder.title.setText(cast.getKnown_for_department());
        holder.name.setText(cast.getName());
        Glide.with(mContext).load("https://image.tmdb.org/t/p/original" + cast.getProfile_path()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
    public void setCastList(List<Cast> list){
        List<Cast> castListToShow =  new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            castListToShow.add(list.get(i));
        }
        castList = castListToShow;
        notifyDataSetChanged();
    }

    public class CastHolder extends RecyclerView.ViewHolder {

        private TextView title, name;
        private ImageView image;

        public CastHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cast_person_title);
            name= itemView.findViewById(R.id.cast_person_name);
            image = itemView.findViewById(R.id.cast_person_image);
        }
    }

}
