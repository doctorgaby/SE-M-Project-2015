package group8.com.application.Model;

/**
 * Created by enriquecordero on 30/03/15.
 */
public abstract class ConstantData {

    /* GRADING SYSTEM*/
    public static final int initialPoints = 50;
    public static final double goodFuelConsumption = 600.0;
    public static final double extremeSpeed = 120.0;
    public static final double extremeFuelConsumption = 1200.0;
    public static final int outOfRangeSpeed = 20;
    public static final int outOfRangeSpeedLowerMargin = 2;
    public static final int outOfRangeSpeedMiddleMargin = 4;
    /* END - GRADING SYSTEM*/

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_ACTION_LOGIN = "login";
    public static final String TAG_ACTION_FBLOGIN = "fblogin";
    public static final String TAG_ACTION_REGISTER = "register";

    //Remote Host
    public static final String INDEX_URL = "http://semprojectgroup8.site50.net/project_systems_dev/index.php";

    //LOCALHOST
    //public static final String INDEX_URL = "http://192.168.56.1:80/AutomotiveDriverAssistant/index.php";

    public static final String TAG_POSTS = "posts";
    public static final String TAG_SPEED = "speed";
    public static final String TAG_FUEL = "fuel";
    public static final String TAG_BRAKE = "brake";
    public static final String TAG_DISTRACTION = "distraction";
    public static final String TAG_MEASUREDAT = "measuredAt";
    public static final String TAG_GETMEASUREMENTS = "getMeasurements";
    public static final String TAG_GETFILTEREDMEASUREMENTS = "getFilteredMeasurements";
    public static final String TAG_GETPOINTS = "getPoints";
    public static final String TAG_GETFILTEREDPOINTS = "getFilteredPoints";
    public static final String TAG_SETMEASUREMENTS = "setMeasurements";
    public static final String TAG_SETPOINTS = "setPoints";
    public static final String TAG_SAVEDAY = "SaveDay";
    public static final String TAG_SETFINALSCORES = "setFinalScore";


    public static final String TAG_RANKING = "posts";
    public static final String TAG_USERNAME = "username";

    public static final String TAG_GETALLSCORES = "getAllScores";
    public static final String TAG_GETFRIENDSSCORES = "getFriendsScores";
    public static final String TAG_SETFRIEND = "setFriend";
    public static final String TAG_REMOVEFRIEND = "removeFriend";
    public static final String TAG_GETALLFRIENDS = "getAllFriends";


    public static final String TAG_STARTFRAGMENT = "start_menu";
    public static final String TAG_CONTINUEFINISHFRAGMENT = "continue_finish_menu";
    public static final String TAG_LOGINREGISTERFRAGMENT = "login_reg_menu";
    public static final String TAG_LOGINFRAGMENT = "login_frag";
    public static final String TAG_REGISTERFRAGMENT = "login_frag";
    public static final String TAG_GETFINALSCORE = "getFinalScore";

    public static final String[] medalID = {"Brake Medal", "Distraction Medal", "Speed Medal", "Fuel Medal"};
    public static final String[] medalName = {"Master at braking", "Master at focus", "Master at speed", "Master at fuel upkeep"};

}
