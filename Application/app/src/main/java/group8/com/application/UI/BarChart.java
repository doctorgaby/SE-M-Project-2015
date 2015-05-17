package group8.com.application.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class BarChart extends Activity {

    private GraphicalView mChartView;
    public GraphicalView getView(Context context, int speed, int fuelconsumption, int driverdistraction, int brake) {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries sp = new XYSeries("Speed");
        XYSeries fc = new XYSeries("Fuel Consumption");
        XYSeries dd = new XYSeries("Driver Distraction");
        XYSeries bk = new XYSeries("Brake");


        sp.add(0.5, speed);
        fc.add(1.5, fuelconsumption);
        bk.add(2.5, brake);
        dd.add(3.5, driverdistraction);

        //add the XY series to the dataset
        dataset.addSeries(sp);
        dataset.addSeries(fc);
        dataset.addSeries(bk);
        dataset.addSeries(dd);


        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setChartTitle("Your Total Points");
        mRenderer.setChartTitleTextSize(70);
        //mRenderer.setXTitle("Measurements");
        //mRenderer.setYTitle("Points");
        // mRenderer.setAxisTitleTextSize(80);
        //mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setXAxisColor(Color.TRANSPARENT);
        mRenderer.setYAxisColor(Color.WHITE);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.TRANSPARENT);
        mRenderer.setMargins(new int[]{60, 60, 60, 60});
        mRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
        //mRenderer.setMarginsColor(Color.WHITE);

        mRenderer.setZoomEnabled(true, true);
        mRenderer.setExternalZoomEnabled(true);
        mRenderer.setPanEnabled(false, true);
        //mRenderer.setZoomButtonsVisible(true);
        mRenderer.setBarWidth(150);
        mRenderer.setBarSpacing(0.5);

        mRenderer.setLabelsTextSize(30);
        mRenderer.setLabelsColor(Color.WHITE);
        //mRenderer.setLegendTextSize(30);
        mRenderer.setShowLegend(false);
        mRenderer.addXTextLabel(0.5, "Speed");
        mRenderer.addXTextLabel(1.5, "Fuel Consumption");
        mRenderer.addXTextLabel(2.5, "Brake");
        mRenderer.addXTextLabel(3.5, "Driver Distraction");

        mRenderer.setXAxisMax(ResultsView.BIND_AUTO_CREATE);
        mRenderer.setShowGridY(true);
        mRenderer.setShowGridX(true);
        mRenderer.setShowAxes(true);

        mRenderer.setYAxisMin(0);
       // mRenderer.setYAxisMax(MainView.max); // CHANGE
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(4);
       // mRenderer.setXLabelsAlign(Paint.Align.LEFT);
        mRenderer.setXLabelsColor(Color.WHITE);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setXLabels(0);
        mRenderer.setYLabelsColor(0, Color.WHITE);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.parseColor("#a300ff"));
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesTextSize(70);

        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setColor(Color.parseColor("#0033ff"));
        renderer2.setDisplayChartValues(true);
        renderer2.setChartValuesTextSize(70);

        XYSeriesRenderer renderer3 = new XYSeriesRenderer();
        renderer3.setColor(Color.parseColor("#ff4d01"));
        renderer3.setDisplayChartValues(true);
        renderer3.setChartValuesTextSize(70);

        XYSeriesRenderer renderer4 = new XYSeriesRenderer();
        renderer4.setColor(Color.parseColor("#ffff00"));
        renderer4.setDisplayChartValues(true);
        renderer4.setChartValuesTextSize(70);

          //add the single renderers to the multiple renderer
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.addSeriesRenderer(renderer2);
        mRenderer.addSeriesRenderer(renderer3);
        mRenderer.addSeriesRenderer(renderer4);

        mChartView = ChartFactory.getBarChartView(context, dataset, mRenderer, Type.STACKED);
        return mChartView;
    }
}