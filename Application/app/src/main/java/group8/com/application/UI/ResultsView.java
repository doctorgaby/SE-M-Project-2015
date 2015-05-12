package group8.com.application.UI;

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

import java.util.Collections;
import java.util.List;

import group8.com.application.Application.Session;
import group8.com.application.Application.Controller;
import group8.com.application.Model.DataList;
import group8.com.application.R;
import group8.com.application.UI.Graphs.DriverDistractionGraph;
import group8.com.application.UI.Graphs.FuelConsumptionGraph;
import group8.com.application.UI.Graphs.SpeedGraph;
import group8.com.application.R;

public class ResultsView extends Activity {

    private int xMin, xMax, xRange, yMin, yMax, yRange;
    protected DataList data;
    private XYPlot plot;
    private String setPlot;
    private Button currBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_display);

        //build a default graph from session
        buildPlot(data = Controller.eventGetPoints(), setPlot);

        //Listeners for filter buttons
        currBtn = (Button) findViewById(R.id.currBtn);
        currBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildPlot(data = Controller.eventGetPoints(), setPlot);
                plot.redraw();

            }
        });

        Button weekBtn = (Button) findViewById(R.id.weekBtn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildPlot(data = Controller.eventGetFilteredPoints((int) ((System.currentTimeMillis() / 1000) - 300), (int) (System.currentTimeMillis() / 1000)) , setPlot);
                plot.redraw();

            }
        });

        Button monthBtn = (Button) findViewById(R.id.monthBtn);
        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plot.clear();
                buildPlot(data = Controller.eventGetFilteredPoints((int) System.currentTimeMillis() / 1000, (int) System.currentTimeMillis() / 1000 - 262800), setPlot);
                plot.redraw();

            }
        });

    }

    /**
     * Builds the actual graph
     *
     * @param data
     */

    private void buildPlot(DataList data, String s){

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);

        //Plotting Variables
        xMin = 0;
        xMax = data.getMaxTime();
        xRange = xMax / 5;
        yMin = 0;
        yMax = 100;
        yRange = yMax / 5;

        //Domain: X-Axis
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, xRange);
        plot.setDomainBoundaries(xMin, xMax, BoundaryMode.FIXED);

        //Range: Y-Axis
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, yRange);
        plot.setRangeBoundaries(yMin, yMax + yRange, BoundaryMode.FIXED);

        if (s == "speed"){
            buildSpeedPlot(data);

        }
        else if (s == "fuel"){
            buildFuelPlot(data);

        }
        else if (s == "distraction"){
            buildDistractionPlot(data);

        } else {
            buildFullPlot(data);

       }

    }

    /**
     * builds a graph for the
     * speed score
     *
     * @param data sets values
     */

    private void buildSpeedPlot(DataList data){

        //set plot UI description
        plot.getRangeLabelWidget().setText("Speed (km/h)");
        plot.getTitleWidget().setText("Speed per Measurement");

        //Initiate Series to Draw
        XYSeries speedSeries = new SimpleXYSeries(
                (data.getPlottableSpeed()),
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

    /**
     * builds a graph for the
     * fuel consumption score
     *
     * @param data sets values
     */

    private void buildFuelPlot(DataList data){

        //set Plot UI description
        plot.getRangeLabelWidget().setText("Fuel Consumption");
        plot.getTitleWidget().setText("Liters per Measurement");

        //Initiate Series to Draw
        XYSeries fuelConsumptionSeries = new SimpleXYSeries(
                data.getPlottableFuelConsumption(),
                SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
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

    /**
     * builds a graph for the
     * driver distraction score
     *
     * @param data sets values
     */

    private void buildDistractionPlot(DataList data){

        //set plot UI description
        plot.getRangeLabelWidget().setText("Driver Distraction");
        plot.getTitleWidget().setText("Distraction per Measurement");

        //Initiate Series to Draw
        XYSeries driverDistractionSeries = new SimpleXYSeries(
                data.getPlottableDriverDistraction(),
                SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                "DDL");

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

    /**
     * builds a graph with points from all
     * measurement
     *
     * @param data sets values
     */
    private void buildFullPlot(DataList data) {

        //Initiate Series to Draw
        XYSeries speedSeries = new SimpleXYSeries(
                data.getPlottableSpeed(),
                SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                "S");

        XYSeries fuelConsumptionSeries = new SimpleXYSeries(
                data.getPlottableFuelConsumption(),
                SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                "FC");

        XYSeries brakeSeries = new SimpleXYSeries(
                data.getPlottableBrake(),
                SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                "B");

        XYSeries driverDistractionSeries = new SimpleXYSeries(
                data.getPlottableDriverDistraction(),
                SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED, // Y_VALS_ONLY means use the element index as the x value
                "DDL");

        //Initiate the formatters for the lines.
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter speedFormat = new LineAndPointFormatter();
        speedFormat.setPointLabelFormatter(new PointLabelFormatter());
        speedFormat.configure(getApplicationContext(),
                R.xml.lpf_speed);

        LineAndPointFormatter fuelConsumptionFormat = new LineAndPointFormatter();
        fuelConsumptionFormat.setPointLabelFormatter(new PointLabelFormatter());
        fuelConsumptionFormat.configure(getApplicationContext(),
                R.xml.lfp_fuelconsumption);

        LineAndPointFormatter brakeFormat = new LineAndPointFormatter();
        brakeFormat.setPointLabelFormatter(new PointLabelFormatter());
        brakeFormat.configure(getApplicationContext(),
                R.xml.lpf_brake);

        LineAndPointFormatter driverDistractionFormat = new LineAndPointFormatter();
        driverDistractionFormat.setPointLabelFormatter(new PointLabelFormatter());
        driverDistractionFormat.configure(getApplicationContext(),
                R.xml.lpf_driverdistractionlevel);

        //Add series to the plot with the correct formats.
        plot.clear();
        plot.addSeries(speedSeries, speedFormat);
        plot.addSeries(fuelConsumptionSeries, fuelConsumptionFormat);
        plot.addSeries(brakeSeries, brakeFormat);
        plot.addSeries(driverDistractionSeries, driverDistractionFormat);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    /**
     * updates the menu with new values
     * in this case it sets the visibility
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (setPlot == "speed") {
            menu.findItem(R.id.menuSpeedOption).setVisible(false);
            menu.findItem(R.id.menuFuel_consumptionOption).setVisible(true);
            menu.findItem(R.id.menuDriverDistractionOption).setVisible(true);
            menu.findItem(R.id.menuPointsOption).setVisible(true);
        }
        else if(setPlot == "fuel"){
            menu.findItem(R.id.menuSpeedOption).setVisible(true);
            menu.findItem(R.id.menuFuel_consumptionOption).setVisible(false);
            menu.findItem(R.id.menuDriverDistractionOption).setVisible(true);
            menu.findItem(R.id.menuPointsOption).setVisible(true);
        }
        else if (setPlot == "distraction") {
            menu.findItem(R.id.menuSpeedOption).setVisible(true);
            menu.findItem(R.id.menuFuel_consumptionOption).setVisible(true);
            menu.findItem(R.id.menuDriverDistractionOption).setVisible(false);
            menu.findItem(R.id.menuPointsOption).setVisible(true);
        } else {
            menu.findItem(R.id.menuSpeedOption).setVisible(true);
            menu.findItem(R.id.menuFuel_consumptionOption).setVisible(true);
            menu.findItem(R.id.menuDriverDistractionOption).setVisible(true);
            menu.findItem(R.id.menuPointsOption).setVisible(false);
        }

        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        //clears and repaints before every change
        switch (item.getItemId()) {

            case R.id.menuSpeedOption:
                setPlot = "speed";
                currBtn.callOnClick();
                return true;

            case R.id.menuFuel_consumptionOption:
                setPlot = "fuel";
                currBtn.callOnClick();
                return true;

            case R.id.menuDriverDistractionOption:
                setPlot = "distraction";
                currBtn.callOnClick();
                return true;

            case R.id.menuPointsOption:
                setPlot = "";
                currBtn.callOnClick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
