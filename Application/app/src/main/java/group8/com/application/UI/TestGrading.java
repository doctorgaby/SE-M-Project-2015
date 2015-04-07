package group8.com.application.UI;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_grading);

        MeasurementFactory.startMeasurements();
        GradingSystem.startGradingSystem();

        speed = (TextView) findViewById(R.id.speed);
        fuel = (TextView) findViewById(R.id.fuel);
        brake = (TextView) findViewById(R.id.brake);
        distraction = (TextView) findViewById(R.id.distraction);

        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                while(!Thread.interrupted()) {
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
                                    fuel.setText("" + Session.getFuelConsumptionScore() + " " + value);
                                    // value++;
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

                return null;

            }

        }.execute();

    }

}