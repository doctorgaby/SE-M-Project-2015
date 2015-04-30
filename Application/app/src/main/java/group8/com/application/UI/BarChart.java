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

import group8.com.application.Application.Session;

public class BarChart extends Activity {

    int speed = Session.getSpeedScore();
    int fuelconsumption = Session.getFuelConsumptionScore();
    int driverdistraction = Session.getDriverDistractionLevelScore();
    int brake = Session.getBrakeScore();

    public GraphicalView getView(Context context) {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries sp = new XYSeries("Speed");
        XYSeries fc = new XYSeries("Fuel Consumption");
        XYSeries dd = new XYSeries("Driver Distraction");
        XYSeries bk = new XYSeries("Brake");

        sp.add(0, speed);
        fc.add(1, fuelconsumption);
        dd.add(2, driverdistraction);
        bk.add(3, brake);

        dataset.addSeries(sp);
        dataset.addSeries(fc);
        dataset.addSeries(dd);
        dataset.addSeries(bk);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setChartTitle("Your Points");
        // mRenderer.setXTitle("");
        // mRenderer.setYTitle("");
        mRenderer.setAxesColor(Color.BLACK);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.LTGRAY);
        mRenderer.setMarginsColor(Color.WHITE);
        mRenderer.setZoomEnabled(true);
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setBarSpacing(0.0f);
//      mRenderer.setMargins(new int[] {20, 30, 15, 0});
        mRenderer.setAxisTitleTextSize(16);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.addXTextLabel(1, "Speed");
        mRenderer.addXTextLabel(2, "Fuel Consumption");
        mRenderer.addXTextLabel(3, "Driver Distraction");
        mRenderer.addXTextLabel(4, "Brake");
        mRenderer.setBarWidth(200);
//      mRenderer.setXAxisMax(9);
        mRenderer.setXAxisMin(0);
        mRenderer.setYAxisMin(0);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0, Color.BLACK);
        mRenderer.setXLabels(0);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.parseColor("#00AA00"));
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesTextSize(150);

        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setColor(Color.parseColor("#666600"));
        renderer2.setDisplayChartValues(true);
        renderer2.setChartValuesTextSize(70);

        XYSeriesRenderer renderer3 = new XYSeriesRenderer();
        renderer3.setColor(Color.parseColor("#FF0000"));
        renderer3.setDisplayChartValues(true);
        renderer3.setChartValuesTextSize(100);

        XYSeriesRenderer renderer4 = new XYSeriesRenderer();
        renderer4.setColor(Color.parseColor("#0033ff"));
        renderer4.setDisplayChartValues(true);
        renderer4.setChartValuesTextSize(50);

        mRenderer.addSeriesRenderer(renderer);
        mRenderer.addSeriesRenderer(renderer2);
        mRenderer.addSeriesRenderer(renderer3);
        mRenderer.addSeriesRenderer(renderer4);

        return ChartFactory.getBarChartView(context, dataset, mRenderer, Type.STACKED);
       /* layout =(LinearLayout)findViewById(R.id.BarChart);
        mChartView=ChartFactory.getBarChartView(getApplicationContext(), dataset, mRenderer, org.achartengine.chart.BarChart.Type.DEFAULT);
        layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
*/

    }
}