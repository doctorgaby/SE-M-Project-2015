package group8.com.application;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
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
 * Created by kike on 2015-03-03.
 */
public class FuelConsumption extends Activity {

    private TextView dfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        dfc = (TextView) findViewById(R.id.displayFuelConsumption);


        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]),
                        new AutomotiveListener() {
                            @Override
                            public void receive(final AutomotiveSignal automotiveSignal) {
                                dfc.post(new Runnable() {
                                    public void run() {
                                        //dfc.setText(String.format("%.1f km/L", ((SCSFloat) automotiveSignal.getData()).getFloatValue())); //ID = 323
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
                ).register(AutomotiveSignalId.FMS_INSTANTANEOUS_FUEL_ECONOMY); // Register for the fuel consumption signal

                return null;
            }
        }.execute();

    }

}
