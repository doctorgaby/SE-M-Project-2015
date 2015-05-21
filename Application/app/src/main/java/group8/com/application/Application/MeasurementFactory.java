package group8.com.application.Application;

import android.os.AsyncTask;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.Uint8;
import android.util.Log;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.automotiveapi.AutomotiveManager;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

/**
 * Class that retrieves the signals from AGA, and sends them to the controller.
 */
public abstract class MeasurementFactory implements AutomotiveManager {

    /* Local variables */
    private static boolean running = false;
    private static AsyncTask measurementTask;
    private static double speed, fuelConsumption;
    private static int brake, distractionLevel;

    /**
     * Initialize the measurements and connection to the car. The measurements have, however, not been started.
     * To start the measurements call the method startMeasurements().
     * */
    protected static void initMeasurements() {

        if(measurementTask == null) {

            measurementTask = new AsyncTask() {

                @Override
                protected Object doInBackground(Object[] params) {
                    AutomotiveFactory.createAutomotiveManagerInstance(
                            new AutomotiveCertificate(new byte[0]),
                            new AutomotiveListener() { // Listener that observes the Signals
                                @Override
                                public void receive(final AutomotiveSignal automotiveSignal) {
                                    Session.continueTimer();
                                    int choice = automotiveSignal.getSignalId();
                                    switch (choice) {
                                        case 320: //Speed has signalID 320.
                                            speed = (double) (((SCSFloat) automotiveSignal.getData()).getFloatValue());
                                            if(running)
                                                Controller.eventSpeedChanged(speed);

                                            break;
                                        case 322: //Fuel Rate has signalID 322.
                                            fuelConsumption = (double) (((SCSFloat) automotiveSignal.getData()).getFloatValue());
                                            if(running)
                                                Controller.eventFuelConsumptionChanged(fuelConsumption);

                                            break;
                                        case 317: //Brake has signalID 317.
                                            brake = ((Uint8) automotiveSignal.getData()).getIntValue();
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
                                    distractionLevel = driverDistractionLevel.getLevel();
                                    if(running)
                                        Controller.eventDriverDistractionLevelChanged(distractionLevel);
                                }

                                @Override
                                public void lightModeChanged(LightMode lightMode) {
                                }

                                @Override
                                public void stealthModeChanged(StealthMode stealthMode) {
                                }
                            }
                    ).register(AutomotiveSignalId.FMS_FUEL_RATE, AutomotiveSignalId.FMS_WHEEL_BASED_SPEED, AutomotiveSignalId.FMS_BRAKE_SWITCH); // Register for the speed signal


                    Log.d("Almost done with ", "do in background");
                    return null;
                }
            };
            measurementTask.execute();

        }

    }

    /**
     * Start measuring.
     * */
    protected static void startMeasurements() {
        running = true;
    }

    /**
     * Stop measuring.
     * */
    protected static void pauseMeasurements() {
        running = false;
    }

    /**
     * Check if the MeasurementFactory is measuring.
     *
     * @return true if the MeasurementFactory is measuring, and false if it is not.
     * */
    protected static boolean isMeasuring() {
        return running;
    }

    /**
     * Get the current speed.
     *
     * @return the current speed level of the car, a value from 0 - 300(km/h).
     * */
    protected static double getSpeed() {
        return speed;
    }

    /**
     * Get the current fuel consumption.
     *
     * @return the current fuel consumption, a value from 0.0 - 3212.75(Litres per hour).
     * */
    protected static double getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * Get the current brake status.
     *
     * @return 0 if the brake is in released state, and 1 if it is in pressed state.
     * */
    protected static int getBrake() {
        return brake;
    }

    /**
     * Get the current distraction level of the driver.
     *
     * @return a value from 1 - 4, with 1 being no distraction, and 4 being very high distraction.
     * */
    protected static int getDistractionLevel() {
        return distractionLevel;
    }

}