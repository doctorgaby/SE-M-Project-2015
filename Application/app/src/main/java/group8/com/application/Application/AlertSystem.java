package group8.com.application.Application;

import android.os.CountDownTimer;

import group8.com.application.Model.ConstantData;

/**
 * This class is used to evaluate the behavior of the driver, and alert the driver if extreme situations occur.
 * */
public class AlertSystem {



    /* Variable initialization */
    private static boolean brakeAlert = false, brakeIsCounting = false,
                           driverDistractionAlert = false, driverDistractionIsCounting = false,
                           speedAlert = true,
                           fuelAlert = true,
                           shouldAlert = true;
    private static int brakeCount = 0, distractionCount = 0;
    private static CountDownTimer brakeCountDown = null;



    /* Initialize the cool down timer, which is preventing the alerts to appear more than once every 10 seconds */
    private static CountDownTimer coolDown = new CountDownTimer(10000, 10000) {
        @Override
        public void onTick(long millisUntilFinished) {}

        @Override
        public void onFinish() {
            shouldAlert = true;
        }
    };



    /**
     * Evaluate the speed.
     *
     * @return true if the speed is above the set limit, and false if it is not.
     * */
    protected static boolean evaluateSpeed() {

        if(shouldAlert && speedAlert && Controller.getCurrentSpeed() >= ConstantData.extremeSpeed) {

            // The booleans are used to prevent the alert to appear more than once at a time
            shouldAlert = false;
            speedAlert = false;
            coolDown.start();
            return true;

        } else if(Controller.getCurrentSpeed() < ConstantData.extremeSpeed) {

            // The speed alert can now appear again
            speedAlert = true;

        }

        return false;

    }



    /**
     * Evaluate the fuel consumption.
     *
     * @return true if the fuel consumption is above the set limit, and false if it is not.
     * */
    protected static boolean evaluateFuelConsumption() {

        if(shouldAlert && fuelAlert && Controller.getCurrentFuelConsumption() >= ConstantData.extremeFuelConsumption) {

            // The booleans are used to prevent the alert to appear more than once at a time
            shouldAlert = false;
            fuelAlert = false;
            coolDown.start();
            return true;

        } else if(Controller.getCurrentFuelConsumption() < ConstantData.extremeFuelConsumption) {

            // The fuel alert can now appear again
            fuelAlert = true;

        }

        return false;

    }



    /**
     * Evaluate the braking.
     *
     * @return true if the brake has been pushed for too long(5s), and false if it has not.
     * */
    protected static boolean evaluateBrake() {

        // When the brake is equal to 1, the brake pedal is active
        if(!brakeIsCounting && Controller.getCurrentBrake() == 1 && brakeCount == 0) {

            brakeIsCounting = true;

            brakeCountDown = new CountDownTimer(5000, 100) {

                @Override
                public void onTick(long millisUntilFinished) {}

                @Override
                public void onFinish() {

                    brakeAlert = true;
                    brakeIsCounting = false;

                }

            }.start();

            // To prevent the alert to appear more than once during one single brake(the pedal has to be released before another alert can appear)
            brakeCount++;

        } else if(Controller.getCurrentBrake() == 0) {

            if(brakeCountDown != null) {

                // When the car releases the brake, the timer(and the alert) is cancelled
                brakeCountDown.cancel();

            }

            // Everything resets, in order to enable another alert
            brakeCount = 0;
            brakeIsCounting = false;
            brakeCountDown = null;

        }

        // The alert should happen
        if(shouldAlert && brakeAlert) {

            shouldAlert = false;
            brakeAlert = false;
            coolDown.start();

            return true;

        }

        return false;

    }



    /**
     * Evaluate the distraction level.
     *
     * @return true if the distraction level has been at maximum for too long(2s), and false if it has not.
     * */
    protected static boolean evaluateDriverDistractionLevel() {

        // When the distraction level is equal to 4, the driver is fully distracted
        if(!driverDistractionIsCounting && Controller.getCurrentDistractionLevel() == 4 && distractionCount == 0) {

            driverDistractionIsCounting = true;

            new CountDownTimer(2000, 100) {
                @Override
                public void onTick(long millisUntilFinished) {

                    if(Session.getLastDriverDistractionLevel() == 0) {

                        // When the driver is not distracted at all, the timer(and the alert) is cancelled
                        driverDistractionIsCounting = false;
                        this.cancel();

                    }

                }

                @Override
                public void onFinish() {

                    driverDistractionAlert = true;
                    driverDistractionIsCounting = false;

                }
            }.start();

            // To prevent the alert to appear more than once during one single distracted period(the driver has to be less distracted before another alert can appear)
            distractionCount++;

        } else if(Controller.getCurrentDistractionLevel() < 4)
            // Counter resets, in order to enable another alert
            distractionCount = 0;

        // The alert should happen
        if(shouldAlert && driverDistractionAlert) {

            shouldAlert = false;
            coolDown.start();
            driverDistractionAlert = false;
            return true;

        }

        return false;

    }

}