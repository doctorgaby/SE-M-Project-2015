package group8.com.application.Application;

import group8.com.application.Model.DataList;

public abstract class Session {
    private static String userName = "";
    private static DataList currentPoints = new DataList();
    private static DataList currentMeasurements = new DataList();

    public static void setUserName(String username) {
        Session.userName = username;
    }

    public static String getUserName() {
        return userName;
    }

    public static boolean isLoggedIn() {
        return !userName.equals("");
    }

    public static void restart() {
        currentPoints = new DataList();
        currentMeasurements = new DataList();
        userName = "";
    }
}
