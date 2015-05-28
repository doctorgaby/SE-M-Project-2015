package group8.com.application.Application;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;

import group8.com.application.Model.ConstantData;

/**
 * Class representing the logic for the grading of drivers performance.
 * To start the grading system, the method startGrading() has to be called.
 */
public abstract class GradingSystem {

    /* Private variables */
    private static int lastDistractionLevel;                // Stores the current distraction level(updates when the distraction level is changed)
    private static boolean running = false;                  // Flag used in the threads loop
    private static CountDownTimer brakeTimer;               // Timer used for grading the braking
    private static CountDownTimer speedTimer;
    private static CountDownTimer fuelTimer;
    private static CountDownTimer distractionTimer;
    private static ArrayList<Double> tempSpeedList;
    private static ArrayList<Double> tempFuelList;
    private static ArrayList<Integer> tempBrakeList;
    private static ArrayList<Integer> tempDistractionList;
    private static int currentDistraction;

    /**
     * Start the grading system.
     * */
    protected static void startGradingSystem() {

        if(MeasurementFactory.isMeasuring()) {

            running = true;

            // Create a new countdown. When the countdown has finished, the braking score increases by 1.
            tempBrakeList =  new ArrayList<>();
            brakeTimer = new CountDownTimer(5000, 5000) {

                public void onTick(long millisUntilFinished) {}
                public void onFinish() {
                    if (Session.isMeasuring) {
                        int currentScore = Session.getBrakeScore();
                        if (tempBrakeList.size() != 0)
                            Session.setBrake(brakingAverage());
                        int newScore = currentScore + evaluateBrake();
                        if (newScore <= 100 && newScore >= 0) {
                            Session.setBrakeScore(newScore);
                        }
                        tempBrakeList = new ArrayList<>();
                    }
                    brakeTimer.start();
                }
            }.start();

            // Create a new countdown. When the countdown has finished, the speed score increases by 1 if the speed changes are reasonable.
            tempSpeedList =  new ArrayList<>();
            speedTimer = new CountDownTimer(10000, 5000) {
                int points = 0;
                public void onTick(long millisUntilFinished) {
                    if (Session.isMeasuring) {
                        points = points + evaluateSpeedAverage();
                        if (tempSpeedList.size() != 0)
                            Session.setSpeed(tempSpeedList.get(tempSpeedList.size() - 1)); //Sets the speed measurement every 5 seconds.
                        tempSpeedList = new ArrayList<>();
                    }
                }

                public void onFinish() {
                    if (Session.isMeasuring) {
                        int currentScore = Session.getSpeedScore();
                        int newScore = currentScore + points;
                        if (newScore>100)
                            newScore = 100;
                        else if (newScore<0)
                            newScore = 0;
                        if (newScore >= 0 && newScore <= 100) {
                            Session.setSpeedScore(newScore); //Sets the speed points every 10 seconds.
                        }
                        points = 0;
                    }
                    speedTimer.start();
                }
            }.start();

            // Create a new countdown. When the countdown has finished, the fuel score increases by 1
            // if the fuel changes are reasonable.
            tempFuelList =  new ArrayList<>();
            fuelTimer = new CountDownTimer(5000, 5000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    if (Session.isMeasuring) {
                        int currentScore = Session.getFuelConsumptionScore();
                        if (tempFuelList.size() != 0)
                            Session.setFuelConsumption(tempFuelList.get(tempFuelList.size() - 1));
                        int newScore = currentScore + evaluateFuel();
                        if (newScore <= 100 && newScore >= 0)
                            Session.setFuelConsumptionScore(newScore);
                        tempFuelList = new ArrayList<>();
                    }
                    fuelTimer.start();
                }
            }.start();

            // Create a new countdown. When the countdown has finished, the distraction score increases by 1
            // if the fuel changes are reasonable.
            tempDistractionList =  new ArrayList<>();
            distractionTimer = new CountDownTimer(5000, 1000) {
                public void onTick(long millisUntilFinished) {
                    tempDistractionList.add(currentDistraction);
                }

                public void onFinish() {
                    if (Session.isMeasuring) {
                        int currentScore = Session.getDriverDistractionLevelScore();
                        if (tempDistractionList.size() != 0)
                            Session.setDriverDistractionLevel(tempDistractionList.get(tempDistractionList.size() - 1));
                        int newScore = currentScore + evaluateDistraction();
                        if (newScore>100)
                            newScore = 100;
                        if (newScore<0)
                            newScore = 0;
                        if (newScore <= 100 && newScore >= 0)
                            Session.setDriverDistractionLevelScore(newScore);
                        tempDistractionList = new ArrayList<>();
                    }
                    distractionTimer.start();
                }
            }.start();

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

        if(running&&Session.isMeasuring) {
            tempSpeedList.add(speed);
        }

    }

