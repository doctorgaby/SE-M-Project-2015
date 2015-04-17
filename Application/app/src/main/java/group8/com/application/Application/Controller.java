package group8.com.application.Application;


import group8.com.application.Model.DataList;

public abstract class Controller {

    /* Methods for MeasurementsFactory */
    protected static void eventSpeedChanged(double speed) {
        Session.setSpeed(speed);
        GradingSystem.updateSpeedScore(speed);
    }

    protected static void eventFuelConsumptionChanged(double fuelConsumption) {
        Session.setFuelConsumption(fuelConsumption);
        GradingSystem.updateFuelConsumptionScore(fuelConsumption);
    }

    protected static void eventBrakeChanged(int brake) {
        Session.setBrake(brake);
        GradingSystem.updateBrakeScore(brake);
    }

    protected static void eventDriverDistractionLevelChanged(int driverDistractionLevel) {
        Session.setDriverDistractionLevel(driverDistractionLevel);
        GradingSystem.updateDriverDistractionLevelScore(driverDistractionLevel);
    }
    /* END - Methods for MeasurementsFactory */

    public static DataList eventGetMeasurements() {
        return DBHandler.getMeasurements(Session.getUserName());
    }

    public static DataList eventGetFilteredMeasurements(int start, int stop) {
        return DBHandler.getFilteredMeasurements(Session.getUserName(), start, stop);
    }

    public static DataList eventGetPoints() {
        return DBHandler.getPoints(Session.getUserName());
    }

    public static DataList eventGetFilteredPoints(int start, int stop) {
        return DBHandler.getFilteredPoints(Session.getUserName(), start, stop);
    }

}