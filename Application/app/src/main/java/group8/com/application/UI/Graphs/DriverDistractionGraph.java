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
import group8.com.application.Model.DataList;
import group8.com.application.R;
import group8.com.application.UI.ResultsView;

/**
 * Created by Kristiyan on 3/16/2015.
 */
public class DriverDistractionGraph extends Activity {


    private XYPlot plot;
    protected DataList data;
    int xMin, xMax, xRange, yMin, yMax, yRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_display);


        //build a default graph from session
        buildDdlGraph(data = Controller.eventGetMeasurements());

        //Listeners for filter buttons
        Button currBtn = (Button) findViewById(R.id.currBtn);
        currBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildDdlGraph(data = Controller.eventGetMeasurements());
                plot.redraw();
            }
        });

        Button weekBtn = (Button) findViewById(R.id.weekBtn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildDdlGraph(data = Controller.eventGetFilteredMeasurements( (int) System.currentTimeMillis() / 1000, (int) System.currentTimeMillis() / 1000 - 60480));
                plot.redraw();
            }
        });

        Button monthBtn = (Button) findViewById(R.id.monthBtn);
        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildDdlGraph(data = Controller.eventGetFilteredMeasurements( (int) System.currentTimeMillis() / 1000, (int) System.currentTimeMillis() / 1000 - 262800));
                plot.redraw();
            }
        });
    }

    private void buildDdlGraph(DataList data){

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);
        plot.getRangeLabelWidget().setText("Driver Distraction");
        plot.getTitleWidget().setText("Distraction per Measurement");

        //Plotting Variables
        xMin = 0;
        xMax = data.getMaxTime();
        xRange = xMax / 5;
        yMin = 0;
        yMax = 4;
        yRange = yMax;

        //Domain: X-Axis
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, xRange);
        plot.setDomainBoundaries(xMin, xMax, BoundaryMode.FIXED);

        //Range: Y-Axis
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, yRange);
        plot.setRangeBoundaries(yMin, yMax, BoundaryMode.FIXED);

        XYSeries driverDistractionSeries = new SimpleXYSeries(
                data.getPlottableDriverDistraction(),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Driver Distraction");

        //Initiate the formatters for the lines.
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:

        LineAndPointFormatter driverDistractionFormat = new LineAndPointFormatter();
        driverDistractionFormat.setPointLabelFormatter(new PointLabelFormatter());
        driverDistractionFormat.configure(getApplicationContext(),
                R.xml.lpf_driverdistractionlevel);

        //Add series to the plot with the correct formats.
        plot.clear();
        plot.addSeries(driverDistractionSeries, driverDistractionFormat);

    }

    private DataList weekFill(){

        DataList data = new DataList("w");

        data.setDriverDistractionLevel(1, 1);
        data.setDriverDistractionLevel(2, 3);
        data.setDriverDistractionLevel(3, 2);
        data.setDriverDistractionLevel(4, 1);
        data.setDriverDistractionLevel(5, 4);
        data.setDriverDistractionLevel(6, 1);

        return data;
    }

    private DataList monthFill(){

        DataList data = new DataList("w");

        data.setDriverDistractionLevel(1, 4);
        data.setDriverDistractionLevel(2, 3);
        data.setDriverDistractionLevel(3, 1);
        data.setDriverDistractionLevel(4, 2);
        data.setDriverDistractionLevel(5, 1);
        data.setDriverDistractionLevel(6, 4);

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
        MenuItem item= menu.findItem(R.id.menuDriverDistractionOption);
        //depending on your conditions, either enable/disable
        item.setEnabled(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menuSpeedOption:
                Intent intentS = new Intent(this, ResultsView.class);
                this.startActivity(intentS);
                return true;
            case R.id.menuFuel_consumptionOption:
                Intent intentFC = new Intent(this, FuelConsumptionGraph.class);
                this.startActivity(intentFC);
                return true;
            case R.id.menuPointsOption:
                Intent intentP = new Intent(this, DriverDistractionGraph.class);
                this.startActivity(intentP);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
