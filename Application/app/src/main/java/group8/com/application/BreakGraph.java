package group8.com.application;

import android.app.Activity;
import android.os.Bundle;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

/**
 * Created by Kristiyan on 3/16/2015.
 */
public class BreakGraph extends Activity {

    private XYPlot plot;
    Points data;
    int xMin, xMax, xRange, yMin, yMax, yRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_points);

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);
        plot.getRangeLabelWidget().setText("Breaks");
        plot.getTitleWidget().setText("Breaks per Measurement" );

        //Points Data
        data = new Points();

        //Test
        data.setBrake(0);
        data.setBrake(1);
        data.setBrake(0);
        data.setBrake(0);
        data.setBrake(1);
        data.setBrake(0);
        //end Test

        //Plotting Variables
        xMin = 0;
        xMax = data.getMaxLength();
        xRange = xMax / 5;
        yMin = 0;
        yMax = 1;
        yRange = yMax;

        //Domain: X-Axis
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, xRange);
        plot.setDomainBoundaries(xMin, xMax, BoundaryMode.FIXED);

        //Range: Y-Axis
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, yRange);
        plot.setRangeBoundaries(yMin, yMax, BoundaryMode.FIXED);

        //Initiate Series to Draw
        XYSeries brakeSeries = new SimpleXYSeries(
                data.getBrake(),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Brake");

        //Initiate the formatters for the lines.
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:

        LineAndPointFormatter brakeFormat = new LineAndPointFormatter();
        brakeFormat.setPointLabelFormatter(new PointLabelFormatter());
        brakeFormat.configure(getApplicationContext(),
                R.xml.lpf_brake);

        //Add series to the plot with the correct formats.
        plot.clear();
        plot.addSeries(brakeSeries, brakeFormat);
    }

}
