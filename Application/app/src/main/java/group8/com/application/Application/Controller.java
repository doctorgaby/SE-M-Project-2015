package group8.com.application.Application;

import android.content.Context;
import android.content.Intent;

import group8.com.application.Application.Database.DBHandler;
import group8.com.application.Model.DataList;
import group8.com.application.alert.BrakesActivity;
import group8.com.application.alert.DistractionActivity;
import group8.com.application.alert.FuelActivity;
import group8.com.application.alert.SpeedActivity;

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


 /*
 *
 * The following 4 methods are used to call an alerting activity defined in the alert package
 * 
 * Since controller class doesn't extend activity calling these methods on a context object 
 * thus we need to define a custom constructor
 */

    public Controller (Context context) {  
        mContext = context;
    }


    private Context mContext;


    public void speedAlert() {

        Intent intent = new Intent(mContext, SpeedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);

    }
    public void brakesAlert() {

        
        Intent intent = new Intent(mContext, BrakesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
    public void fuelAlert() {

        Intent intent = new Intent(mContext, FuelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
    public void DistractionAlert() {

        Intent intent = new Intent(mContext, DistractionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
 }

}
