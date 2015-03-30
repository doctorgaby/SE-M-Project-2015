package group8.com.application.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import group8.com.application.Foundation.TimeComparator;
import group8.com.application.Foundation.ValueComparator;

public class DataList {
    private List<DataObject> speed;
    private List<DataObject> fuelConsumption;
    private List<DataObject> brake;
    private List<DataObject> driverDistractionLevel;

    public DataList()
    {
        //Individual Lists for the speed, fuel consumption, brake and driver distraction level.
        //Each object in the list contains a time and a value, both of type integer.
        speed = new ArrayList<>();
        fuelConsumption = new ArrayList<>();
        brake = new ArrayList<>();
        driverDistractionLevel = new ArrayList<>();

        speed.add(new DataObject(0, ConstantData.initialPoints));//ADD the initial value
        fuelConsumption.add(new DataObject(0, ConstantData.initialPoints));//ADD the initial value
        brake.add(new DataObject(0, ConstantData.initialPoints));//ADD the initial value
        driverDistractionLevel.add(new DataObject(0, ConstantData.initialPoints));//ADD the initial value
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

    //Adds a new brake value to the list of measurements.
    public void setBrake (int time, boolean brake) {
        int temp;
        if (brake)
            temp=1;
        else
            temp=0;
        this.brake.add(new DataObject(time, temp));
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

    //Adds a new brake value to the list of points.
    public void setBrake(int time, int brake) {
        this.brake.add(new DataObject(time, brake));
    }

//Common Setters
    //Adds a new driver distraction value to the list of points or measurements.
    public void setDriverDistractionLevel(int time, int driverDistractionLevel) {
        this.driverDistractionLevel.add(new DataObject(time, driverDistractionLevel));
    }

//Place GETTERS -- These getters get the object from the defined list from a specific position.
    public DataObject getSpeed(int position) {
        return speed.get(position);
    }

    public DataObject getFuelConsumption(int position) {
        return fuelConsumption.get(position);
    }

    public DataObject getBrake(int position) {
        return brake.get(position);
    }

    public DataObject getDriverDistractionLevel(int position) {
        return driverDistractionLevel.get(position);
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
        return Collections.max(temp);
    }

//GETPLOTTABLE -- These getters get a copy of each list with a different format. Instead of being a
// list of DataObjects, its a list of Integers. The list has the format {x,y,x1,y1,x2,y2....xn,yn}.
// The purpose of this is to make them plottable in the graph.
    public List<Integer> getPlottableFuelConsumption()
    {
        return transformList(fuelConsumption);
    }

    public List<Integer> getPlottableSpeed()
    {
        return transformList(speed);
    }

    public List<Integer> getPlottableBrake()
    {
        return transformList(brake);
    }

    public List<Integer> getPlottableDriverDistraction()
    {
        return transformList(driverDistractionLevel);
    }

// Helper function that transforms a list into a plottable list.
    private List<Integer> transformList (List<DataObject> list)
    {
        List<Integer> temp = new ArrayList<>();
        for (DataObject counter:list)
        {
            temp.add(counter.getTime());
            temp.add(counter.getValue());
        }
        return temp;
    }
}
