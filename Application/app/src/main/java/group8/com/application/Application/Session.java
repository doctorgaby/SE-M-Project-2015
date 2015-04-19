package group8.com.application.Application;

import group8.com.application.Model.DataList;

public abstract class Session {
    private static String userName = "";
    public static DataList currentPoints = new DataList("p");
    public static DataList currentMeasurements = new DataList("m");

    public static void setUserName(String username) {
        Session.userName = username;
    }

    public static String getUserName() {
        return userName;
    }

    public static boolean isLoggedIn() {
        return !userName.equals("");
    }

    public static void restart() {
        currentPoints = new DataList("p");
        currentMeasurements = new DataList("m");
        userName = "";
    }

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

        return currentPoints.getLastSpeed().getValue();

    }

    /**
     * Get the fuel consumption score for the current session.
     *
     * @return The fuel consumption score for the current session.
     */
    public static int getFuelConsumptionScore() {

        return currentPoints.getLastFuelConsumption().getValue();

    }

    /**
     * Get the braking score for the current session.
     *
     * @return The braking score for the current session.
     */
    public static int getBrakeScore() {

        return currentPoints.getLastBrake().getValue();

    }

    /**
     * Get the distraction level score for the current session.
     *
     * @return The distraction level score for the current session.
     */
    public static int getDriverDistractionLevelScore() {

        return currentPoints.getLastDriverDistractionLevel().getValue();

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

        return currentMeasurements.getLastSpeed().getValue();

    }

    public static int getLastFuelConsumption() {

        return currentMeasurements.getLastFuelConsumption().getValue();

    }

    public static int getLastBrake() {

        return currentMeasurements.getLastBrake().getValue();

    }

    public static int getLastDriverDistractionLevel() {

        return currentMeasurements.getLastDriverDistractionLevel().getValue();

    }
    /* MEASUREMENT RELATED GETTERS */

}