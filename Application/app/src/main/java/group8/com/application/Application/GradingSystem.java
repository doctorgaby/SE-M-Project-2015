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
    static private boolean running = false;                  // Flag used in the threads loop
    static private CountDownTimer brakeTimer;               // Timer used for grading the braking

    /**
     * Start the grading system.
     * */
    public static void startGradingSystem() {

        if(MeasurementFactory.isMeasuring()) {
            running = true;
            MeasurementFactory.startMeasurements();

            if (brakeTimer == null) {
                brakeTimer = new CountDownTimer(10000, 1000) { // Create a new countdown. When the countdown has finished, the braking score increases by 1.

                    public void onTick(long millisUntilFinished) {
                        System.out.println("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        updateBrakeScore(Session.getLastBrake(), true);
                    }

                };
            }

        }

    }

    /**
     * Pause the grading system.
     * */
    public static void stopGradingSystem() {

        running = false;
        brakeTimer.cancel();

    }

    /**
     * Check if the GradingSystem is grading.
     *
     * @return true if the GradingSystem is grading, and false if it is not.
     * */
    public static boolean isGrading() {

        return running;

    }

    /**
     * Sets the score to the current speed.
     * */
    protected static void updateSpeedScore(double speed) {

        if(running) {

            // Store the current score and the new score
            int currentScore = Session.getSpeedScore();
            int newScore = currentScore;

            // Evaluate the measurements

            // Update the lists of measurements and scores
            if(newScore != currentScore) {
                Session.setSpeed(speed);
                Session.setSpeedScore(newScore); // update view here
            }

        }

    }

    /**
     * - 1 point when the fuel consumption is below 60.
     * + 1 point if the fuel consumption is above 60.
     * This updates every time the signal gets activated ie. when the fuel consumption changes.
     * */
    protected static void updateFuelConsumptionScore(double fuelConsumption) {

        if(running) {

            // Store the current score and the new score
            int currentScore = Session.getFuelConsumptionScore();
            int newScore = currentScore;

            // Evaluate the measurements
            if (fuelConsumption > 60.0) { // If the fuel consumption is "good".
                newScore = currentScore + 1;

            } else                        // If the fuel consumption is "bad" and it has been good before(it should not count the same fuel count several times)
                newScore = currentScore - 1;

            // Update the lists of measurements and scores
            if(newScore != currentScore) {
                Session.setFuelConsumption(fuelConsumption);
                Session.setFuelConsumptionScore(newScore);
            }

        }

    }

    /**
     * - 1 point when the brake is activated.
     * + 1 point every 10 seconds the brake is not activated.
     * */
    protected static void updateBrakeScore(int brake, boolean timerFinished) {

        if(running) {

            // Store the current score and the new score
            int currentScore = Session.getBrakeScore();
            int newScore = currentScore;

            // Evaluate the measurements
            if (brake == 0) {                    // If the brake is in a "Released" state.

                shouldDecreaseBrakeScore = true; // The car has released the brake before it brakes again
                brakeTimer.start();              // Start the timer

            } else {                             // If the brake is in "Pressed state"

                if (brakeTimer != null)
                    brakeTimer.cancel();

                if (shouldDecreaseBrakeScore)    // If the brake is active and it has been released between brakes(it should not count the same brake several times)
                    newScore = currentScore - 1;

            }

            if(timerFinished)
                newScore = currentScore + 1;

            // Update the lists of measurements and scores
            if(newScore != currentScore) {
                Session.setBrake(brake);
                Session.setBrakeScore(newScore);
            }

        }

    }

    /**
     * + 1 point when the distraction level changes to standstill(0).
     * - 1 point when the distraction level changes to high(3).
     * - 2 points when the distraction level changes to very high(4).
     * */
    protected static void updateDriverDistractionLevelScore(int distractionLevel) {

        if(running) {

            // Store the current score and the new score
            int currentScore = Session.getDriverDistractionLevelScore();
            int newScore = currentScore;

            // Evaluate the measurements
            if (distractionLevel == 3 && lastDistractionLevel < 3)
                newScore = currentScore - 1;

            if (distractionLevel == 4 && lastDistractionLevel < 4)
                newScore = currentScore - 2;

            if (distractionLevel == 0 && lastDistractionLevel != 0)
                newScore = currentScore + 1;

            lastDistractionLevel = distractionLevel;

            // Update the lists of measurements and scores
            if(newScore != currentScore) {
                Session.setDriverDistractionLevel(distractionLevel);
                Session.setDriverDistractionLevelScore(newScore);
            }

        }

    }

}