package group8.com.application.Application;

import java.util.HashMap;
import java.util.Map;

import group8.com.application.Model.Medal;

/**
 * Created by Kristiyan on 5/12/2015.
 */
public class MedalsLogic {

    public static Map<String, Boolean> medalMap = new HashMap<>();

    public static Map<String, Boolean> initiateMedals(){

        medalMap.put("Speed Medal", false);
        medalMap.put("Brake Medal", false);
        medalMap.put("Distraction Medal", false);
        medalMap.put("Fuel Medal", false);

        return medalMap;
    }

    public static Map<String, Boolean> updateMap(Map map){

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

    public static boolean setUpdatedMap(String s){

        return updateMap(initiateMedals()).get(s);

    }

}
