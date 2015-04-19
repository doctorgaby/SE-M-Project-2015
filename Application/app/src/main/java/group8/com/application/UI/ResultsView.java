package group8.com.application.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
import group8.com.application.UI.Graphs.DriverDistractionGraph;
import group8.com.application.UI.Graphs.FuelConsumptionGraph;
import group8.com.application.UI.Graphs.SpeedGraph;
import group8.com.application.R;

public class ResultsView extends Activity {

    int xMin, xMax, xRange, yMin, yMax, yRange;
    DataList data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_display);
        XYPlot plot;

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);


        //Test for the DBHandler
        //DataList data = Controller.eventGetMeasurements();
        //DataList data = Controller.eventGetFilteredMeasurements(7,10);
<<<<<<< HEAD
        //DataList data = Controller.eventGetPoints();                              !!!NOT WORKING YET
        //Log.d("ResultsView", "DataList loaded!");
        //DataList data = Controller.eventGetFilteredPoints(0,5);                   !!!NOT WORKING YET

        data = Session.currentPoints;

=======
        DataList data = Controller.eventGetPoints();
        Log.d("ResultsView", "DataList loaded!");
        //DataList data = Controller.eventGetFilteredPoints(0,5);
>>>>>>> origin/master
/*
        //Points Data
        DataList data;
        data = new DataList("p");

        //Test
        data.setSpeed(1,10);
        data.setSpeed(2,30);
        data.setSpeed(3,80);
        data.setSpeed(4,70);
        data.setSpeed(5,75);
        data.setSpeed(6,90);
        data.setFuelConsumption(1,5);
        data.setFuelConsumption(2,10);
        data.setFuelConsumption(3,30);
        data.setFuelConsumption(4,0);
        data.setFuelConsumption(5,10);
        data.setFuelConsumption(6,25);
        data.setBrake(1,50);
        data.setBrake(2,45);
        data.setBrake(3,35);
        data.setBrake(4,45);
        data.setBrake(5,65);
        data.setBrake(6,75);
        data.setDriverDistractionLevel(1,50);
        data.setDriverDistractionLevel(2,55);
        data.setDriverDistractionLevel(3,45);
        data.setDriverDistractionLevel(4,35);
        data.setDriverDistractionLevel(5,60);
        data.setDriverDistractionLevel(6,90);
        //end Test
*/
        //Plotting Variables
        xMin = 0;
        xMax = data.getMaxTime();
        xRange = xMax / 5;
        yMin = 0;//This should be done according to the values depending whether or not to allow negative points.
        yMax = data.getMaxPoints();
        yRange = (yMax - yMin) / 5;

        //Domain: X-Axis
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, xRange);
        plot.setDomainBoundaries(xMin, xMax, BoundaryMode.FIXED);

        //Range: Y-Axis
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, yRange);
        plot.setRangeBoundaries(yMin, yMax + yRange, BoundaryMode.FIXED);


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
            case R.id.menuSpeedOption:
                Intent intentS = new Intent(this, SpeedGraph.class);
                this.startActivity(intentS);
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
