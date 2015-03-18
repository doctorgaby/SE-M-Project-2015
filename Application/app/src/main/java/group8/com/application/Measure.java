package group8.com.application;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.Uint8;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;


public class Measure extends Activity {

    private TextView ds;
    private TextView dfc;
    private TextView db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        ds = (TextView) findViewById(R.id.displaySpeed);
        dfc = (TextView) findViewById(R.id.displayFuelConsumption);
        db = (TextView) findViewById(R.id.displayBrake);
        Button graphBtn = (Button) findViewById(R.id.graphBtn);


        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]),
                        new AutomotiveListener() { // Listener that observes the Signals
                            @Override
                            public void receive(final AutomotiveSignal automotiveSignal) {
                                int choice = automotiveSignal.getSignalId();
                                switch (choice) {
                                    case 320: //Speed has signalID 320.
                                        ds.post(new Runnable() { // Post the result back to the View/UI thread
                                            public void run() {
                                                ds.setText(String.format("%.1f km/h", ((SCSFloat) automotiveSignal.getData()).getFloatValue()));//ID = 320 for speed
                                                //ds.setText(Integer.toString(automotiveSignal.getSignalId()));
                                            }
                                        });
                                        break;
                                    case 323: //Instantaneous fuel economy has signalID 323.
                                        dfc.post(new Runnable() {
                                            public void run() {
                                                dfc.setText(String.format("%.1f km/L", ((SCSFloat) automotiveSignal.getData()).getFloatValue())); //ID = 323
                                            }
                                        });
                                        break;
                                    case 317: //Brake has signalID 317.
                                        db.post(new Runnable() {
                                            public void run() {//ID = 317 for breaking

                                                String output;

                                                if (((Uint8) automotiveSignal.getData()).getIntValue() == 0) // If the brake is in a "Released" state.
                                                    output = "No!";
                                                else
                                                    output = "Yes!";

                                                db.setText(output);
                                            }
                                        });
                                        break;
                                    default:
                                        break;
                                }
                            }

                            @Override
                            public void timeout(int i) {
                            }

                            @Override
                            public void notAllowed(int i) {
                            }
                        },
                        new DriverDistractionListener() { // Observe driver distraction level
                            @Override
                            public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                                ds.post(new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        ds.setTextSize(driverDistractionLevel.getLevel()*10.0F + 12.0F);
                                    }
                                });
                            }

                            @Override
                            public void lightModeChanged(LightMode lightMode) {
                            }

                            @Override
                            public void stealthModeChanged(StealthMode stealthMode) {
                            }
                        }
                ).register(AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY, AutomotiveSignalId.FMS_WHEEL_BASED_SPEED, AutomotiveSignalId.FMS_BRAKE_SWITCH); // Register for the speed signal

                return null;
            }
        }.execute();

        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), GraphPoints.class);
                startActivityForResult(intent, 0);
            }
        });
    }

}

