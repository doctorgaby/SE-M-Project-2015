package group8.com.application.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import group8.com.application.Application.MedalAdapter;
import group8.com.application.Model.Medal;
import group8.com.application.R;

/**
 * Created by Kristiyan on 5/12/2015.
 */
public class MedalsView extends Activity implements AbsListView.OnScrollListener{

    List medals;
    GridView gvMedals = null;
    MedalAdapter adapterMedals;

    private boolean lvBusy = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medal_display);

        // populate data
        medals = new ArrayList();
        medals.add(new Medal("Master at braking", R.drawable.medal_brakes));
        medals.add(new Medal("Master at focus",R.drawable.medal_distraction));
        medals.add(new Medal("Master at speed",R.drawable.medal_speed));
        medals.add(new Medal("Master at fuel upkeep",R.drawable.medal_fuel));

        gvMedals = (GridView) findViewById( R.id.grid_medals);
        adapterMedals = new MedalAdapter(this, medals);
        gvMedals.setAdapter(adapterMedals);

    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                lvBusy = false;
                adapterMedals.notifyDataSetChanged();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                lvBusy = true;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                lvBusy = true;
                break;
        }
    }
    public boolean isLvBusy(){
        return lvBusy;
    }
}
