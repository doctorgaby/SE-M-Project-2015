package group8.com.application.Application;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import group8.com.application.Application.Database.DBHandler;
import group8.com.application.Model.DataList;
import group8.com.application.UI.NotificationSystem;
import group8.com.application.UI.mainView.menuView;

/**
 * Main controller for the application. Acts as a mediator between different classes.
 * */
public class Controller {

    private static Controller instance = null;

    public static Context context = menuView.getContext();


    /**
     * Private constructor for the Controller class, which prevents other classes from instantiating a new instance of the Controller class.
     * */
    private Controller() {
        super();
    }

    /**
     * @return an instance of the Controller class. This is to limit the amount of instances of the class to one, according to the singleton principle.
     * */
    public static Controller getInstance() {

        if(instance == null)
            instance = new Controller();

        return instance;

    }



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

    public static void initMeasurements() {
        MeasurementFactory.initMeasurements();
    }

    public static boolean isMeasuring() {
        return MeasurementFactory.isMeasuring();
    }

    public static boolean isReceivingSignal() {
        return true;
    }

    public static double getCurrentSpeed() {
        return MeasurementFactory.getSpeed();
    }

    public static double getCurrentFuelConsumption() {
        return MeasurementFactory.getFuelConsumption();
    }

    public static int getCurrentBrake() {
        return MeasurementFactory.getBrake();
    }

    public static int getCurrentDistractionLevel() {
        return MeasurementFactory.getDistractionLevel();
    }

/* END - Methods for MeasurementsFactory */



/* Methods for GradingSystem */
    public static void startGrading() {

        MeasurementFactory.startMeasurements();
        GradingSystem.startGradingSystem();

    }

    public static void stopGrading() {
        GradingSystem.stopGradingSystem();
        MeasurementFactory.pauseMeasurements();
        Session.pause();
    }

    public static void finishGrading (boolean save) {
        Session.finishDrive();
        if (save) {
            eventSetMeasuremtents();
            eventSetPoints();
            eventSetLastScores();
        }
    }

    public static boolean isGrading() {
        return GradingSystem.isGrading();
    }

    public static int getSpeedScore() {
        return Session.getSpeedScore();
    }

    public static int getFuelConsumptionScore() {
        return Session.getFuelConsumptionScore();
    }

    public static int getBrakeScore() {
        return Session.getBrakeScore();
    }

    public static int getDriverDistractionLevelScore() {
        return Session.getDriverDistractionLevelScore();
    }
/* END - Methods for GradingSystem */



/* Methods for login and register*/
    
    public static JSONObject attemptLogin(String tag, String username, String password) {
        return DBHandler.attemptLogin(tag, username, password);
    }

    public static JSONObject registerUser(String username, String password) {
        return DBHandler.registerUser(username, password);
    }
/* END - Methods for login and register*/



/* Methods for the DBHandler */
    public static DataList eventGetMeasurements() {
        return DBHandler.getMeasurements(Session.getUserName());
    }

    public static DataList eventGetFilteredMeasurements(int start, int stop) {
        return DBHandler.getFilteredMeasurements(Session.getUserName(), start, stop);
    }

    public static DataList eventGetPoints() {
        return DBHandler.getPoints(Session.getUserName());
//        return Session.currentPoints;
    }

    public static DataList eventGetFilteredPoints(int start, int stop) {
        Log.d("start, stop", start + " , " + stop);
        return DBHandler.getFilteredPoints(Session.getUserName(), start, stop);
    }

    /*Daily message Method */

    public static Toast eventGetCustomToast(Context context, View view){
        return NotificationSystem.customToast(context, view);
    }

    public static Toast eventGetMedalToast(Context context, View view){
        return NotificationSystem.medalUpdateMessage(context, view);
    }

    public static HashMap<String, Integer> eventGetFinalPoints (){
        return DBHandler.getFinalScore(Session.getUserName());
    }

    public static boolean updateStatus(String s, Context context){
        return MedalsLogic.medalStatusUpdate(s, context);
    }

    public static boolean getMedalStatus(String s, Context context){
        return MedalsLogic.medalStatus(s, context);
    }

    public static void eventSetMeasuremtents () {
        DBHandler.setMeasurements(Session.getUserName());
    }

    public static void eventSetPoints () {
        DBHandler.setPoints(Session.getUserName());
    }

    public static void eventSetLastScores () {DBHandler.setScores(Session.getUserName());}
/* END - Methods for the DBHandler */



/* Methods for AlertSystem */
    public static boolean evaluateSpeedAlert() {
        return AlertSystem.evaluateSpeed();
    }

    public static boolean evaluateFuelConsumptionAlert() {
        return AlertSystem.evaluateFuelConsumption();
    }

    public static boolean evaluateBrakeAlert() {
        return AlertSystem.evaluateBrake();
    }

    public static boolean evaluateDriverDistractionLevelAlert() {
        return AlertSystem.evaluateDriverDistractionLevel();
    }
/* END - Methods for AlertSystem */



 /*
 * The following method is used to display the graph
 */


    public static List<String> eventGetAllFriends () {
        return DBHandler.getAllFriends(Session.getUserName());
    }

    public static ArrayList<HashMap<String, String>> getAllRankings () {
        return DBHandler.getAllRankings();
    }

    public static ArrayList<HashMap<String, String>> getFriendsRankings() {
        return DBHandler.getFriendsRankings(Session.getUserName());
    }

    public static JSONObject addFriend (String friend) {
        return DBHandler.addFriend(friend);
    }

    public static JSONObject removeFriend (String friend) {
        return DBHandler.removeFriend(friend);
    }

}

