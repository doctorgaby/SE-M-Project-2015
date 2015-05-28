package group8.com.application.Application;

import android.os.CountDownTimer;

import org.json.JSONObject;

import group8.com.application.Model.DataList;

public abstract class Session {

    // Username for the user logged in for the current session.
    private static String userName = "";

    //DataList of points for the session.
    public static DataList currentPoints = new DataList("p");

    //DataList of measurements for the session.
    public static DataList currentMeasurements = new DataList("m");

    //Boolean variable to know when the session is paused.
    private static boolean paused = false;

    //Boolean variable to know if the user wants to finish the drive.
    public static boolean doFinish = false;

    //Boolean variable to know if the toast should be shown at the beggining of the session.
    public static boolean showToast = true;

    //Boolean variable to know if the Simulator has been initialized and is measuring.
    public static boolean isMeasuring = false;

    //Count Down timer used to check if the Simulator is still measuring. (Note: works only for Linux simulator).
    private static CountDownTimer timer = new CountDownTimer(101, 101) {
        public void onTick(long millisUntilFinished) {}
        public void onFinish() {
            isMeasuring = false;
        }
    };

    //Functions considering the timer and whether it should be initialized or continued.
    public static void startTimer () {
        timer.start();
    }

    public static void continueTimer () {
        isMeasuring = true;
        timer.start();
    }

    //Function to get the currently logged in username.
    public static String getUserName() {
        return userName;
    }

    //Pause the Session
    public static void pause () {
        paused = true;
    }

    //Finish Drive.
    public static void finishDrive () {
        paused = false;
    }

    //Check if the Session is paused.
    public static boolean isPaused() {
        return paused;
    }

    public static void restart() {
        currentPoints = new DataList("p");
        currentMeasurements = new DataList("m");
        userName = "";
    }

    //Restart the session with the given username.
    public static void restart(String username) {
        userName = username;
        currentPoints = new DataList("p");
        currentMeasurements = new DataList("m");
    }

    /* GRADING AND POINTS RELATED SETTERS */
    public static void setSpeedScore(int score) {

        int timeInSeconds = (int) (System.currentTimeMillis() / 1000);
        currentPoints.setSpeed(timeInSeconds, score);

    }

    public static void setFuelConsumptionScore(int score) {

        int timeInSeconds = (int) (System.currentTimeMillis() / 1000);
        currentPoints.setFuelConsumption(timeInSeconds, score);

    }

    public static void setBrakeScore(int score) {

        int timeInSeconds = (int) (System.currentTimeMillis() / 1000);
        currentPoints.setBrake(timeInSeconds, score);

    }

    public static void setDriverDistractionLevelScore(int score) {

        int timeInSeconds = (int) (System.currentTimeMillis() / 1000);
        currentPoints.setDriverDistractionLevel(timeInSeconds, score);

    }
    /* END - GRADING AND POINTS RELATED SETTERS */


    /* GRADING AND POINTS RELATED GETTERS */

    /**
     * Get the speed score for the current session.
     *
     * @return The speed score for the current session.
     */
    public static int getSpeedScore() {

        return currentPoints.getSpeed(currentPoints.getSpeedSize() - 1).getValue();

    }

    /**
     * Get the fuel consumption score for the current session.
     *
     * @return The fuel consumption score for the current session.
     */
    public static int getFuelConsumptionScore() {

        return currentPoints.getFuelConsumption(currentPoints.getFuelConsumptionSize() - 1).getValue();

    }

    /**
     * Get the braking score for the current session.
     *
     * @return The braking score for the current session.
     */
    public static int getBrakeScore() {

        return currentPoints.getBrake(currentPoints.getBrakeSize() - 1).getValue();

    }

    /**
     * Get the distraction level score for the current session.
     *
     * @return The distraction level score for the current session.
     */
    public static int getDriverDistractionLevelScore() {

        return currentPoints.getDriverDistractionLevel(currentPoints.getDriverDistractionLevelSize() - 1).getValue();

    }

    /**
     * Get the Json list of the points list.
     * @return JSON list of the points.
     */
    public static JSONObject getPointsJson() {
        return currentPoints.getJson();
    }
    /* END - GRADING AND POINTS RELATED GETTERS */


    /* MEASUREMENT RELATED SETTERS */
    public static void setSpeed(double speed) {

        int timeInSeconds = (int) (System.currentTimeMillis() / 1000);
        currentMeasurements.setSpeed(timeInSeconds, speed);

    }

    public static void setFuelConsumption(double fuelConsumption) {

        int timeInSeconds = (int) (System.currentTimeMillis() / 1000);
        currentMeasurements.setFuelConsumption(timeInSeconds, fuelConsumption);

    }

    public static void setBrake(int brake) {

        int timeInSeconds = (int) (System.currentTimeMillis() / 1000);
        currentMeasurements.setBrake(timeInSeconds, brake);

    }

    public static void setDriverDistractionLevel(int driverDistractionLevel) {

        int timeInSeconds = (int) (System.currentTimeMillis() / 1000);
        currentMeasurements.setDriverDistractionLevel(timeInSeconds, driverDistractionLevel);

    }
    /* END - MEASUREMENT RELATED SETTERS */


    /* MEASUREMENT RELATED GETTERS */
    public static int getLastSpeed() {

        return currentMeasurements.getSpeed(currentMeasurements.getSpeedSize() - 1).getValue();

    }

    public static int getLastFuelConsumption() {

        return currentMeasurements.getFuelConsumption(currentMeasurements.getFuelConsumptionSize() - 1).getValue();

    }

    public static int getLastBrake() {

        return currentMeasurements.getBrake(currentMeasurements.getBrakeSize() - 1).getValue();

    }

    public static int getLastDriverDistractionLevel() {

        return currentMeasurements.getDriverDistractionLevel(currentMeasurements.getDriverDistractionLevelSize() - 1).getValue();

    }

    public static JSONObject getMeasurementsJson() {
        return currentMeasurements.getJson();
    }

    /* MEASUREMENT RELATED GETTERS */

}