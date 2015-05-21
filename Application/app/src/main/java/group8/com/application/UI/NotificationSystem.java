package group8.com.application.UI;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.R;

public class NotificationSystem{

    private static HashMap<String,Integer> list;
    private static int speedScore;
    private static int brakeScore;
    private static int DDLScore;
    private static int fuelScore;
    private static int avg = 50;
    private static boolean isPositive;

    public static Toast customToast(Context context, View view) {

        list = Controller.eventGetFinalPoints();
        speedScore = list.get(ConstantData.TAG_SPEED);
        brakeScore = list.get(ConstantData.TAG_BRAKE);
        DDLScore = list.get(ConstantData.TAG_DISTRACTION);
        fuelScore = list.get(ConstantData.TAG_FUEL);


        Log.d("user:", Session.getUserName());
        Log.d("attr",""+ speedScore);
        Log.d("attr",""+ brakeScore);
        Log.d("attr",""+ DDLScore);
        Log.d("attr",""+ fuelScore);
        int duration = Toast.LENGTH_LONG;

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(view);

        TextView myMessage = (TextView)view.findViewById(R.id.text);
        myMessage.setText(setMessage());

        ImageView myImage = (ImageView)view.findViewById(R.id.img);
        setImage(myImage, getPosition());

        if (evaluateList() != "terminate") {
            toast.show();
        }

        return toast;

    }
/*  >>>>>>>>>>>>> WORK IN PROGRESS <<<<<<<<<<<<<<<<<<<<<

    public static Toast medalUpdateMessage(Context context, View view){

        int speedScore = Session.getSpeedScore();
        int brakeScore = Session.getBrakeScore();
        int DDLScore = Session.getDriverDistractionLevelScore();
        int fuelScore = Session.getFuelConsumptionScore();

        int duration = Toast.LENGTH_LONG;

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(view);

        TextView myMessage = (TextView)view.findViewById(R.id.text);
        myMessage.setText(setMessage());

        ImageView myImage = (ImageView)view.findViewById(R.id.img);
        setImage(myImage, getPosition());

    }

    >>>>>>>>>>>>> WORK IN PROGRESS <<<<<<<<<<<<<<<<<<<<<
*/
    private static CharSequence setMedalMessage(){

        CharSequence msg = message(evaluateList(), getPosition());
        return msg;

    }

