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
public class FuelConsumptionGraph extends Activity {

    private XYPlot plot;
    protected DataList data;
    int xMin, xMax, xRange, yMin, yMax, yRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_display);

        //build a default graph from session
        buildFcGraph(data = Controller.eventGetMeasurements());

        //Listeners for filter buttons
        Button currBtn = (Button) findViewById(R.id.currBtn);
        currBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildFcGraph(data = Controller.eventGetMeasurements());
                plot.redraw();
            }
        });

        Button weekBtn = (Button) findViewById(R.id.weekBtn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildFcGraph(data = Controller.eventGetFilteredMeasurements( (int) System.currentTimeMillis(), (int) System.currentTimeMillis() - 60480000));
                plot.redraw();
            }
        });

        Button monthBtn = (Button) findViewById(R.id.monthBtn);
        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildFcGraph(data = Controller.eventGetFilteredMeasurements( (int) System.currentTimeMillis(), (int) System.currentTimeMillis() - 262800000));
                plot.redraw();
            }
        });
    }

    //building fuel consumption graph
    private void buildFcGraph(DataList data){

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);
        plot.getRangeLabelWidget().setText("Fuel Consumption");
        plot.getTitleWidget().setText("Liters per Measurement");

        //Plotting Variables
        xMin = 0;
        xMax = data.getMaxTime();
        xRange = xMax / 5;
        yMin = 0;
        yMax = data.getMaxPoints();
        yRange = (yMax - yMin) / 5;

        //Domain: X-Axis
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, xRange);
        plot.setDomainBoundaries(xMin, xMax, BoundaryMode.FIXED);

        //Range: Y-Axis
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, yRange);
        plot.setRangeBoundaries(yMin, yMax + yRange, BoundaryMode.FIXED);

        XYSeries fuelConsumptionSeries = new SimpleXYSeries(
                data.getPlottableFuelConsumption(),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Fuel Consumption");


        //Initiate the formatters for the lines.
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:

        LineAndPointFormatter fuelConsumptionFormat = new LineAndPointFormatter();
        fuelConsumptionFormat.setPointLabelFormatter(new PointLabelFormatter());
        fuelConsumptionFormat.configure(getApplicationContext(),
                R.xml.lfp_fuelconsumption);

        //Add series to the plot with the correct formats.
        plot.clear();
        plot.addSeries(fuelConsumptionSeries, fuelConsumptionFormat);

    }
    //test for filter
    private DataList weekFill(){

        DataList data = new DataList("w");

        data.setFuelConsumption(1, 1);
        data.setFuelConsumption(2, 5);
        data.setFuelConsumption(3, 10);
        data.setFuelConsumption(4, 20);
        data.setFuelConsumption(5, 3);
        data.setFuelConsumption(6, 6);

        return data;
    }

    //test for filter
    private DataList monthFill() {

        DataList data = new DataList("w");

        data.setFuelConsumption(1, 20);
        data.setFuelConsumption(2, 40);
        data.setFuelConsumption(3, 10);
        data.setFuelConsumption(4, 5);
        data.setFuelConsumption(5, 9);
        data.setFuelConsumption(6, 18);

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
        MenuItem item= menu.findItem(R.id.menuFuel_consumptionOption);
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
            case R.id.menuSpeedOption:
                Intent intentS = new Intent(this, SpeedGraph.class);
                this.startActivity(intentS);
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
