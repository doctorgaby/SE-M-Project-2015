package group8.com.application.Model;

import java.util.Comparator;

//This is an object that works both for the points and the measurements. All information can be represented as integers.
public class DataObject{
    private Integer time; //Maybe there is a better way to declare this than integer Hampus should check this out.
    private Integer value;

    //Constructor. Receives both the time and the value.
    public DataObject(int time, int value)
    {
        this.time = time;
        this.value = value;
    }

    public Integer getValue()
    {
        return value;
    }

    public Integer getTime()
    {
        return time;
    }
}
