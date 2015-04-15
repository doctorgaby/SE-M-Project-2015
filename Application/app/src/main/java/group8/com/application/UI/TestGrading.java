package group8.com.application.UI;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import group8.com.application.Application.GradingSystem;
import group8.com.application.Application.MeasurementFactory;
import group8.com.application.Application.Session;
import group8.com.application.R;

public class TestGrading extends Activity {

    TextView speed;
    TextView fuel;
    TextView brake;
    TextView distraction;
    TextView notRunningText;
    Button startGrading;
    Button stopGrading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_grading);

        speed = (TextView) findViewById(R.id.speed);
        fuel = (TextView) findViewById(R.id.fuel);
        brake = (TextView) findViewById(R.id.brake);
        distraction = (TextView) findViewById(R.id.distraction);
        startGrading = (Button) findViewById(R.id.startGrading);
        stopGrading = (Button) findViewById(R.id.stopGrading);

        MeasurementFactory.startMeasurements();

        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                while (!Thread.interrupted()) {

                    if (GradingSystem.isGrading()) {

                        speed.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        speed.setText("" + Session.getSpeedScore());
                                    }
                                }
                        );

                        fuel.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        fuel.setText("" + Session.getFuelConsumptionScore());
                                    }
                                }
                        );

                        brake.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        brake.setText("" + Session.getBrakeScore());
                                    }
                                }
                        );

                        distraction.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        distraction.setText("" + Session.getDriverDistractionLevelScore());
                                    }
                                }
                        );
                    }

                }

                return null;

            }

        }.execute();

        startGrading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradingSystem.startGradingSystem();
            }
        });

        stopGrading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradingSystem.stopGradingSystem();
            }
        });

    }

}