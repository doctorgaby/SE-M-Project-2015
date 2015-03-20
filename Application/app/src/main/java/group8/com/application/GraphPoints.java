package group8.com.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

/**
 * Created by enriquecordero on 15/03/15.
 */
public class GraphPoints extends Activity {
    private static final String TAG = "GraphPoints";

    private XYPlot plot;
    Points data;
    int xMin, xMax, xRange, yMin, yMax, yRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_points);

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);

        //Points Data
        data = new Points();

        //Test
        data.setSpeed(10);
        data.setSpeed(30);
        data.setSpeed(80);
        data.setSpeed(70);
        data.setSpeed(75);
        data.setSpeed(90);
        data.setFuelConsumption(5);
        data.setFuelConsumption(10);
        data.setFuelConsumption(30);
        data.setFuelConsumption(0);
        data.setFuelConsumption(10);
        data.setFuelConsumption(25);
        data.setBrake(50);
        data.setBrake(45);
        data.setBrake(35);
        data.setBrake(45);
        data.setBrake(65);
        data.setBrake(75);
        data.setDriverDistractionLevel(50);
        data.setDriverDistractionLevel(55);
        data.setDriverDistractionLevel(45);
        data.setDriverDistractionLevel(35);
        data.setDriverDistractionLevel(60);
        data.setDriverDistractionLevel(90);
        //end Test

        //Plotting Variables
        xMin = 0;
        xMax = data.getMaxLength();
        xRange = xMax/5;
        yMin = 0;//This should be done according to the values depending whether or not to allow negative points.
        yMax = data.getMaxOfAll();
        yRange = (yMax-yMin)/5;

        //Domain: X-Axis
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, xRange);
        plot.setDomainBoundaries(xMin, xMax, BoundaryMode.FIXED);

        //Range: Y-Axis
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, yRange);
        plot.setRangeBoundaries(yMin, yMax+yRange, BoundaryMode.FIXED);


        //Initiate Series to Draw
        XYSeries speedSeries = new SimpleXYSeries(
        data.getSpeed(),
        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
        "S");

        XYSeries fuelConsumptionSeries = new SimpleXYSeries(
        data.getFuelConsumption(),
        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
        "FC");

        XYSeries brakeSeries = new SimpleXYSeries(
        data.getBrake(),
        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
        "B");

        XYSeries driverDistractionSeries = new SimpleXYSeries(
        data.getDriverDistractionLevel(),
        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
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

    //Integration done by Kristiyan

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.speed:
                Intent intentS = new Intent(this, SpeedGraph.class);
                this.startActivity(intentS);
                return true;
            case R.id.fuel_consumption:
                Intent intentFC = new Intent(this, FuelConsumptionGraph.class);
                this.startActivity(intentFC);

                return true;
            case R.id.breaks:
                Intent intentB = new Intent(this, BreakGraph.class);
                this.startActivity(intentB);

                return true;
            case R.id.driver_distraction:
                  Intent intentDDL = new Intent(this,DriverDistractionGraph.class);
                  this.startActivity(intentDDL);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
