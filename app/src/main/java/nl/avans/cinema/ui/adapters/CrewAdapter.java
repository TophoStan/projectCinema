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
import nl.avans.cinema.domain.Crew;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewHolder> {

    private List<Crew> crewList = new ArrayList<>();
    private Context mContext;

    public CrewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CrewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.single_crew_item, parent, false);
        return new CrewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewHolder holder, int position) {
        Crew crew = crewList.get(position);
        if (crew.getProfile_path() != null) {
            Glide.with(mContext).load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + crew.getProfile_path()).into(holder.profile);
        }
        holder.name.setText(crew.getName());
        holder.character.setText(crew.getJob());
    }

    @Override
    public int getItemCount() {
        if (crewList.isEmpty()) {
            return 0;
        }
        return crewList.size();
    }

    public ArrayList<Crew> removeDuplicate() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Crew> crewMembersToRemove = new ArrayList<>();
        for (Crew crew : this.crewList) {
            if (!names.contains(crew.getName()) && crew.getProfile_path() != null) {
                names.add(crew.getName());
            } else {
                crewMembersToRemove.add(crew);
            }
        }
        return crewMembersToRemove;
    }

    public void setCrewList(List<Crew> crews) {
        this.crewList = crews;
        this.crewList.removeAll(removeDuplicate());
        notifyDataSetChanged();
    }

    public class CrewHolder extends RecyclerView.ViewHolder {

        private TextView name, character, department;
        private ImageView profile;

        public CrewHolder(@NonNull View itemView) {
            super(itemView);
            this.character = itemView.findViewById(R.id.character_name);
            this.name = itemView.findViewById(R.id.crew_person_name);
            this.profile = itemView.findViewById(R.id.crew_person_image);
        }
    }
}
