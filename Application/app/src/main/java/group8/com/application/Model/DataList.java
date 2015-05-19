package group8.com.application.Model;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import group8.com.application.Foundation.TimeComparator;
import group8.com.application.Foundation.ValueComparator;

public class DataList {

    private List<DataObject> speed;
    private List<DataObject> fuelConsumption;
    private List<DataObject> brake;
    private List<DataObject> driverDistractionLevel;

    public DataList(String type) {
        //Individual Lists for the speed, fuel consumption, brake and driver distraction level.
        //Each object in the list contains a time and a value, both of type integer.
        speed = new ArrayList<>();
        fuelConsumption = new ArrayList<>();
        brake = new ArrayList<>();
        driverDistractionLevel = new ArrayList<>();
        if (type.equals("p")) {
            speed.add(new DataObject(0, ConstantData.initialPoints));//ADD the initial value
            fuelConsumption.add(new DataObject(0, ConstantData.initialPoints));//ADD the initial value
            brake.add(new DataObject(0, ConstantData.initialPoints));//ADD the initial value
            driverDistractionLevel.add(new DataObject(0, ConstantData.initialPoints));//ADD the initial value
        } else {
            speed.add(new DataObject(0, 0));//ADD the initial value
            fuelConsumption.add(new DataObject(0, 0));//ADD the initial value
            brake.add(new DataObject(0, 0));//ADD the initial value
            driverDistractionLevel.add(new DataObject(0, 0));//ADD the initial value
        }
    }

    //Measurements Setters
    //Adds a new speed value to the list measurements.
    public void setSpeed (int time, double speed) {
        this.speed.add(new DataObject(time, (int) speed));
    }

    //Adds a new fuel consumption value to the list measurements..
    public void setFuelConsumption (int time, double fuelConsumption) {
        this.fuelConsumption.add(new DataObject(time, (int) fuelConsumption));
    }

//Points Setters.
    //Adds a new speed value to the list of points.
    public void setSpeed(int time, int speed) {
        this.speed.add(new DataObject(time, speed));
    }

    //Adds a new fuel consumption value to the list of points.
    public void setFuelConsumption(int time, int fuelConsumption) {
        this.fuelConsumption.add(new DataObject(time, fuelConsumption));
    }

//Common Setters
    //Adds a new driver distraction value to the list of points or measurements.
    public void setDriverDistractionLevel(int time, int driverDistractionLevel) {
        this.driverDistractionLevel.add(new DataObject(time, driverDistractionLevel));
    }

    //Adds a new brake value to the list of points.
    public void setBrake(int time, int brake) {
        this.brake.add(new DataObject(time, brake));
    }


//Place GETTERS -- These getters get the object from the defined list from a specific position.

    public DataObject getSpeed(int index) {
        return speed.get(index);
    }

    public DataObject getFuelConsumption(int index) {
        return fuelConsumption.get(index);
    }

    public DataObject getBrake(int index) {
        return brake.get(index);
    }

    public DataObject getDriverDistractionLevel(int index) {
        return driverDistractionLevel.get(index);
    }

    public int getSpeedSize() {
        return speed.size();
    }

    public int getFuelConsumptionSize() {
        return fuelConsumption.size();
    }

    public int getBrakeSize() {
        return brake.size();
    }

    public int getDriverDistractionLevelSize() {
        return driverDistractionLevel.size();
    }

//GETMAX -- These getters get the maxValue of each list depending on what the user wants. The user can also get the
// maximum of all the lists. Take into account that it compares with help of the ValueComparator.
    public int getMaxSpeed() {
        return (findMax (speed, new ValueComparator())).getValue();
    }

    public int getMaxFuelConsumption(){
        return (findMax (fuelConsumption, new ValueComparator())).getValue();
    }

    public int getMaxBrake() {
        return (findMax (brake, new ValueComparator())).getValue();
    }

    public int getMaxDriverDistractionLevel() {
        return (findMax(driverDistractionLevel, new ValueComparator())).getValue();
    }

//GETMAX -- These getters get the maxTime of each list depending on what the user wants. The user can also get the
// maximum of all the lists. Take into account that it compares with help of the TimeComparator.
    public int getMaxSpeedTime() {
        return (findMax (speed, new TimeComparator())).getTime();
    }

    public int getMaxFuelConsumptionTime(){
        return (findMax (fuelConsumption, new TimeComparator())).getTime();
    }

    public int getMaxBrakeTime() {
        return (findMax (brake, new TimeComparator())).getTime();
    }

