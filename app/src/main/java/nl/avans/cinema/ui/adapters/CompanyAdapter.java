package nl.avans.cinema.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nl.avans.cinema.R;
import nl.avans.cinema.domain.Company;
import nl.avans.cinema.domain.Movie;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {

    private List<Company> companies = new ArrayList<>();
    private Context mContext;

    public CompanyAdapter(Context context) {
        this.mContext = context;
    }

    public CompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.single_company_item, parent, false);
        return new CompanyHolder(view);
    }

    public void onBindViewHolder(CompanyHolder holder, int position) {
        Company company = companies.get(position);

        if (company.getName() != null) {
            holder.companyTitle.setText(company.getName());
        }
        if (company.getLogo_path() == null) {
            holder.companyLogo.setImageResource(R.drawable.ic_company);
        } else {
            Glide.with(mContext).load("https://image.tmdb.org/t/p/original" + company.getLogo_path()).into(holder.companyLogo);
        }

    }

    public int getItemCount() {
        return companies.size();
    }

    public void setCompanies(List<Company> companyList) {
        this.companies = companyList;
        notifyDataSetChanged();
    }


    public class CompanyHolder extends RecyclerView.ViewHolder {

        private TextView companyTitle;
        private ImageView companyLogo;

        public CompanyHolder(View itemView) {
            super(itemView);
            this.companyTitle = itemView.findViewById(R.id.company_name);
            this.companyLogo = itemView.findViewById(R.id.company_logo);
        }
    }

}
