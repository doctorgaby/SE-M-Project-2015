package group8.com.application.Foundation;

import java.util.Comparator;

import group8.com.application.Model.DataObject;

//Comparator used to assist the Points and Measurements Lists to be able to retrieve the max value from them.
public class ValueComparator implements Comparator<DataObject> {

    @Override
    public int compare(DataObject obj1, DataObject obj2) {
        return obj1.getValue().compareTo(obj2.getValue());
    }

}
