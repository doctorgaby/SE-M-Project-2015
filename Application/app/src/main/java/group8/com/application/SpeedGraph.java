package group8.com.application;

import android.app.Activity;
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
 * Created by Kristiyan on 3/16/2015.
 */
public class SpeedGraph extends Activity {

    private XYPlot plot;
    Measurements data;
    int xMin, xMax, xRange, yMin, yMax, yRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_points);

        //Android Plot
        plot = (XYPlot) findViewById(R.id.Graph);
        plot.getRangeLabelWidget().setText("Speed (km/h)");
        plot.getTitleWidget().setText("Speed per Measurement" );

        //Points Data
        data = new Measurements();

        //Test
        data.setSpeed(40);
        data.setSpeed(35);
        data.setSpeed(80);
        data.setSpeed(120);
        data.setSpeed(160);
        data.setSpeed(90);


        //Plotting Variables
        xMin = 0;
        xMax = data.getMaxLength();
        xRange = xMax/5;
        yMin = 0;
        yMax = 300;
        yRange = (yMax-yMin)/15;

        //Domain: X-Axis
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, xRange);
        plot.setDomainBoundaries(xMin, xMax, BoundaryMode.FIXED);

        //Range: Y-Axis
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, yRange);
        plot.setRangeBoundaries(yMin, yMax, BoundaryMode.FIXED);


        //Initiate Series to Draw
        XYSeries speedSeries = new SimpleXYSeries(
                data.getSpeed(),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
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
}
