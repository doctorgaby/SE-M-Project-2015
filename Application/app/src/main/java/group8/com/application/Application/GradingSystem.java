package group8.com.application.Application;

import android.os.CountDownTimer;

/**
 * Created by Hampus on 2015-03-18.
 *
 * Class representing the logic for the grading of drivers performance.
 * To start the grading system, the method startGrading() has to be called.
 */
public abstract class GradingSystem {

    /* Private variables */
    static private int lastDistractionLevel;                // Stores the current distraction level(updates when the distraction level is changed)
    static private boolean shouldDecreaseBrakeScore = true; // Is set to true when the brake is released
    static private boolean running = true;                  // Flag used in the threads loop
    static private CountDownTimer brakeTimer;               // Timer used for grading the braking

    /**
     * Start the grading system.
     * */
    public static void startGradingSystem() {

        running = true;

        brakeTimer = new CountDownTimer(10000, 1000) { // Create a new countdown. When the countdown has finished, the braking score increases by 1.

            public void onTick(long millisUntilFinished) {
                System.out.println("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Session.setBrakeScore(Session.getBrakeScore() + 1);
                updateBrakeScore(Session.getLastBrake());
            }

        };

    }

    /**
     * Pause the grading system.
     * */
    public static void pauseGradingSystem() {

        running = false;

    }

    /**
     * Resume the grading system.
     * */
    public static void resumeGradingSystem() {

        running = true;

    }

    protected static void updateSpeedScore(double speed) {

        Session.setSpeedScore((int)speed); // update view here

    }

    protected static void updateFuelConsumptionScore(double fuelConsumption) {

        if(fuelConsumption > 60.0) { // If the fuel consumption is "good".
            Session.setFuelConsumptionScore(Session.getFuelConsumptionScore() + 1);

        } else                       // If the fuel consumption is "bad" and it has been good before(it should not count the same fuel count several times)
            Session.setFuelConsumptionScore(Session.getFuelConsumptionScore() - 1);

    }

    protected static void updateBrakeScore(int brake) {

        if(brake == 0) { // If the brake is in a "Released" state.

            shouldDecreaseBrakeScore = true; // The car has released the brake before it brakes again
            brakeTimer.start();              // Start the timer

        } else { // If the brake is in "Pressed state"

            if(brakeTimer != null)
                brakeTimer.cancel();

            if (shouldDecreaseBrakeScore) // If the brake is active and it has been released between brakes(it should not count the same brake several times)
                Session.setBrakeScore(Session.getBrakeScore() - 1);

        }

    }

    protected static void updateDriverDistractionLevelScore(int distractionLevel) {

        if(distractionLevel == 3 && lastDistractionLevel < 3)
            Session.setDriverDistractionLevelScore(Session.getDriverDistractionLevelScore() - 1);

        if(distractionLevel == 4 && lastDistractionLevel < 4)
            Session.setDriverDistractionLevelScore(Session.getDriverDistractionLevelScore() - 2);

        if(distractionLevel == 0 && lastDistractionLevel != 0)
            Session.setDriverDistractionLevelScore(Session.getDriverDistractionLevelScore() + 1);

        lastDistractionLevel = distractionLevel;

    }

}