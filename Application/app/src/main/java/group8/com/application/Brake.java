package group8.com.application;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.Uint8;
import android.widget.TextView;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

/**
 * Created by Hampus on 2015-03-03.
 */
public class Brake extends Activity {

    private TextView db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        db = (TextView) findViewById(R.id.displayBrake);


        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]),
                        new AutomotiveListener() {
                            @Override
                            public void receive(final AutomotiveSignal automotiveSignal) {
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
                            }

                            @Override
                            public void timeout(int i) {
                                System.out.println("time out");
                            }

                            @Override
                            public void notAllowed(int i) {
                            }
                        },
                        new DriverDistractionListener() {
                            @Override
                            public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {}
                            @Override
                            public void lightModeChanged(LightMode lightMode) {}
                            @Override
                            public void stealthModeChanged(StealthMode stealthMode) {}
                        }
                ).register(AutomotiveSignalId.FMS_BRAKE_SWITCH); // Register for the brake signal

                return null;
            }
        }.execute();

    }

}
