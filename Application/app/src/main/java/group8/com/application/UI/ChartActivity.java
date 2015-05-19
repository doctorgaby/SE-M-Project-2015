package group8.com.application.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

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


    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_chart);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title) ;

        setUpChart();
        addData();

        Button backBtn = (Button) findViewById(R.id.backGraphBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graphsInt = new Intent(ChartActivity.this, menuView.class);
                startActivity(graphsInt);
                finish();
            }
        });
    }

    private void setUpChart () {
        LinearLayout layout = (LinearLayout) findViewById (R.id.barChartLayout);
        chart = new BarChart(this);
        layout.addView(chart);
        // Add some animations
        chart.animateY(5000);
        // enable touch gestures
        chart.setTouchEnabled(true);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        // set description
        chart.setDescription("Your points");
        // add a limit line
        LimitLine line = new LimitLine(50f, "Avg");
        line.setLineWidth(4f);
        line.enableDashedLine(10f, 10f, 0f);
        line.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        line.setTextSize(8f);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.addLimitLine(line);
    }

    private void addData() {
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
        // add colors
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        // change text size
        dataset.setValueTextSize(10f);
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Speed");
        labels.add("Distraction");
        labels.add("Brakes ");
        labels.add("Fuel");
        BarData data = new BarData(labels, dataset);
        chart.setData(data);
    }

}
