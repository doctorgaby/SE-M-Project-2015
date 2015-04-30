package group8.com.application.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.achartengine.GraphicalView;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.R;
import group8.com.application.UI.Graphs.DriverDistractionGraph;
import group8.com.application.UI.Graphs.FuelConsumptionGraph;
import group8.com.application.UI.Login.LoginView;


public class MainView extends Activity {


    // I added this block of code here since we need it here for alert methods in controller class to work
    public static Context mContext;
    public static Context getContext() {
        return mContext;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mContext= getBaseContext(); //  I also added a base context here....needed for methods to work

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        //checks if it's a new day
//        dailyMessage();

        //test code
        Controller.eventGetCustomToast(this, customLayout());

        Button graphBtn = (Button) findViewById(R.id.graphBtn);
        Button testMeasurementBtn = (Button) findViewById(R.id.testMeasurementsBtn);
        TextView userTxt = (TextView) findViewById(R.id.username);

        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsView.class);
                startActivityForResult(intent, 0);
            }
        });

        testMeasurementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TestGrading.class);
                startActivityForResult(intent, 0);
            }
        });
        // creates the new activity in the same view
        BarChart bar = new BarChart();
        GraphicalView bView = bar.getView(this);
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        layout.addView(bView);

        userTxt.setText(Session.getUserName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mainviewmenu_logout:
                //Does the
                if (Profile.getCurrentProfile() != null)
                    LoginManager.getInstance().logOut();
                SharedPreferences sp = PreferenceManager
                        .getDefaultSharedPreferences(MainView.this);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("username", "");
                edit.apply();
                Session.restart();

                //Goes back to the login view.
                Intent intent = new Intent(this, LoginView.class);
                this.startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Method for checking if
     * it's a new day, if it is
     * a new day, it saves the current
     * date and executes the
     * daily message
     */


    private void dailyMessage(){

        //get SharedPreference
        SharedPreferences prefs = getSharedPreferences(ConstantData.TAG_SAVEDAY, 0);
        long getNewDay = prefs.getLong(ConstantData.TAG_SAVEDAY, 0);

        //check if it's a new day
        if ((getNewDay + (24 * 60 * 60 * 1000)) < System.currentTimeMillis()) {

            // Save current timestamp for next Check
            getNewDay = System.currentTimeMillis();
            SharedPreferences.Editor editor = getSharedPreferences(ConstantData.TAG_SAVEDAY, 0).edit();
            editor.putLong(ConstantData.TAG_SAVEDAY, getNewDay);
            editor.commit();

            //execute daily message
            Controller.eventGetCustomToast(this, customLayout());
            Log.d("MyApp",getNewDay + "");
        }
    }

    private View customLayout(){

        LayoutInflater myInflator = getLayoutInflater();
        View myLayout = myInflator.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

        return myLayout;

    }

}

