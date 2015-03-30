package group8.com.application.Foundation;

import java.util.Comparator;

import group8.com.application.Model.DataObject;

//Comparator used to assist the Points and Measurements Lists to be able to retrieve the max time from them.
public class TimeComparator implements Comparator<DataObject> {
    @Override
    public int compare(DataObject obj1, DataObject obj2) {
        return obj1.getTime().compareTo(obj2.getTime());
    }
}
