package group8.com.application.Application;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import group8.com.application.Model.ConstantData;
import group8.com.application.Model.DataList;
import group8.com.application.R;


public class MedalsLogic {

    private static Map<String, Boolean> initiateMedals(){

        Map<String, Boolean> medalMap = new HashMap<>();

        medalMap.put("Brake Medal", false);
        medalMap.put("Distraction Medal", false);
        medalMap.put("Speed Medal", false);
        medalMap.put("Fuel Medal", false);

        return medalMap;
    }

    private static Map<String, Boolean> updateMap(Map map){

        int speedScore = Session.getSpeedScore();
        int brakeScore = Session.getBrakeScore();
        int distractionScore = Session.getDriverDistractionLevelScore();
        int fuelScore = Session.getFuelConsumptionScore();

        if (speedScore == 100){
            map.get("Speed Medal");
            map.put("Speed Medal", true);

        }
        if (brakeScore == 100){
            map.get("Brake Medal");
            map.put("Brake Medal", true);

        }
        if (distractionScore == 100){
            map.get("Distraction Medal");
            map.put("Distraction Medal", true);

        }
        if (fuelScore == 100){
            map.get("Fuel Medal");
            map.put("Fuel Medal", true);

        }

        return map;

    }

    private static boolean setUpdatedMap(String s){

        return updateMap(initiateMedals()).get(s);

    }

    public static boolean medalStatus(String s, Context context){

        SharedPreferences prefs = context.getSharedPreferences("Save_Medal_Data", 0);

        boolean status = prefs.getBoolean(Session.getUserName() + s, false);

        if (!status){
            return false;
        } else {
            return status;
        }

    }

    public static boolean medalStatusUpdate(String s,Context context){

        SharedPreferences.Editor editor = context.getSharedPreferences("Save_Medal_Data", 0).edit();
        SharedPreferences prefs = context.getSharedPreferences("Save_Medal_Data", 0);

        if(setUpdatedMap(s)) {
            editor.putBoolean(Session.getUserName() + s, true);
            editor.apply();
        }

        boolean saveMedalStatus = prefs.getBoolean(Session.getUserName() + s, false);

        if (saveMedalStatus != true){
            return false;
        } else {
            return saveMedalStatus;
        }

    }

    public static void showDialog(String title, boolean state, Context context){

        String [] name = {"braking", "distraction", "speed", "fuel consumption"};

        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle(title);

        final TextView editText = (TextView)dialog.findViewById(R.id.textDialog);
        ImageView dialogImage = (ImageView) dialog.findViewById(R.id.imageDialog);

        for (int i = 0; i <= 3;i++) {
            if (title == ConstantData.medalName[i])
                if (state) {

                    if(title.equals(ConstantData.medalName[0])){
                        dialogImage.setImageResource(R.drawable.medal_brakes);
                    }
                    if(title.equals(ConstantData.medalName[1])){
                        dialogImage.setImageResource(R.drawable.medal_distraction);
                    }
                    if(title.equals(ConstantData.medalName[2])){
                        dialogImage.setImageResource(R.drawable.medal_speed);
                    }
                    if(title.equals(ConstantData.medalName[3])){
                        dialogImage.setImageResource(R.drawable.medal_fuel);
                    }

                    editText.setText("You have unlocked this medal!");

                } else {

                    dialogImage.setImageResource(R.drawable.locked);
                    editText.setText("Score 100 points in " + name[i] + " to unlock this medal");
                }
        }

        Button ok = (Button)dialog.findViewById(R.id.okButton);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


}
