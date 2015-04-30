package group8.com.application.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.achartengine.GraphicalView;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.R;
import group8.com.application.UI.Login.LoginView;



public class MainView extends Activity {

    public static Context mContext;
    public static Context getContext(){

        return mContext;
    }

    int sp = 0;
    int dd = 0;
    int fc = 0;
    int bk = 0;
    static int max;

    private TextView userTxt;
    private GraphicalView bView;
    private LinearLayout layout;
    private Button graphBtn;
    private Button updateBtn;

    /*Hampus*/
    private MainView mainView = this;
    private boolean shouldUpdateGraphs;

    private Button startButton;
    private Button stopButton;
    /*END Hampus*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseContext();
        setContentView(R.layout.main_display);

        graphBtn = (Button) findViewById(R.id.graphBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);

        /*Hampus*/
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        stopButton.setVisibility(View.INVISIBLE);
        userTxt = (TextView) findViewById(R.id.username);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Controller.isReceivingSignal()) {

                    startButton.setClickable(true);

                    startButton.setVisibility(View.INVISIBLE);
                    stopButton.setVisibility(View.VISIBLE);
                    graphBtn.setVisibility(View.INVISIBLE);
                    updateBtn.setVisibility(View.INVISIBLE);

                    // creates the new activity in the same view
                    doGraph(sp, dd, fc, bk);
                    userTxt.setText(Session.getUserName());

                    Controller.startGrading();
                    startTask();

                } else {
                    startButton.setClickable(false);
                    Toast.makeText(MainView.this, "Can't ", Toast.LENGTH_LONG).show();
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
                graphBtn.setVisibility(View.VISIBLE);
                updateBtn.setVisibility(View.VISIBLE);

                stopTask();
                Controller.stopGrading();

            }
        });
        /*END Hampus*/


;

        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultsView.class);
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
        doGraph(sp,fc,dd, bk);
        max = (sp + bk + dd + fc) / 2;
        bView.repaint();
        bView.refreshDrawableState();

    }

    private void startTask() {

        shouldUpdateGraphs = true;

         new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                while(shouldUpdateGraphs) {

                    layout.post(new Runnable() {
                        @Override
                        public void run() {
                            repaintGraph();
                        }
                    });

                }
                return null;
            }
        }.execute();

    }

    private void stopTask() {

        shouldUpdateGraphs = false;

    }

}