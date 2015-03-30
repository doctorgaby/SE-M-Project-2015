package group8.com.application.Application;

import java.util.ArrayList;
import java.util.List;

import group8.com.application.Model.DataList;

/**
 * Created by enriquecordero on 30/03/15.
 */
public class Session {
    String userName;
    DataList currentPoints;
    DataList currentMeasurements;

    public Session (String userName)
    {
        this.userName = userName;
        currentPoints = new DataList();
        currentMeasurements = new DataList();
    }
}
