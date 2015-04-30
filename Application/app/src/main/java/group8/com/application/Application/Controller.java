package group8.com.application.Application;

import android.content.Context;
import android.content.Intent;

import group8.com.application.Application.Database.DBHandler;
import group8.com.application.Model.DataList;
import group8.com.application.UI.MainView;
import group8.com.application.alert.BrakesActivity;
import group8.com.application.alert.DistractionActivity;
import group8.com.application.alert.FuelActivity;
import group8.com.application.alert.SpeedActivity;

public abstract class Controller {

    /* Methods for MeasurementsFactory */
    protected static void eventSpeedChanged(double speed) {
        //Session.setSpeed(speed);
        GradingSystem.updateSpeedScore(speed);
    }

    protected static void eventFuelConsumptionChanged(double fuelConsumption) {
        //Session.setFuelConsumption(fuelConsumption);
        GradingSystem.updateFuelConsumptionScore(fuelConsumption);
    }

    protected static void eventBrakeChanged(int brake) {
        //Session.setBrake(brake);
        GradingSystem.updateBrakeScore(brake, false);
    }

    protected static void eventDriverDistractionLevelChanged(int driverDistractionLevel) {
        //Session.setDriverDistractionLevel(driverDistractionLevel);
        GradingSystem.updateDriverDistractionLevelScore(driverDistractionLevel);
    }
    /* END - Methods for MeasurementsFactory */

    /* Methods for login and register*/
    public static int attemptLogin(String tag, String username, String password) {
        return DBHandler.attemptLogin(tag, username, password);
    }

    public static int registerUser(String username, String password) {
        return DBHandler.registerUser(username, password);
    }
    /* END - Methods for login and register*/

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

    public static void eventSetMeasuremtents () {
        DBHandler.setMeasurements(Session.getUserName());
    }


 /*
 *
 * The following 4 methods are used to call an alerting activity defined in the alert package
 *
 */

    Context context = MainView.getContext();


    public void speedAlert() {

        Intent intent = new Intent(context, SpeedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }
    public void brakesAlert() {

        
        Intent intent = new Intent(context, BrakesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }
    public void fuelAlert() {

        Intent intent = new Intent(context, FuelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }
    public void DistractionAlert() {

        Intent intent = new Intent(context, DistractionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
 }
    //
    public void setMainView() {

        Intent intent = new Intent(context, MainView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }
 /*
 * The following method are is used to call the alternative visualActivity
 */

 /*   public void visualData() {


        Intent intent = new Intent(context, VisualizationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
*/

    }

