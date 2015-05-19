package group8.com.application.UI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

import group8.com.application.Application.Session;
import group8.com.application.R;
import group8.com.application.UI.mainView.menuView;

public class ChartActivity extends Activity {

    Context context = menuView.getContext();
    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_chart);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title) ;

        // Used to update the Arraylist
        int speed = Session.getSpeedScore();
        int distraction = Session.getDriverDistractionLevelScore();
        int brake = Session.getBrakeScore();
        int fuel = Session.getFuelConsumptionScore();

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(speed, 0));
        entries.add(new BarEntry(distraction, 1));
        entries.add(new BarEntry(brake, 2));
        entries.add(new BarEntry(fuel, 3));

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

        // set description
        chart.setDescription("Your points");

        // add colors
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);

        // Add some animations
        chart.animateY(5000);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        // add a limit line
        LimitLine line = new LimitLine(50f, "Average");
        line.setLineWidth(4f);
        line.enableDashedLine(10f, 10f, 0f);
        line.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        line.setTextSize(10f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.addLimitLine(line);


        // Graph displayed for 20 seconds
        cdt = new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                
                finish ();

            }
        };

        //Start the timer
        cdt.start();
    }

}
