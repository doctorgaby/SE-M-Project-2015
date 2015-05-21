package group8.com.application.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.R;
import group8.com.application.UI.mainView.menuView;

public class ChartActivity extends Activity {


    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chart);


        setUpChart();
        addData();

        Button backBtn = (Button) findViewById(R.id.backGraphBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuInt = new Intent(ChartActivity.this, menuView.class);
                startActivity(menuInt);
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

        chart.setTouchEnabled(false);
        chart.setDrawGridBackground(false);
        chart.getAxisLeft().setGridColor(Color.parseColor("#ffffff"));
        chart.getAxisLeft().setGridLineWidth(1f);
        chart.getAxisLeft().setAxisLineColor(Color.parseColor("#ffffff"));
        chart.getAxisLeft().setAxisLineWidth(4f);
        //chart.getAxisLeft().setInverted(true);

        chart.getXAxis().setGridColor(getResources().getColor(R.color.menuViewCenter));
        chart.getXAxis().setGridLineWidth(2f);
        chart.getXAxis().setAxisLineColor(Color.parseColor("#ffffff"));
        chart.getXAxis().setAxisLineWidth(4f);
        chart.setBackgroundColor(getResources().getColor(R.color.menuViewCenter));
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //chart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
        // enable scaling and dragging
        //chart.setDragEnabled(true);
        //chart.setScaleEnabled(true);
        //chart.setPinchZoom(true);
        // set description
        chart.setDescription("Your points");
        chart.getAxisLeft().setAxisMaxValue(110);
        chart.getAxisRight().setEnabled(false);
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
        int speed;
        int distraction;
        int brake;
        int fuel;
        if (Session.isPaused()) {
            speed = Session.getSpeedScore();
            distraction = Session.getDriverDistractionLevelScore();
            brake = Session.getBrakeScore();
            fuel = Session.getFuelConsumptionScore();
            if (Session.doFinish) {
                Controller.finishGrading(true);
            }
        } else {
            HashMap<String,Integer>  list = Controller.eventGetFinalPoints();
            speed = list.get(ConstantData.TAG_SPEED);
            brake = list.get(ConstantData.TAG_BRAKE);
            distraction = list.get(ConstantData.TAG_DISTRACTION);
            fuel = list.get(ConstantData.TAG_FUEL);
        }


        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(speed, 0));
        entries.add(new BarEntry(brake, 1));
        entries.add(new BarEntry(fuel, 2));
        entries.add(new BarEntry(distraction, 3));

        BarDataSet dataset = new BarDataSet(entries, "Points Earned");
        // add colors
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        // change text size
        dataset.setValueTextSize(15f);
        dataset.setValueTypeface(Typeface.DEFAULT_BOLD);
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Speed");
        labels.add("Brakes ");
        labels.add("Fuel");
        labels.add("Distraction");
        BarData data = new BarData(labels, dataset);
        chart.setData(data);
    }

}