    public int getMaxDriverDistractionLevelTime() {
        return (findMax (driverDistractionLevel, new TimeComparator())).getTime();
    }

//Helper function to find the max of a list according to a comparator.
    private DataObject findMax (List<DataObject> list,  Comparator<DataObject> comp)
    {
        return Collections.max(list, comp);
    }


//Gets the max points of all the lists.
    public int getMaxPoints ()
    {
        List<Integer> temp = new ArrayList();
        temp.add(getMaxSpeed());
        temp.add(getMaxFuelConsumption());
        temp.add(getMaxBrake());
        temp.add(getMaxDriverDistractionLevel());
        return Collections.max(temp);
    }

//Gets the max time of all the lists.
    public int getMaxTime()
    {
        List<Integer> temp = new ArrayList();
        temp.add(getMaxSpeedTime());
        temp.add(getMaxFuelConsumptionTime());
        temp.add(getMaxBrakeTime());
        temp.add(getMaxDriverDistractionLevelTime());
        return (Collections.max(temp) / 1000);
    }

//GETPLOTTABLE -- These getters get a copy of each list with a different format. Instead of being a
// list of DataObjects, its a list of Integers. The list has the format {x,y,x1,y1,x2,y2....xn,yn}.
// The purpose of this is to make them plottable in the graph.
    public HashMap<String,ArrayList> getPlottableFuelConsumption(String time)
    {
        return transformList(fuelConsumption, time);
    }

    public HashMap<String,ArrayList> getPlottableSpeed(String time)
    {
        return transformList(speed, time);
    }

    public HashMap<String,ArrayList> getPlottableBrake(String time)
    {
        return transformList(brake, time);
    }

    public HashMap<String,ArrayList> getPlottableDriverDistraction(String time)
    {
        return transformList(driverDistractionLevel, time);
    }

// Helper function that transforms a list into a plottable list.
    private HashMap<String,ArrayList> transformList (List<DataObject> list, String time)
    {
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<>();
        int size = list.size();
        int includedValues = 0;
        for (int i=0; i<size; i++)
        {
            int currenttime = list.get(i).getTime();
            if (currenttime !=0) {
                yVals.add(new Entry(list.get(i).getValue(), includedValues));
                includedValues++;
                Date date = new Date((long) currenttime*1000);
                SimpleDateFormat sdf;
                if (time.equals("now"))
                    sdf = new SimpleDateFormat("hh:mm");
                else
                    sdf = new SimpleDateFormat("yy-MM-dd");
                String formattedDate = sdf.format(date);
                //Log.d("value: " + data.getSpeed(i).getValue(), "date: " + formattedDate);
                xVals.add(formattedDate);
            }
        }
        HashMap<String, ArrayList> answer = new HashMap<>();
        answer.put("xVals", xVals);
        answer.put("yVals", yVals);
        return answer;
    }

// Get the JSON representation of the lists
    public JSONObject getJson() {

        JSONArray speedList = new JSONArray();
        JSONArray fuelConsumptionList = new JSONArray();
        JSONArray brakeList = new JSONArray();
        JSONArray driverDistractionLevelList = new JSONArray();


        for(int i = 0; i < speed.size(); i++) {

            JSONObject list = new JSONObject();
            String value = getSpeed(i).getValue().toString();
            String time = getSpeed(i).getTime().toString();

            try {

                list.put(ConstantData.TAG_SPEED, value);
                list.put(ConstantData.TAG_MEASUREDAT, time);
                speedList.put(list);

            } catch(JSONException ex) {
                // Could not add to speed list
            }

        }

        for(int i = 0; i < fuelConsumption.size(); i++) {

            JSONObject list = new JSONObject();
            String value = getFuelConsumption(i).getValue().toString();
            String time = getFuelConsumption(i).getTime().toString();

            try {

                list.put(ConstantData.TAG_FUEL, value);
                list.put(ConstantData.TAG_MEASUREDAT, time);
                fuelConsumptionList.put(list);

            } catch(JSONException ex) {
                // Could not add to fuel list
            }

        }

        for(int i = 0; i < brake.size(); i++) {

            JSONObject list = new JSONObject();
            String value = getBrake(i).getValue().toString();
            String time = getBrake(i).getTime().toString();

            try {

                list.put(ConstantData.TAG_BRAKE, value);
                list.put(ConstantData.TAG_MEASUREDAT, time);
                brakeList.put(list);

            } catch(JSONException ex) {
                // Could not add to brake list
            }

        }

        for(int i = 0; i < driverDistractionLevel.size(); i++) {

            JSONObject list = new JSONObject();
            String value = getDriverDistractionLevel(i).getValue().toString();
            String time = getDriverDistractionLevel(i).getTime().toString();

            try {

                list.put(ConstantData.TAG_DISTRACTION, value);
                list.put(ConstantData.TAG_MEASUREDAT, time);
                driverDistractionLevelList.put(list);

            } catch(JSONException ex) {
                // Could not add to distraction list
            }

        }

        JSONObject outerList = new JSONObject();

        try {
            outerList.put(ConstantData.TAG_SPEED, speedList);
            outerList.put(ConstantData.TAG_FUEL, fuelConsumptionList);
            outerList.put(ConstantData.TAG_BRAKE, brakeList);
            outerList.put(ConstantData.TAG_DISTRACTION, driverDistractionLevelList);
        } catch(JSONException ex) {
            // Could not add to outer list
        }

        return outerList;

    }

}
