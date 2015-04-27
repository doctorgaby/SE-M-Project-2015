package group8.com.application.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.achartengine.GraphicalView;

import group8.com.application.Application.Session;
import group8.com.application.R;
import group8.com.application.UI.Login.LoginView;


public class MainView extends Activity {
        int sp = 0;
        int dd = 0;
        int fc = 0;
        int bk = 0;
        static int max;

        private GraphicalView bView;
        private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        Button graphBtn = (Button) findViewById(R.id.graphBtn);
        Button testMeasurementBtn = (Button) findViewById(R.id.testMeasurementsBtn);
        Button updateBtn = (Button) findViewById(R.id.updateBtn);
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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

 //               repaintGraph();
                layout.removeView(bView);
                sp = 50+10;
                dd = 50+10;
                fc = 50+10;
                bk = 50+10;
                max = (sp + bk + dd + fc) / 2;
                doGraph(sp, dd,fc, bk);
                bView.repaint();
                bView.refreshDrawableState();
            }
        });
        // creates the new activity in the same view
        doGraph(sp, dd, fc, bk);
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

    public void doGraph(int speed, int fuelconsumption, int driverdistraction, int brake) {

        BarChart bar = new BarChart();
        bView = bar.getView(this, speed, fuelconsumption, driverdistraction, brake);
            layout = (LinearLayout) findViewById(R.id.chart);
            layout.addView(bView);

    }

    public void repaintGraph(){

        layout.removeView(bView);
        sp = Session.getSpeedScore();
        dd = Session.getDriverDistractionLevelScore();
        fc = Session.getFuelConsumptionScore();
        bk = Session.getBrakeScore();
        doGraph(sp, dd,fc, bk);
        max = (sp + bk + dd + fc) / 2;
        bView.repaint();
        bView.refreshDrawableState();

    }
}

