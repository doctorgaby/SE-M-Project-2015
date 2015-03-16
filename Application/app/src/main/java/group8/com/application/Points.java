package group8.com.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by enriquecordero on 15/03/15.
 */
public class Points {
    private final int initialPoints = 0;
    private List<Integer> speed;
    private List<Integer> fuelConsumption;
    private List<Integer> brake;
    private List<Integer> driverDistractionLevel;

    public Points ()
    {
        speed = new ArrayList<>();
        fuelConsumption = new ArrayList<>();
        brake = new ArrayList<>();
        driverDistractionLevel = new ArrayList<>();

        speed.add(initialPoints);//ADD the initial value
        fuelConsumption.add(initialPoints);//ADD the initial value
        brake.add(initialPoints);//ADD the initial value
        driverDistractionLevel.add(initialPoints);//ADD the initial value
    }

    public void setSpeed(int speed) {
        this.speed.add(speed);
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption.add(fuelConsumption);
    }

    public void setBrake(int brake) {
        this.brake.add(brake);
    }

    public void setDriverDistractionLevel(int driverDistractionLevel) {
        this.driverDistractionLevel.add(driverDistractionLevel);
    }

    public int getSpeed(int position) {
        return speed.get(position);
    }

    public int getFuelConsumption(int position) {
        return fuelConsumption.get(position);
    }

    public int getBrake(int position) {
        return brake.get(position);
    }

    public int getDriverDistractionLevel(int position) {
        return driverDistractionLevel.get(position);
    }

    public int getSizeSpeed ()
    {
        return speed.size();
    }

    public int getSizeFuelConsumption()
    {
        return fuelConsumption.size();
    }

    public int getSizeBrake()
    {
        return brake.size();
    }

    public int getSizeDriverDistractionLevel()
    {
        return driverDistractionLevel.size();
    }

    public List<Integer> getSpeed() {
        return speed;
    }

    public List<Integer> getFuelConsumption() {
        return fuelConsumption;
    }

    public List<Integer> getBrake() {
        return brake;
    }

    public List<Integer> getDriverDistractionLevel() {
        return driverDistractionLevel;
    }

    public int getMaxSpeed()
    {
        if (!speed.isEmpty())
            return Collections.max(speed);
        else
            return 0;
    }

    public int getMaxFuelConsumption()
    {
        if (!fuelConsumption.isEmpty())
            return Collections.max(fuelConsumption);
        else
            return 0;
    }

    public int getMaxBrake()
    {
        if (!brake.isEmpty())
            return Collections.max(brake);
        else
            return 0;
    }

    public int getMaxDriverDistractionLevel()
    {
        if (!driverDistractionLevel.isEmpty())
            return Collections.max(driverDistractionLevel);
        else
            return 0;
    }

    public int getMaxOfAll ()
    {
        List<Integer> temp = new ArrayList();
        temp.add(getMaxSpeed());
        temp.add(getMaxFuelConsumption());
        temp.add(getMaxBrake());
        temp.add(getMaxDriverDistractionLevel());
        return Collections.max(temp);
    }

    public int getMaxLength()
    {
        List<Integer> temp = new ArrayList();
        temp.add(speed.size());
        temp.add(fuelConsumption.size());
        temp.add(brake.size());
        temp.add(driverDistractionLevel.size());
        return Collections.max(temp);
    }
}
