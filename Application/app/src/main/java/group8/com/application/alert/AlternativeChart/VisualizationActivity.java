/*
package group8.com.application.alert.AlternativeChart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

import group8.com.application.Application.Session;
import group8.com.application.R;
import group8.com.application.UI.MainView;


public class VisualizationActivity extends Activity {



    Context context = MainView.getContext();
    CountDownTimer cdt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualization);





        int speed = Session.getSpeedScore();
        int fuelconsumption = Session.getFuelConsumptionScore();
        int driverdistraction = Session.getDriverDistractionLevelScore();
        int brake = Session.getBrakeScore();




        // The code above is/ will be used to update the array list below whenever the activity is refreshed


        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(speed, 0));
        entries.add(new BarEntry(fuelconsumption, 1));
        entries.add(new BarEntry(driverdistraction, 2));
        entries.add(new BarEntry(brake, 3));


        BarDataSet dataset = new BarDataSet(entries, "Points Earned");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Speed");
        labels.add("Distraction");
        labels.add("Brakes ");
        labels.add("Fuel");


        BarChart chart = new BarChart(context);
        setContentView(chart);
        BarData data = new BarData(labels, dataset);
        chart.setData(data);


        chart.setDescription("Your points");

        dataset.setColors(ColorTemplate.JOYFUL_COLORS); // add colors

        chart.animateY(5000); // Add some animations



        //Timer to refresh activity after every 5 minutes inorder to update results
        cdt = new CountDownTimer(15000, 1000) {  //15 seconds for demonstration purposes

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                //Intent nextScreen = new Intent(getApplicationContext(), mainView.class);

                finish ();
                startActivity(getIntent());

            }
        };

        //Start the timer
        cdt.start();
    }

}*/
