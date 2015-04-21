package group8.com.application.Application;

import group8.com.application.Application.Database.DBHandler;
import group8.com.application.Model.DataList;

public abstract class Controller {

    /* Methods for MeasurementsFactory */
    protected static void eventSpeedChanged(double speed) {
        GradingSystem.updateSpeedScore(speed);
    }

    protected static void eventFuelConsumptionChanged(double fuelConsumption) {
        GradingSystem.updateFuelConsumptionScore(fuelConsumption);
    }

    protected static void eventBrakeChanged(int brake) {
        GradingSystem.updateBrakeScore(brake, false);
    }

    protected static void eventDriverDistractionLevelChanged(int driverDistractionLevel) {
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



    /**
 * /**
 * Created by Nicholas on 19/04/2015.
 *
 * The following 4 methods are used to call an alerting activity defined in the alert package


    public void speedAlert() {

        Intent nextScreen = new Intent(getApplicationContext(), SpeedActivity.class);

        startActivity(nextScreen);
    }



    public void brakesAlert() {

        Intent nextScreen = new Intent(getApplicationContext(), BrakesActivity.class);

        startActivity(nextScreen);
    }
    public void distractionAlert() {

        Intent nextScreen = new Intent(getApplicationContext(), DistractionActivity.class);

        // starting new activity
        startActivity(nextScreen);
    }
    public void fuelAlert() {

        Intent nextScreen = new Intent(getApplicationContext(), FuelActivityActivity.class);

        // starting new activity
        startActivity(nextScreen);
    }
     */

}