package group8.com.application.UI;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.Model.DataList;
import group8.com.application.R;

/**
 * Created by Kristiyan on 4/22/2015.
 */
public class NotificationSystem{

    private static DataList data;
    private static int speedScore;
    private static int brakeScore;
    private static int DDLScore;
    private static int fuelScore;



    public static Toast customToast(Context context, View view) {

        int duration = Toast.LENGTH_LONG;

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(view);

        TextView myMessage = (TextView)view.findViewById(R.id.text);
        myMessage.setText(setMessage());

        ImageView myImage = (ImageView)view.findViewById(R.id.img);
        setImage(myImage);

        if (evaluateList() != "terminate") {
            toast.show();
        }

        return toast;

    }

    private static CharSequence setMessage(){

        CharSequence msg = message(evaluateList(), getPosition());
        return msg;
    }

    /**Utility Method for message(String, int)
     *
     *  method for checking on which
     *  measurement the user did
     *  the worst
     *
     * @return a a string of the measurement with the worst score;
     */
    private static String evaluateList(){

/*
        data = Controller.eventGetPoints();
        speedScore = data.getMaxSpeed();
        brakeScore = data.getMaxBrake();
        DDLScore = data.getMaxDriverDistractionLevel();
        fuelScore = data.getMaxFuelConsumption();
*/
/*  Session test code

        speedScore = Session.getSpeedScore();
        brakeScore = Session.getBrakeScore();
        DDLScore = Session.getDriverDistractionLevelScore();
        fuelScore = Session.getFuelConsumptionScore();
*/

//  Hardcoded testing values
        speedScore = 20;
        brakeScore = 40;
        DDLScore = 50;
        fuelScore = 60;
//

        if (speedScore < brakeScore && speedScore < DDLScore && speedScore < fuelScore){
            return ConstantData.TAG_SPEED;
        }
        if (brakeScore < speedScore && brakeScore < DDLScore && brakeScore < fuelScore) {
            return ConstantData.TAG_BRAKE;
        }
        if (DDLScore < speedScore && DDLScore < brakeScore && DDLScore < fuelScore){
            return ConstantData.TAG_DISTRACTION;
        }
        if (fuelScore < speedScore && fuelScore < brakeScore && fuelScore < DDLScore){
            return ConstantData.TAG_FUEL;
        }

        return "terminate";

    }

    /** Utility Method for message(String, int)
     *
     * determines with how many
     * points the lowest measurement is
     * under the average and returns an
     * integer which is used as a position
     * identifier
     *
     * @return the approximate position
     */

    private static int getPosition(){
/*
        data = Controller.eventGetPoints();
        speedScore =  data.getMaxSpeed();
        brakeScore = data.getMaxBrake();
        DDLScore = data.getMaxDriverDistractionLevel();
        fuelScore = data.getMaxFuelConsumption();
*/
//  Hardcoded test values
        speedScore = 20;
        brakeScore = 40;
        DDLScore = 50;
        fuelScore = 60;


        int looper = 4;
        int pos = 4;

        int avg = (brakeScore + DDLScore + fuelScore + speedScore) / 4;

        for( int i = 0; i <= looper; i++) {

            if (speedScore <= avg - rangeOne(i) && speedScore >= avg - rangeTwo(i)) {
                pos = i;
            }
            if (brakeScore <= avg - rangeOne(i) && brakeScore >= avg - rangeTwo(i)) {
                pos = i;
            }
            if (DDLScore <= avg - rangeOne(i) && DDLScore >= avg - rangeTwo(i)) {
                pos = i;
            }
            if (fuelScore <= avg - rangeOne(i) && fuelScore >= avg - rangeTwo(i)) {
                pos = i;
            }
        }
        return pos;
    }

    //loop support method, may get re-worked
    private static int rangeOne(int i){

        int set = 0;

        if (i == 1){
            set = 10;
        }
        if (i == 2){
            set = 20;
        }
        if (i == 3){
            set = 30;
        }
        if (i == 4){
            set = 40;
        }

        return set;
    }

    //loop support method, may get re-worked
    private static int rangeTwo(int i){

        int set = 10;

        if (i == 1){
            set = 20;
        }
        if (i == 2){
            set = 30;
        }
        if (i == 3){
            set = 40;
        }
        if (i ==4){
            set = 50;
        }


        return set;
    }

    /**
     * Accepts a string and int
     * to determine which message
     * to be chosen for the core
     * method of the system
     *
     * @param eval which measurement
     * @param pos position in array
     * @return the correct message
     */

    private static String message (String eval, int pos){

        String[] speed = {"Speed score is under avarage", "You need to watch your speed","Your results regarding speed are getting worse",
                          "You should pay more attention to your speed","Son. You got speeding issues"};
        String[] fuel = {"Fuel consumption score is under average","Your fuel upkeep is worsening", "Your results regarding fuel consumption are worrying",
                         "You should pay more attention to fuel consumption", "You are sufficient to burn the fuel of the entire planet"};
        String[] brake = {"Braking score is under average","It appears you brake too much", "Your brake score is getting worse",
                          "Your results regarding brakes are getting increasingly worse", "How about you lift your foot from the pedal every now and then"};
        String[] ddl = {"distraction level score is under average", "Keep focus on the road", "Your level of distraction is increasing",
                        "Your levels of distraction are worrying","Eyes on the road because i got my eyes on you ლ(ಠ_ಠლ)"};


        if (eval == ConstantData.TAG_SPEED){ return speed[pos]; }
        if (eval == ConstantData.TAG_BRAKE){ return brake[pos]; }
        if (eval == ConstantData.TAG_FUEL) { return fuel[pos]; }
        if (eval == ConstantData.TAG_DISTRACTION) { return ddl[pos]; }

        return "terminate";
    }

    public static void setImage(ImageView view){


        if (evaluateList() == ConstantData.TAG_SPEED) {
            view.setImageResource(R.drawable.speed_icon);
        }
        if (evaluateList() == ConstantData.TAG_BRAKE) {
            view.setImageResource(R.drawable.brakes_icon);
        }
        if (evaluateList() == ConstantData.TAG_FUEL) {
            view.setImageResource(R.drawable.gas_icon);
        }
        if (evaluateList() == ConstantData.TAG_DISTRACTION) {
            view.setImageResource(R.drawable.driver_icon);
        }

    }

}
