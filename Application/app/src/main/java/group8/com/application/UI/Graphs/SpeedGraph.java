package group8.com.application.UI.Graphs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.DataList;
import group8.com.application.R;
import group8.com.application.UI.ResultsView;

/**
 * Created by Kristiyan on 3/16/2015.
 */
public class SpeedGraph extends Activity {

    private XYPlot plot;
    private DataList data = Controller.eventGetMeasurements();
    int xMin, xMax, xRange, yMin, yMax, yRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_display);

        //build a default graph from session
        buildSpeedGraph(data);

        //Listeners for filter buttons
        Button currBtn = (Button) findViewById(R.id.currBtn);
        currBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildSpeedGraph(data);
                plot.redraw();
            }
        });

        Button weekBtn = (Button) findViewById(R.id.weekBtn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildSpeedGraph(weekFill());
                plot.redraw();
            }
        });

        Button monthBtn = (Button) findViewById(R.id.monthBtn);
        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildSpeedGraph(monthFill());
                plot.redraw();
            }
        });
    }

    //builder for the speed graph
    private void buildSpeedGraph(DataList data){

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);
        plot.getRangeLabelWidget().setText("Speed (km/h)");
        plot.getTitleWidget().setText("Speed per Measurement");

        //Plotting Variables
        xMin = 0;
        xMax = data.getMaxTime();
        xRange = xMax / 5;
        yMin = 0;
        yMax = 300;
        yRange = (yMax - yMin) / 15;

        //Domain: X-Axis
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, xRange);
        plot.setDomainBoundaries(xMin, xMax, BoundaryMode.FIXED);

        //Range: Y-Axis
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, yRange);
        plot.setRangeBoundaries(yMin, yMax, BoundaryMode.FIXED);


        //Initiate Series to Draw
        XYSeries speedSeries = new SimpleXYSeries(
                data.getPlottableSpeed(),
                SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                "Speed");

        //Initiate the formatters for the lines.
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter speedFormat = new LineAndPointFormatter();
        speedFormat.setPointLabelFormatter(new PointLabelFormatter());
        speedFormat.configure(getApplicationContext(),
                R.xml.lpf_speed);


        //Add series to the plot with the correct formats.
        plot.clear();
        plot.addSeries(speedSeries, speedFormat);

    }

    //test code for filter
    private DataList weekFill(){

        DataList data = new DataList("w");
        data.setSpeed(1, 40);
        data.setSpeed(2, 35);
        data.setSpeed(3, 80);
        data.setSpeed(4, 120);
        data.setSpeed(5, 160);
        data.setSpeed(6, 90);
        return data;
    }

    //test code for filter
    private DataList monthFill(){

        DataList data = new DataList("w");
        data.setSpeed(1, 60);
        data.setSpeed(2, 90);
        data.setSpeed(3, 70);
        data.setSpeed(4, 140);
        data.setSpeed(5, 70);
        data.setSpeed(6, 40);
        return data;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.menuSpeedOption);
        //depending on your conditions, either enable/disable
        item.setEnabled(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menuPointsOption:
                Intent intentP = new Intent(this, ResultsView.class);
                this.startActivity(intentP);
                return true;
            case R.id.menuFuel_consumptionOption:
                Intent intentFC = new Intent(this, FuelConsumptionGraph.class);
                this.startActivity(intentFC);
                return true;
            case R.id.menuDriverDistractionOption:
                Intent intentDDL = new Intent(this, DriverDistractionGraph.class);
                this.startActivity(intentDDL);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
