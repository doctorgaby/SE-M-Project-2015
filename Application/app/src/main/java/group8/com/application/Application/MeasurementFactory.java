package group8.com.application.Application;

import android.os.AsyncTask;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.Uint8;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

/**
 * Created by enriquecordero on 30/03/15.
 */
public abstract class MeasurementFactory {

    private static boolean running = false;
    private static AsyncTask measurementTask;

    /**
     * Start measuring.
     * */
    public static void initMeasurements() {

        if(measurementTask == null) {

            measurementTask = new AsyncTask() {

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
                                            double speed = (double) (((SCSFloat) automotiveSignal.getData()).getFloatValue());
                                            if(running)
                                                Controller.eventSpeedChanged(speed);
                                            break;
                                        case 323: //Instantaneous fuel economy has signalID 323.
                                            double fuelConsumption = (double) (((SCSFloat) automotiveSignal.getData()).getFloatValue());
                                            if(running)
                                                Controller.eventFuelConsumptionChanged(fuelConsumption);
                                            break;
                                        case 317: //Brake has signalID 317.
                                            int brake = ((Uint8) automotiveSignal.getData()).getIntValue();
                                            if(running)
                                                Controller.eventBrakeChanged(brake);
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
                                    if(running)
                                        Controller.eventDriverDistractionLevelChanged(driverDistractionLevel.getLevel());
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
            };
            measurementTask.execute();

        }

    }

    protected static void startMeasurements() {

        running = true;

    }


    /**
     * Stop measuring.
     * */
    public static void pauseMeasurements() {

        running = false;
        GradingSystem.stopGradingSystem();

    }


    /**
     * Check if the MeasurementFactory is measuring.
     *
     * @return true if the MeasurementFactory is measuring, and false if it is not.
     * */
    public static boolean isMeasuring() {

        return running;

    }

}