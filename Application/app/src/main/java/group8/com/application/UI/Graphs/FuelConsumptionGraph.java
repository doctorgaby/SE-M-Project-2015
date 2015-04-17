package group8.com.application.UI.Graphs;

import android.app.Activity;
import android.os.Bundle;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import group8.com.application.Model.DataList;
import group8.com.application.R;

/**
 * Created by Kristiyan on 3/16/2015.
 */
public class FuelConsumptionGraph extends Activity {

    private XYPlot plot;
    DataList data;
    int xMin, xMax, xRange, yMin, yMax, yRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_display);

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);
        plot.getRangeLabelWidget().setText("Fuel Consumption");
        plot.getTitleWidget().setText("Liters per Measurement");

        //Points Data
        data = new DataList("p");

        //Test
        data.setFuelConsumption(1, 1);
        data.setFuelConsumption(2, 5);
        data.setFuelConsumption(3, 10);
        data.setFuelConsumption(4, 20);
        data.setFuelConsumption(5, 3);
        data.setFuelConsumption(6, 6);

        //end Test

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
}
