package group8.com.application.UI;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.Model.DataList;
import group8.com.application.R;

import static group8.com.application.R.id;
import static group8.com.application.R.layout;

public class ResultsView extends Activity {

    //Tags to handle the time chosen.
    private String TAG_NOW = "now";
    private String TAG_WEEK = "week";
    private String TAG_MONTH = "month";

    //Message if no user is available.
    private String TAG_NODATAMSG = "No data yet. Start Using the app.";

    //Chart to show the information.
    private LineChart mchart;

    //Variables to keep track of what is clicked. They are initialized with default values.
    private String time = TAG_NOW;
    private String type = ConstantData.TAG_SPEED;

    //Data list that holds the data of the user currently logged in.
    DataList data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.results_display);

        customizeChart();
        executionMenu();

        //Listeners for filter buttons
        Button currBtn = (Button) findViewById(id.currBtn);
        currBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = TAG_NOW;
                executionMenu();
            }
        });

        Button weekBtn = (Button) findViewById(id.weekBtn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = TAG_WEEK;
                executionMenu ();
            }
        });

        Button monthBtn = (Button) findViewById(id.monthBtn);
        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = TAG_MONTH;
                executionMenu ();
            }
        });

        Button speedBtn = (Button) findViewById(id.speedBtn);
        speedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ConstantData.TAG_SPEED;
                executionMenu ();
            }
        });
        Button brakeBtn = (Button) findViewById(id.brakeBtn);
        brakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ConstantData.TAG_BRAKE;
                executionMenu ();
            }
        });
        Button fuelBtn = (Button) findViewById(id.fuelBtn);
        fuelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ConstantData.TAG_FUEL;
                executionMenu ();
            }
        });
        Button distractionBtn = (Button) findViewById(id.distractionBtn);
        distractionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ConstantData.TAG_DISTRACTION;
                executionMenu ();
            }
        });

    }

    //Executes the calling of methods accordingly to the choices clicked.
    private void executionMenu () {
        mchart.clear();
        if (time.equals(TAG_WEEK)) {
            data = Controller.eventGetFilteredPoints((int) ((System.currentTimeMillis() / 1000) - 604800), (int) (System.currentTimeMillis() / 1000));
        } else if (time.equals(TAG_MONTH)) {
            data = Controller.eventGetFilteredPoints((int) ((System.currentTimeMillis() / 1000) - 2628000), (int) (System.currentTimeMillis() / 1000));
        } else {
            data = Session.currentPoints;
        }
        switch (type) {
            case ConstantData.TAG_BRAKE:
                addBrakeData();
                break;
            case ConstantData.TAG_FUEL:
                addFuelData();
                break;
            case ConstantData.TAG_DISTRACTION:
                addDistractionData();
                break;
            default:
                addSpeedData();
                break;
        }
    }

    //Fills the chart with the DataList at hand.
    private void customizeChart(){
        //Customize the chart
        LinearLayout mainLayout = (LinearLayout) findViewById(id.graphLayout);
        mchart = new LineChart(this);
        mainLayout.addView(mchart);
        mchart.setDescription("");
        mchart.setNoDataTextDescription("No Data for the Moment.");

        mchart.setDragEnabled(false);
        mchart.setClickable(false);
        mchart.setScaleEnabled(false);
        mchart.setDrawGridBackground(false);
        mchart.setPinchZoom(false);
        mchart.setTouchEnabled(false);

        mchart.setBackgroundColor(getResources().getColor(R.color.menuViewCenter));

        Legend leg = mchart.getLegend();
        leg.setForm(Legend.LegendForm.LINE);
        leg.setTextColor(Color.WHITE);
        leg.setTextSize(15f);

        XAxis xax = mchart.getXAxis();
        xax.setTextColor(Color.WHITE);
        xax.setDrawGridLines(true);
        xax.setAvoidFirstLastClipping(true);
        xax.setGridColor(Color.parseColor("#ffffff"));
        xax.setGridLineWidth(1f);
        xax.setAxisLineColor(Color.parseColor("#ffffff"));
        xax.setAxisLineWidth(4f);
        xax.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yax = mchart.getAxisLeft();
        yax.setTextColor(Color.WHITE);
        yax.setAxisMaxValue(110);
        yax.setDrawGridLines(true);
        yax.setGridColor(Color.parseColor("#ffffff"));
        yax.setGridLineWidth(1f);
        yax.setAxisLineColor(Color.parseColor("#ffffff"));
        yax.setAxisLineWidth(4f);

        YAxis yaxR = mchart.getAxisRight();
        yaxR.setEnabled(false);
    }

    //*******Helper Methods to load the desired values into the variables used by the chart filler.
    //Includes also the design methods for each type of value being shown in the graph.


    private void addSpeedData () {

        HashMap<String,ArrayList> map = data.getPlottableSpeed(time);
        ArrayList<String> xVals = map.get("xVals");
        ArrayList<Entry> yVals = map.get("yVals");
        LineDataSet dataset = createSpeedSet(yVals);
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        LineData data;
        if (!xVals.isEmpty())
             data =  new LineData(xVals,dataSets);
        else {
            data = new LineData();
            Toast.makeText(this, TAG_NODATAMSG, Toast.LENGTH_LONG).show();
        }
        mchart.setData(data);
    }

    private LineDataSet createSpeedSet (ArrayList<Entry> yVals) {
        LineDataSet set = new LineDataSet(yVals, "Speed");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setColor(getResources().getColor(R.color.speedChart));
        set.setCircleColor(getResources().getColor(R.color.speedChart));
        set.setLineWidth(3f);
        set.setCircleSize(0.2f);
        set.setFillColor(getResources().getColor(R.color.speedChart));
        set.setDrawValues(false);
        return set;
    }

    private void addBrakeData () {
        HashMap<String,ArrayList> map = data.getPlottableBrake(time);
        ArrayList<String> xVals = map.get("xVals");
        ArrayList<Entry> yVals = map.get("yVals");
        LineDataSet dataset = createBreakSet(yVals);
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        LineData data;
        if (!xVals.isEmpty())
            data =  new LineData(xVals,dataSets);
        else {
            data = new LineData();
            Toast.makeText(this, TAG_NODATAMSG, Toast.LENGTH_LONG).show();
        }
        mchart.setData(data);
    }

    private LineDataSet createBreakSet (ArrayList<Entry> yVals) {
        LineDataSet set = new LineDataSet(yVals, "Brake");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setColor(getResources().getColor(R.color.brakeChart));
        set.setCircleColor(getResources().getColor(R.color.brakeChart));
        set.setLineWidth(3f);
        set.setCircleSize(0.2f);
        set.setFillColor(getResources().getColor(R.color.brakeChart));
        set.setDrawValues(false);
        return set;
    }

    private void addFuelData () {
        HashMap<String,ArrayList> map = data.getPlottableFuelConsumption(time);
        ArrayList<String> xVals = map.get("xVals");
        ArrayList<Entry> yVals = map.get("yVals");
        LineDataSet dataset = createFuelSet(yVals);
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        LineData data;
        if (!xVals.isEmpty())
            data =  new LineData(xVals,dataSets);
        else {
            data = new LineData();
            Toast.makeText(this, TAG_NODATAMSG, Toast.LENGTH_LONG).show();
        }
        mchart.setData(data);
    }

    private LineDataSet createFuelSet (ArrayList<Entry> yVals) {
        LineDataSet set = new LineDataSet(yVals, "Fuel");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setColor(getResources().getColor(R.color.fuelChart));
        set.setCircleColor(getResources().getColor(R.color.fuelChart));
        set.setLineWidth(3f);
        set.setCircleSize(0.2f);
        set.setFillColor(getResources().getColor(R.color.fuelChart));
        set.setDrawValues(false);
        return set;
    }

    private void addDistractionData () {
        HashMap<String,ArrayList> map = data.getPlottableDriverDistraction(time);
        ArrayList<String> xVals = map.get("xVals");
        ArrayList<Entry> yVals = map.get("yVals");
        LineDataSet dataset = createDistractionSet(yVals);
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        LineData data;
        if (!xVals.isEmpty())
            data =  new LineData(xVals,dataSets);
        else {
            data = new LineData();
            Toast.makeText(this, TAG_NODATAMSG, Toast.LENGTH_LONG).show();
        }
        mchart.setData(data);
    }

    private LineDataSet createDistractionSet (ArrayList<Entry> yVals) {
        LineDataSet set = new LineDataSet(yVals, "Distraction");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setColor(getResources().getColor(R.color.distractionChart));
        set.setCircleColor(getResources().getColor(R.color.distractionChart));
        set.setLineWidth(3f);
        set.setCircleSize(0.2f);
        set.setFillColor(getResources().getColor(R.color.distractionChart));
        set.setDrawValues(false);
        return set;
    }
}
