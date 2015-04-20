package group8.com.application.UI;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import group8.com.application.Application.Session;
import group8.com.application.R;
import group8.com.application.UI.Login.LoginView;


public class MainView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

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

        userTxt.setText(Session.getUserName());

        int speed = 50;
        int fuelconsumption = 40;
        int driverdistraction = 30;
        int brake = 10;

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        XYSeries sp = new XYSeries("Speed");
        XYSeries fc = new XYSeries("Fuel Consumption");
        XYSeries dd = new XYSeries("Driver Distraction");
        XYSeries bk = new XYSeries("Brake");

        sp.add(0, speed);
        fc.add(1, fuelconsumption);
        dd.add(2, driverdistraction);
        bk.add(3, brake);

        dataset.addSeries(sp);
        dataset.addSeries(fc);
        dataset.addSeries(dd);
        dataset.addSeries(bk);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setChartTitle("Your Points");
        // mRenderer.setXTitle("");
        // mRenderer.setYTitle("");
        mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.LTGRAY);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setZoomEnabled(true);
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setBarSpacing(0.0f);
//      mRenderer.setMargins(new int[] {20, 30, 15, 0});
        mRenderer.setAxisTitleTextSize(16);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.addXTextLabel(1, "Speed");
        mRenderer.addXTextLabel(2, "Fuel Consumption");
        mRenderer.addXTextLabel(3, "Driver Distraction");
        mRenderer.addXTextLabel(4, "Brake");
        mRenderer.setBarWidth(200);
//      mRenderer.setXAxisMax(9);
        mRenderer.setXAxisMin(0);
        mRenderer.setYAxisMin(0);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0, Color.BLACK);
        mRenderer.setXLabels(0);


        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.parseColor("#00AA00"));
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesTextSize(150);

        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setColor(Color.parseColor("#666600"));
        renderer2.setDisplayChartValues(true);
        renderer2.setChartValuesTextSize(70);

        XYSeriesRenderer renderer3 = new XYSeriesRenderer();
        renderer3.setColor(Color.parseColor("#FF0000"));
        renderer3.setDisplayChartValues(true);
        renderer3.setChartValuesTextSize(100);

        XYSeriesRenderer renderer4 = new XYSeriesRenderer();
        renderer4.setColor(Color.parseColor("#0000FF"));
        renderer4.setDisplayChartValues(true);
        renderer4.setChartValuesTextSize(50);

        mRenderer.addSeriesRenderer(renderer);
        mRenderer.addSeriesRenderer(renderer2);
        mRenderer.addSeriesRenderer(renderer3);
        mRenderer.addSeriesRenderer(renderer4);

       // Intent intent = ChartFactory.getBarChartIntent(context, dataset, mRenderer, Type.STACKED);
        //return intent;
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
       // public Intent getIntent(Context context) {
//fagfsa
    }