    //Checks if the score is positive or negative
    private static boolean checker(int sp, int br, int ddl, int fc){

        if (sp > avg && br > avg && ddl > avg && fc > avg){
            return true;
        }
        else if (sp == avg && br == avg && ddl == avg && fc == avg){
            return true;
        }
        else if (sp == avg && br > avg && ddl > avg && fc > avg){
            return true;
        }
        else if (sp > avg && br == avg && ddl > avg && fc > avg){
            return true;
        }
        else if (sp > avg && br > avg && ddl == avg && fc > avg){
            return true;
        }
        else if (sp > avg && br > avg && ddl > avg && fc == avg){
            return true;
        }
        else if (sp > avg && br > avg && ddl == avg && fc == avg){
            return true;
        }
        else if (sp == avg && br == avg && ddl > avg && fc > avg){
            return true;
        }
        else if (sp == avg && br > avg && ddl > avg && fc == avg){
            return true;
        }
        else if (sp > avg && br == avg && ddl == avg && fc > avg){
            return true;
        }
        else if (sp == avg && br > avg && ddl == avg && fc > avg){
            return true;
        }
        else if (sp > avg && br == avg && ddl > avg && fc == avg){
            return true;
        }
        else if (sp == avg && br == avg && ddl > avg && fc == avg){
            return true;
        }
        else if (sp == avg && br == avg && ddl == avg && fc > avg){
            return true;
        }
        else if (sp == avg && br > avg && ddl == avg && fc == avg){
            return true;
        }
        else if (sp > avg && br == avg && ddl == avg && fc == avg){
            return true;
        }

        return false;
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

        if (!checker(speedScore, brakeScore, DDLScore, fuelScore)) {

            isPositive = false;

            if (speedScore < brakeScore && speedScore < DDLScore && speedScore < fuelScore) {
                return ConstantData.TAG_SPEED;
            }
            if (brakeScore < speedScore && brakeScore < DDLScore && brakeScore < fuelScore) {
                return ConstantData.TAG_BRAKE;
            }
            if (DDLScore < speedScore && DDLScore < brakeScore && DDLScore < fuelScore) {
                return ConstantData.TAG_DISTRACTION;
            }
            if (fuelScore < speedScore && fuelScore < brakeScore && fuelScore < DDLScore) {
                return ConstantData.TAG_FUEL;
            }
            if (speedScore == fuelScore && speedScore == brakeScore && speedScore == DDLScore)
                return ConstantData.TAG_SPEED;
        } else {

            isPositive = true;

            if (speedScore > brakeScore && speedScore > DDLScore && speedScore > fuelScore) {
                return ConstantData.TAG_SPEED;
            }
            if (brakeScore > speedScore && brakeScore > DDLScore && brakeScore > fuelScore) {
                return ConstantData.TAG_BRAKE;
            }
            if (DDLScore > speedScore && DDLScore > brakeScore && DDLScore > fuelScore) {
                return ConstantData.TAG_DISTRACTION;
            }
            if (fuelScore > speedScore && fuelScore > brakeScore && fuelScore > DDLScore) {
                return ConstantData.TAG_FUEL;
            }
            if (DDLScore == avg && speedScore == avg && brakeScore == avg && fuelScore == avg){
                return "good";
            }
            if (speedScore == fuelScore && speedScore == brakeScore && speedScore == DDLScore)
                return ConstantData.TAG_SPEED;

        }
        Log.d("Terminate!", "TERMINATE!");

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
        Log.d("getpos",""+ speedScore);
        Log.d("getpos",""+ brakeScore);
        Log.d("getpos",""+ DDLScore);
        Log.d("getpos",""+ fuelScore);

        int looper = 4;
        int pos=1;

        if (!checker(speedScore, brakeScore, DDLScore, fuelScore)) {
            for (int i = 0; i <= looper; i++) {

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
        } else {
            for (int i = 0; i <= looper; i++) {
                if (speedScore >= avg + rangeOne(i) && speedScore <= avg + rangeTwo(i)) {
                    Log.d("first if",""+i);
                    pos = i;
                }
                if (brakeScore >= avg + rangeOne(i) && brakeScore <= avg + rangeTwo(i)) {
                    Log.d("second if",""+i);
                    pos = i;
                }
                if (DDLScore >= avg + rangeOne(i) && DDLScore <= avg + rangeTwo(i)) {
                    Log.d("third if",""+i);
                    pos = i;
                }
                if (fuelScore >= avg + rangeOne(i) && fuelScore <= avg + rangeTwo(i)) {
                    Log.d("fourth if",""+i);
                    pos = i;
                }
            }
        }
        Log.d("postion", "" + pos);
        return pos;
    }

    //determines the evaluation offset range
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

    //determines the evaluation end range
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

        //Messages for when the score is negative
        String[] speedNegative = {"Speed score is under avarage", "You need to watch your speed","Your results regarding speed are getting worse",
                          "You should pay more attention to your speed","Son. You got speeding issues"};
        String[] fuelNegative = {"Fuel consumption score is under average","Your fuel upkeep is worsening", "Your results regarding fuel consumption are worrying",
                         "You should pay more attention to fuel consumption", "You are sufficient to burn the fuel of the entire planet"};
        String[] brakeNegative = {"Braking score is under average","It appears you brake too much", "Your brake score is getting worse",
                          "Your results regarding brakes are getting increasingly worse", "How about you lift your foot from the pedal every now and then"};
        String[] ddlNegative = {"distraction level score is under average", "Keep focus on the road", "Your level of distraction is increasing",
                        "Your levels of distraction are worrying","Eyes on the road because i got my eyes on you ლ(ಠ_ಠლ)"};

        //Messages for when the score is positive
        String[] speedPositive = {"Your speed score is over the average", "Your speed score is improving even further", "You are doing very well regarding speed",
                           "Speed rules are your life", "Master at speed"};
        String[] fuelPositive = {"Your fuel consumption score is over the average", "Your fuel consumption score is improving even further", "You are doing very well regarding fuel consumption",
                           "Fuel is important and you know that best", "Master at fuel consumption"};
        String[] brakePositive = {"Your braking score is over the average", "Your braking score is improving even further", "You are doing very well regarding braking",
                            "You always brake at the right moment", "Master at braking"};
        String[] ddlPositive = {"Your distraction score is over the average", "Your distraction score is improving even further", "You are a focused driver",
                "Distracted? You don't even know that word", "Absolute driving focus"};

        //Message for when the score of all measurements is 50
        String average = "Jack of all trades , master of none";

        //Return a informative message depending on performance
        if (!isPositive) {
            if (eval.equals(ConstantData.TAG_SPEED)) {
                return speedNegative[pos];
            }
            if (eval.equals(ConstantData.TAG_BRAKE)) {
                return brakeNegative[pos];
            }
            if (eval.equals(ConstantData.TAG_FUEL)) {
                return fuelNegative[pos];
            }
            if (eval.equals(ConstantData.TAG_DISTRACTION)) {
                return ddlNegative[pos];
            }
        } else {
            if (eval.equals(ConstantData.TAG_SPEED)) {
                return speedPositive[pos];
            }
            if (eval.equals(ConstantData.TAG_BRAKE)) {
                return brakePositive[pos];
            }
            if (eval.equals(ConstantData.TAG_FUEL)) {
                return fuelPositive[pos];
            }
            if (eval.equals(ConstantData.TAG_DISTRACTION)) {
                return ddlPositive[pos];
            }
            if (eval.equals("good")) {
                return average;
            }

        }
        return "terminate";
    }