    /**
     * - 1 point when the fuel consumption is below 60.
     * + 1 point if the fuel consumption is above 60.
     * This updates every time the signal gets activated ie. when the fuel consumption changes.
     * */
    protected static void updateFuelConsumptionScore(double fuelConsumption) {

        if(running&&Session.isMeasuring) {
            tempFuelList.add(fuelConsumption);
        }

    }

    /**
     * - 1 point when the brake is activated.
     * + 1 point every 10 seconds the brake is not activated.
     * */
    protected static void updateBrakeScore(int brake, boolean timerFinished) {

        if(running&&Session.isMeasuring) {
            tempBrakeList.add(brake);
        }

    }

    /**
     * + 1 point when the distraction level changes to standstill(0).
     * - 1 point when the distraction level changes to high(3).
     * - 2 points when the distraction level changes to very high(4).
     * */
    protected static void updateDriverDistractionLevelScore(int distractionLevel) {

        if(running&&Session.isMeasuring) {
/*
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
                if (newScore>=0&&newScore<=100) {
                    Session.setDriverDistractionLevel(distractionLevel);
                    Session.setDriverDistractionLevelScore(newScore);
                }
            }
*/
            currentDistraction = distractionLevel;
            tempDistractionList.add(distractionLevel);
        }

    }

    /**
     * Function to evaluate if the speed has too many numbers out of range. A number is considered out of range if it has 5km/h
     * out either way from the average of the list.
     * The function will return +2 if less than 2 out of 50 possible numbers are out of range, +1 if 2-4 are out of range and -1 otherwise.
     * @return +2/+1/-1/-2 depending on the amount of numbers out of range (<5/>5 respectively) or if the user exceeded the speed limit.
     */
    private static int evaluateSpeedAverage ()
    {
        //finds the average of the list.
        double total = 0;
        for (int i=0; i < tempSpeedList.size(); i++)
        {
            total = total + tempSpeedList.get(i);
            if (tempSpeedList.get(i) >120){
                return -2;
            }
        }
        double average = total /tempSpeedList.size();

        int outOfRange = 0;

        for (int i=0; i < tempSpeedList.size(); i++)
        {
            if (tempSpeedList.get(i) < average-ConstantData.outOfRangeSpeed || tempSpeedList.get(i) > average+ConstantData.outOfRangeSpeed)
                outOfRange= outOfRange+1;
        }

        if (outOfRange<=ConstantData.outOfRangeSpeedLowerMargin)
            return 2;
        else if (outOfRange <=ConstantData.outOfRangeSpeedMiddleMargin)
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

    /**
     * Function to find the average amongst the breaking measurements in the tempBrakeList
     * @return 1 or 0 representing the average of braking during the time frame.
     */
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

    /**
     * Function to evaluate the braking. If the total measurement are greater or equal to 25 it returns -1
     * if they are lower than 25 it returns a 1.
     * @return -1 or 1 depending if the braking is good or bad.
     */
    private static int evaluateBrake () {
        int total = 0;
        for (int i=0; i<tempBrakeList.size();i++) {
            Log.d("Brake Value:", "" + tempBrakeList.get(i));
            total = total+tempBrakeList.get(i);
        }
        if (total >= 25)
            return -1;
        else
            return 1;
    }

    /**
     * Function to evaluate the distraction. If the list contains a very high distraction (4) then it returns -2,
     * if it has a high distraction (3) it returns a -1 if it has more than 2 seconds of medium distraction (2) or
     * more than 3 seconds of low distraction (1) it returns -1, otherwise it returns 1.
     * @return -2, -1 or 1 depending if the distraction is good, bad or really bad.
     */
    private static int evaluateDistraction() {
        int total1=0;
        int total2=0;
        for (int i=0;i<tempDistractionList.size();i++) {
            int value = tempDistractionList.get(i);
            Log.d("Distraction Value:", "" + value);
            if (value == 4) {
                return -2;
            } else if (value == 3) {
                return -1;
            } else if (value == 2) {
                total2 = total2+1;
            } else if (value==1) {
                total1 = total1+1;
            }
        }
        if (total2>2) {
            return -1;
        } else if (total1>3) {
            return -1;
        } else {
            return 1;
        }
    }
}