package group8.com.application.Application;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;

import group8.com.application.Model.ConstantData;

/**
 * Created by Hampus on 2015-03-18.
 *
 * Class representing the logic for the grading of drivers performance.
 * To start the grading system, the method startGrading() has to be called.
 */
public abstract class GradingSystem {

    /* Private variables */
    private static Controller controller = null;
    private static int lastDistractionLevel;                // Stores the current distraction level(updates when the distraction level is changed)
    private static boolean shouldDecreaseBrakeScore = true; // Is set to true when the brake is released
    private static boolean running = false;                  // Flag used in the threads loop
    private static CountDownTimer brakeTimer;               // Timer used for grading the braking
    private static CountDownTimer speedTimer;
    private static CountDownTimer fuelTimer;
    private static ArrayList<Double> tempSpeedList;
    private static ArrayList<Double> tempFuelList;
    private static ArrayList<Integer> tempBrakeList;
    /**
     * Start the grading system.
     * */
    protected static void startGradingSystem() {

        if(MeasurementFactory.isMeasuring()) {

            running = true;
            controller = Controller.getInstance();

            if(brakeTimer == null) {
                tempBrakeList =  new ArrayList<>();
                brakeTimer = new CountDownTimer(5000, 5000) { // Create a new countdown. When the countdown has finished, the braking score increases by 1.

                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        int currentScore = Session.getBrakeScore();
                        if (tempBrakeList.size()!=0)
                            Session.setBrake(brakingAverage());
                        Session.setBrakeScore(currentScore + evaluateBrake());
                        tempBrakeList =  new ArrayList<>();
                        brakeTimer.start();
                        // Updates the score and restarts the timer
                        //updateBrakeScore(0, true);
                    }

                }.start();
            }

            if (speedTimer == null) {
                tempSpeedList =  new ArrayList<>();
                speedTimer = new CountDownTimer(10000, 5000) { // Create a new countdown. When the countdown has finished, the speed score increases by 1 if the speed changes are reasonable.
                    int points = 0;
                    public void onTick(long millisUntilFinished) {
                        points = points + evaluateSpeedAverage();
                        if (tempSpeedList.size()!=0)
                            Session.setSpeed(tempSpeedList.get(tempSpeedList.size()-1)); //Sets the speed measurement every 5 seconds.
                        tempSpeedList =  new ArrayList<>();
                        if (tempSpeedList.size()!=0)
                            Log.d("Speed into Session.",""+ tempSpeedList.get(tempSpeedList.size()-1));
                    }

                    public void onFinish() {

                        int currentScore = Session.getSpeedScore();
                        Session.setSpeedScore(currentScore + points); //Sets the speed points every 10 seconds.
                        Log.d("Speedscore into Session",""+ currentScore + points);
                        //Restarts the timer
                        points = 0;
                        speedTimer.start();

                        Log.d("speedTimer", "Timer Finished");
                    }

                }.start();
            }
            if (fuelTimer == null) {
                tempFuelList =  new ArrayList<>();
                fuelTimer = new CountDownTimer(5000, 5000) { // Create a new countdown. When the countdown has finished, the fuel score increases by 1 if the fuel changes are reasonable.
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        int currentScore = Session.getFuelConsumptionScore();
                        if (tempFuelList.size()!=0)
                            Session.setFuelConsumption(tempFuelList.get(tempFuelList.size()-1));
                        Session.setFuelConsumptionScore(currentScore + evaluateFuel());
                        tempFuelList =  new ArrayList<>();
                        fuelTimer.start();
                    }

                }.start();
            }
        }

    }

    /**
     * Pause the grading system.
     * */
    protected static void stopGradingSystem() {

        running = false;
        brakeTimer.cancel();
        speedTimer.cancel();
        fuelTimer.cancel();
    }

    /**
     * Check if the GradingSystem is grading.
     *
     * @return true if the GradingSystem is grading, and false if it is not.
     * */
    protected static boolean isGrading() {

        return running;

    }

    /**
     * Sets the score to the current speed.
     * */
    protected static void updateSpeedScore(double speed) {

        if(running) {
            tempSpeedList.add(speed);
            //Log.d("Speed added: ", "value: " + speed);
            // Store the current score and the new score
            //int currentScore = Session.getSpeedScore();
            //int newScore = currentScore;

            // Evaluate the measurements

            // Update the lists of measurements and scores
            //if(newScore != currentScore) {
              //  Session.setSpeed(speed);
              //  Session.setSpeedScore(newScore); // update view here
            //}

            //if(speed > ConstantData.extremeSpeed) {
                // Extreme event
            //}

        }

    }

    /**
     * - 1 point when the fuel consumption is below 60.
     * + 1 point if the fuel consumption is above 60.
     * This updates every time the signal gets activated ie. when the fuel consumption changes.
     * */
    protected static void updateFuelConsumptionScore(double fuelConsumption) {

        if(running) {
            tempFuelList.add(fuelConsumption);
/*
            // Store the current score and the new score
            int currentScore = Session.getFuelConsumptionScore();
            int newScore = currentScore;

            // Evaluate the measurements
            if (fuelConsumption > ConstantData.goodFuelConsumption) { // If the fuel consumption is "good".
                newScore = currentScore + 1;

            } else                        // If the fuel consumption is "bad" and it has been good before(it should not count the same fuel count several times)
                newScore = currentScore - 1;

            // Update the lists of measurements and scores
            if(newScore != currentScore) {
                Session.setFuelConsumption(fuelConsumption);
                Session.setFuelConsumptionScore(newScore);
            }

            if(fuelConsumption >= ConstantData.extremeFuelConsumption) {
                // Extreme event
            }
*/
        }

    }

    /**
     * - 1 point when the brake is activated.
     * + 1 point every 10 seconds the brake is not activated.
     * */
    protected static void updateBrakeScore(int brake, boolean timerFinished) {

        if(running) {
            tempBrakeList.add(brake);
            // Store the current score and the new score
            /*int currentScore = Session.getBrakeScore();
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
            }*/
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

            if (distractionLevel == 4 && lastDistractionLevel < 4) {
                newScore = currentScore - 2;
            }

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

    /**
     * Function to evaluate if the speed has too many numbers out of range. A number is considered out of range if it has 5km/h
     * out either way from the average of the list.
     * The function will return +2 if less than 5 out of 50 possible numbers are out of range, +1 if 5-10 are out of range and -1 otherwise.
     * @return +2/-1 depending on the amount of numbers out of range (<5/>5 respectively).
     */
    private static int evaluateSpeedAverage ()
    {
        //finds the average of the list.
        double total = 0;
        for (int i=0; i < tempSpeedList.size(); i++)
        {
            total = total + tempSpeedList.get(i);
        }
        double average = total /tempSpeedList.size();

        int outOfRange = 0;

        for (int i=0; i < tempSpeedList.size(); i++)
        {
            if (tempSpeedList.get(i) < average-ConstantData.outOfRangeSpeed || tempSpeedList.get(i) > average+ConstantData.outOfRangeSpeed)
                outOfRange= outOfRange+1;
        }

        if (outOfRange<=ConstantData.outOfRangeLowerMargin)
            return 2;
        else if (outOfRange > ConstantData.outOfRangeLowerMargin && outOfRange <=ConstantData.outOfRangeMiddleMargin )
            return 1;
        else
            return -1;
    }

    /**
     * Function to evaluate the fuel Consumption.
     * @return +1/-1 depending if the values during 5 seconds go over the "good" fuel consumption.
     */
    private static int evaluateFuel() {
        for (int i =0; i<tempFuelList.size();i++) {
            if (tempFuelList.get(i)>ConstantData.goodFuelConsumption)
                return -1;
        }
        return 1;
    }

    private static int brakingAverage () {
        int total = 0;
        for (int i=0; i<tempBrakeList.size();i++) {
            total = total+tempBrakeList.get(i);
        }
        if (total >= tempBrakeList.size()/2)
            return 1;
        else
            return 0;
    }

    private static int evaluateBrake () {
        int total = 0;
        for (int i=0; i<tempBrakeList.size();i++) {
            total = total+tempBrakeList.get(i);
        }
        if (total >= 25)
            return -1;
        else
            return 1;
    }
}