    //Display the right picture
    public static void setImage(ImageView view, int pos){
        String temp = evaluateList();

        if (pos == 2 && !isPositive || pos == 3 && !isPositive || pos == 4 && !isPositive){
            if (temp.equals(ConstantData.TAG_SPEED)) {
                view.setImageResource(R.drawable.speed_red);
            }
            if (temp.equals(ConstantData.TAG_BRAKE)) {
                view.setImageResource(R.drawable.brake_red);
            }
            if (temp.equals(ConstantData.TAG_FUEL)) {
                view.setImageResource(R.drawable.fuel_red);
            }
            if (temp.equals(ConstantData.TAG_DISTRACTION)) {
                view.setImageResource(R.drawable.distraction_red);
            }
        }

        if (pos == 0 || pos == 1) {
            if (temp.equals(ConstantData.TAG_SPEED)) {
                view.setImageResource(R.drawable.speed_orange);
            }
            if (temp.equals(ConstantData.TAG_BRAKE)) {
                view.setImageResource(R.drawable.brake_orange);
            }
            if (temp.equals(ConstantData.TAG_FUEL)) {
                view.setImageResource(R.drawable.fuel_orange);
            }
            if (temp.equals(ConstantData.TAG_DISTRACTION)) {
                view.setImageResource(R.drawable.distraction_orange);
            }
        }

        if (pos == 2 && isPositive || pos == 3 && isPositive || pos == 4 && isPositive){
            if (temp.equals(ConstantData.TAG_SPEED)) {
                view.setImageResource(R.drawable.speed_green);
            }
            if (temp.equals(ConstantData.TAG_BRAKE)) {
                view.setImageResource(R.drawable.brake_green);
            }
            if (temp.equals(ConstantData.TAG_FUEL)) {
                view.setImageResource(R.drawable.fuel_green);
            }
            if (temp.equals(ConstantData.TAG_DISTRACTION)) {
                view.setImageResource(R.drawable.distraction_green);
            }
        }

    }

}
