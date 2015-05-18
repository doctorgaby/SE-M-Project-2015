package group8.com.application.UI;

import android.app.Activity;
import android.app.Dialog;
import android.app.usage.ConfigurationStats;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group8.com.application.Application.Controller;
import group8.com.application.Application.MedalAdapter;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
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
        medals.add(new Medal("Master at braking", R.drawable.medal_brakes, medalStatus(ConstantData.medalID[0])));
        medals.add(new Medal("Master at focus", R.drawable.medal_distraction, medalStatus(ConstantData.medalID[1])));
        medals.add(new Medal("Master at speed", R.drawable.medal_speed, medalStatus(ConstantData.medalID[2])));
        medals.add(new Medal("Master at fuel upkeep", R.drawable.medal_fuel, medalStatus(ConstantData.medalID[3])));

        gvMedals = (GridView) findViewById(R.id.grid_medals);
        adapterMedals = new MedalAdapter(this, medals);
        gvMedals.setAdapter(adapterMedals);

        //On click listener, handles item clicks on the grid view
        gvMedals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                showDialog(adapterMedals.getItem(position).getTitle(), adapterMedals.getItem(position).isAchieved);
            }
        });

    }

    private void showDialog(String title, boolean state){

        String [] name = {"braking", "distraction", "speed", "fuel consumption"};

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(title);

        final TextView editText = (TextView)dialog.findViewById(R.id.dialog_text);

        for (int i = 0; i <= 3;i++) {
            if (title == ConstantData.medalName[i])
                if (state) {
                    editText.setText("You have unlocked this medal!");
                } else {
                    editText.setText("Score 100 points in " + name[i] + " to unlock this medal");
                }
        }

        Button save = (Button)dialog.findViewById(R.id.ok_dialog);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    public boolean medalStatus(String s){

        SharedPreferences.Editor editor = getSharedPreferences("Save_Medal_Data", 0).edit();
        SharedPreferences prefs = getSharedPreferences("Save_Medal_Data", 0);

        if(Controller.getUpdatedStatus(s) == true) {
            editor.putBoolean(Session.getUserName() + s, true);
            editor.apply();
        }

        boolean saveMedalStatus = prefs.getBoolean(Session.getUserName() + s, false);

        if (saveMedalStatus != true){
            return false;
        } else {
            return saveMedalStatus;
        }

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