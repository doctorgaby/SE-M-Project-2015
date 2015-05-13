package group8.com.application.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group8.com.application.Application.Controller;
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
    Context context = this;
    private boolean lvBusy = false;

    /**
     * sets the view and populates
     * it with medals
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medal_display);

        // populate data
        medals = new ArrayList();
        medals.add(new Medal("Master at braking", R.drawable.medal_brakes, Controller.getUpdatedStatus("Brake Medal")));
        medals.add(new Medal("Master at focus", R.drawable.medal_distraction, Controller.getUpdatedStatus("Distraction Medal")));
        medals.add(new Medal("Master at speed", R.drawable.medal_speed,  Controller.getUpdatedStatus("Speed Medal")));
        medals.add(new Medal("Master at fuel upkeep", R.drawable.medal_fuel, Controller.getUpdatedStatus("Fuel Medal")));

        gvMedals = (GridView) findViewById( R.id.grid_medals);
        adapterMedals = new MedalAdapter(this, medals);
        gvMedals.setAdapter(adapterMedals);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.medals_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        //clears and repaints before every change
        switch (item.getItemId()) {

            case R.id.main_view_back:
                Intent intent = new Intent(context,MainView.class);
                startActivityForResult(intent, 0);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
