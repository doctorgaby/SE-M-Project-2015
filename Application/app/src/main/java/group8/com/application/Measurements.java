package group8.com.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by enriquecordero on 15/03/15.
 */
public class Measurements
{
    private List<Double> speed;
    private List<Double> fuelConsumption;
    private List<Boolean> brake;
    private List<Integer> driverDistractionLevel;

    public Measurements ()
    {
        speed = new ArrayList<>();
        fuelConsumption = new ArrayList<>();
        brake = new ArrayList<>();
        driverDistractionLevel = new ArrayList<>();
    }

    public void setSpeed (double speed)
    {
        this.speed.add(speed);
    }

    public void setFuelConsumption (double fuelConsumption)
    {
        this.fuelConsumption.add(fuelConsumption);
    }

    public void setBrake (boolean brake)
    {
        this.brake.add(brake);
    }

    public void setDriverDistractionLevel(int level)
    {
        this.driverDistractionLevel.add(level);
    }

    public double getSpeed (int position)
    {
        return speed.get(position);
    }

    public double getFuelConsumption (int position)
    {
        return fuelConsumption.get(position);
    }

    public boolean getBrake(int position)
    {
        return brake.get(position);
    }

    public int getDriverDistractionLevel(int position)
    {
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

    public List<Double> getSpeed() {
        return speed;
    }

    public List<Double> getFuelConsumption() {
        return fuelConsumption;
    }

    public List<Boolean> getBrake() {
        return brake;
    }

    public List<Integer> getDriverDistractionLevel() {
        return driverDistractionLevel;
    }

    public double getMaxSpeed ()
    {
        if (!speed.isEmpty())
            return Collections.max(speed);
        else
            return 0;
    }

    public double getMaxFuelConsumption()
    {
        if (!fuelConsumption.isEmpty())
            return Collections.max(fuelConsumption);
        else
            return 0;
    }
}
