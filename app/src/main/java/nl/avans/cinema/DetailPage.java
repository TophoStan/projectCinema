package nl.avans.cinema;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailPage extends Activity {

    private RecyclerView castRecyclerView;
    private RecyclerView crewRecyclerView;
    private RecyclerView alternateRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        /*Cast RecyclerView*/
        castRecyclerView = findViewById(R.id.castRV);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        castRecyclerView.setLayoutManager(layoutManager);

        /*Crew RecyclerView*/
        crewRecyclerView = findViewById(R.id.crewRV);
        GridLayoutManager crewLayoutManager = new GridLayoutManager(this, 2);
        crewRecyclerView.setLayoutManager(crewLayoutManager);

        /*Alternative Titles RecyclerView*/

        //alternateRecyclerView = findViewById(R.id.)
        //GridLayoutManager threeColumnLayoutManager = new GridLayoutManager(this, 3);
        //alternateRecyclerView.setLayoutManager(threeColumnLayoutManager);
    }
}
