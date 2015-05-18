package group8.com.application.Application.Database;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import group8.com.application.Application.Session;
import group8.com.application.Foundation.JSONParser;
import group8.com.application.Model.ConstantData;
import group8.com.application.Model.DataList;

public abstract class DBHandler {

    //                              **************************
    //                              *****                *****
    //                              ***** Getter Methods *****
    //                              *****                *****
    //                              **************************

    /**
     * @param
     */
    public static DataList getMeasurements(String user) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_GETMEASUREMENTS));
        params.add(new BasicNameValuePair("username", user));
        Log.d("getMeasurements!", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with get Measurements.");
        }
        DataList list = new DataList("m");
        jsonToList(list, json);
        return list;
    }

    /**
     * @param
     */
    public static DataList getFilteredMeasurements(String user, int start, int stop) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_GETFILTEREDMEASUREMENTS));
        params.add(new BasicNameValuePair("username", user));
        params.add(new BasicNameValuePair("start", Integer.toString(start)));
        params.add(new BasicNameValuePair("stop", Integer.toString(stop)));
        Log.d("getFilteredMeasurements", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with get filtered Measurements.");
        }
        DataList list = new DataList("m");
        jsonToList(list, json);
        return list;
    }

    /**
     * @param
     */
    public static DataList getPoints(String user) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_GETPOINTS));
        params.add(new BasicNameValuePair("username", user));
        Log.d("getPoints!", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with get Points.");
        }

        DataList list = new DataList("p");
        jsonToList(list, json);
        return list;
    }

    /**
     * @param
     */
    public static DataList getFilteredPoints(String user, int start, int stop) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_GETFILTEREDPOINTS));
        params.add(new BasicNameValuePair("username", user));
        params.add(new BasicNameValuePair("start", Integer.toString(start)));
        params.add(new BasicNameValuePair("stop", Integer.toString(stop)));
        Log.d("getFilteredPoints", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with get filtered Points.");
        }
        DataList list = new DataList("p");
        jsonToList(list, json);
        return list;
    }

    //                              **************************
    //                              *****                *****
    //                              ***** Setter Methods *****
    //                              *****                *****
    //                              **************************

    /**
     * @param
     */
    public static void setScores(String user) {
        JSONObject json;
        int speed = Session.getSpeedScore();
        int brake = Session.getBrakeScore();
        int fuel = Session.getFuelConsumptionScore();
        int distraction = Session.getDriverDistractionLevelScore();
        int measuredAt =(int) System.currentTimeMillis() / 1000;

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_SETFINALSCORES));
        params.add(new BasicNameValuePair("username", user));
        params.add(new BasicNameValuePair("speed", Integer.toString(speed)));
        params.add(new BasicNameValuePair("brake", Integer.toString(brake)));
        params.add(new BasicNameValuePair("fuel", Integer.toString(fuel)));
        params.add(new BasicNameValuePair("distraction", Integer.toString(distraction)));
        params.add(new BasicNameValuePair("measuredAt", Integer.toString(measuredAt)));
        String success = "error";
        try {
            json = new doExecuteValues(params).execute().get(); // jsonParser.makeHttpRequest(ConstantData.INDEX_URL, "POST", params);
            success = "" + json.getInt(ConstantData.TAG_SUCCESS);
        } catch(Exception ex) {
            Log.d("DBHandler.java", "Exception in setMeasurements(): " + ex.getMessage());
        }

        Log.d("success", success);
    }

    /**
     * @param
     */
    public static void setMeasurements(String user) {
        JSONObject json;
        JSONObject list = Session.getMeasurementsJson();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_SETMEASUREMENTS));
        params.add(new BasicNameValuePair("username", user));
        params.add(new BasicNameValuePair("list", list.toString()));
        Log.d("list", list.toString());
        Log.d("params", params.toString());
        String success = "error";
        try {
            json = new doExecuteValues(params).execute().get(); // jsonParser.makeHttpRequest(ConstantData.INDEX_URL, "POST", params);
            success = "" + json.getInt(ConstantData.TAG_SUCCESS);
        } catch(Exception ex) {
            Log.d("DBHandler.java", "Exception in setMeasurements(): " + ex.getMessage());
        }

        Log.d("success", success);

    }

    /**
     * @param
     */
    public static void setPoints(String user) {
        JSONObject json;
        JSONObject list = Session.getPointsJson();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_SETPOINTS));
        params.add(new BasicNameValuePair("username", user));
        params.add(new BasicNameValuePair("list", list.toString()));
        try {
            json = new doExecuteValues(params).execute().get(); // jsonParser.makeHttpRequest(ConstantData.INDEX_URL, "POST", params);
        } catch(Exception ex) {
            // Could not execute request
            Log.d("DBHandler.java", "Exception in setPoints(): " + ex.getMessage());
        }

    }

    //                              **************************
    //                              *****                *****
    //                              ***** User Methods   *****
    //                              *****                *****
    //                              **************************

    /**
     * Verifies the username and password.
     *
     * @return 0, if the login was unsuccessful.
     *         1, if the login was successful.
     * */
    public static JSONObject attemptLogin(String tag, String username, String password) {

        // Variables
        JSONParser jsonParser = new JSONParser();
        JSONObject json = new JSONObject();
        int success;
        // Attempting to login user
        try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("action", tag));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            Log.d("request!", "starting");

            json = jsonParser.makeHttpRequest(ConstantData.INDEX_URL, "POST", params); // getting product details by making HTTP request
            Log.d("Login attempt", json.toString());                                   // check your log for json response
            success = json.getInt(ConstantData.TAG_SUCCESS);                           // json success tag

        } catch (JSONException e) {
            success = 0;
        }
        if(success == 1)
            Log.d("Login Successful!", json.toString());
        else try {
            Log.d("Login Failure!", json.getString(ConstantData.TAG_MESSAGE));
        } catch(JSONException ex) {
            Log.e("DBHandler","JSON Exception");
        }

        return json;

    }

    /**
     * Verifies the username and password.
     *
     * @return 0, if the login was unsuccessful.
     *         1, if the login was successful.
     * */
    public static JSONObject registerUser(String username, String password) {
        // Variables
        JSONParser jsonParser = new JSONParser();
        JSONObject json = new JSONObject();
        int success;
        // Attempting to register user
        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("action", ConstantData.TAG_ACTION_REGISTER));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            Log.d("request!", "starting");

            json = jsonParser.makeHttpRequest(ConstantData.INDEX_URL, "POST", params); //Posting user data to script
            Log.d("Registering attempt", json.toString());                             // full json response
            success = json.getInt(ConstantData.TAG_SUCCESS);                           // json success element

        } catch (JSONException e) {
            success = 0;
        }
        if(success == 1)
            Log.d("Login Successful!", json.toString());
        else try {
            Log.d("Login Failure!", json.getString(ConstantData.TAG_MESSAGE));
        } catch(JSONException ex) {
            Log.e("DBHandler","JSON Exception");
        }

        return json;

    }

    //                              **************************
    //                              *****                *****
    //                              ***** Helper Methods *****
    //                              *****                *****
    //                              **************************

    /**
     * @param
     */
    private static void jsonToList(DataList list, JSONObject json) {
        try {
            JSONArray speedArray = json.getJSONArray(ConstantData.TAG_SPEED);
            for (int i = 0; i < speedArray.length(); i++) {
                JSONObject c = speedArray.getJSONObject(i);
                //gets the content of each JSON object
                Integer speed = c.getInt(ConstantData.TAG_SPEED);
                Integer measuredAt = c.getInt(ConstantData.TAG_MEASUREDAT);
                list.setSpeed(measuredAt, speed);
                Log.d("Speed number: " + i, "value: " + measuredAt);
            }
            JSONArray brakeArray = json.getJSONArray(ConstantData.TAG_BRAKE);
            for (int i = 0; i < brakeArray.length(); i++) {
                JSONObject c = brakeArray.getJSONObject(i);
                //gets the content of each JSON object
                Integer brake = c.getInt(ConstantData.TAG_BRAKE);
                Integer measuredAt = c.getInt(ConstantData.TAG_MEASUREDAT);
                list.setBrake(measuredAt, brake);
                Log.d("Brake number: " + i, "value: " + measuredAt);
            }
            JSONArray fuelArray = json.getJSONArray(ConstantData.TAG_FUEL);
            for (int i = 0; i < fuelArray.length(); i++) {
                JSONObject c = fuelArray.getJSONObject(i);
                //gets the content of each JSON object
                Integer fuel = c.getInt(ConstantData.TAG_FUEL);
                Integer measuredAt = c.getInt(ConstantData.TAG_MEASUREDAT);
                list.setFuelConsumption(measuredAt, fuel);
                Log.d("Fuel number: " + i, "value: " + measuredAt);
            }
            JSONArray distractionArray = json.getJSONArray(ConstantData.TAG_DISTRACTION);
            for (int i = 0; i < distractionArray.length(); i++) {
                JSONObject c = distractionArray.getJSONObject(i);
                //gets the content of each JSON object
                Integer distraction = c.getInt(ConstantData.TAG_DISTRACTION);
                Integer measuredAt = c.getInt(ConstantData.TAG_MEASUREDAT);
                list.setDriverDistractionLevel(measuredAt, distraction);
                Log.d("Distraction number: " + i, "value: " + measuredAt);
            }
        } catch (JSONException e) {
            Log.e("DBHANDLER ERROR", "ERROR WITH THE JSON PARSER. " + e.toString());
        }
    }

    public static ArrayList<HashMap<String, String>> getFriendsRankings(String username) {
        ArrayList<HashMap<String, String>> rankingList= new ArrayList<>();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_GETFRIENDSSCORES));
        params.add(new BasicNameValuePair("username", username));
        Log.d("getFriendsScores!", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with get friend scores.");
        }
        jsonToRankingList (rankingList, json);
        return rankingList;
    }

    public static HashMap<String, Integer> getFinalScore (String user) {
        HashMap<String, Integer> list = new HashMap<>();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_GETFINALSCORE));
        params.add(new BasicNameValuePair(ConstantData.TAG_USERNAME, user));
        Log.d("getFinalScore!", "starting");
        JSONObject json = new JSONObject();
        JSONArray array;
        try {
            json = new doExecuteValues(params).execute().get();
            array = json.getJSONArray(ConstantData.TAG_RANKING);
            if (!array.isNull(0)) {
                JSONObject c = array.getJSONObject(0);
                list.put(ConstantData.TAG_SPEED,c.getInt(ConstantData.TAG_SPEED));
                list.put(ConstantData.TAG_BRAKE,c.getInt(ConstantData.TAG_BRAKE));
                list.put(ConstantData.TAG_FUEL, c.getInt(ConstantData.TAG_FUEL));
                list.put(ConstantData.TAG_DISTRACTION, c.getInt(ConstantData.TAG_DISTRACTION));
            }
            else {
                // ADD DEFAULT VALUES TO THE LIST JUST IN CASE ITS NULL.
                list.put(ConstantData.TAG_SPEED,ConstantData.initialPoints);
                list.put(ConstantData.TAG_BRAKE,ConstantData.initialPoints);
                list.put(ConstantData.TAG_FUEL, ConstantData.initialPoints);
                list.put(ConstantData.TAG_DISTRACTION, ConstantData.initialPoints);
            }
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with get Final Score.");
        }
        return list;
    }

    public static ArrayList<HashMap<String, String>> getAllRankings() {
        ArrayList<HashMap<String, String>> rankingList= new ArrayList<>();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_GETALLSCORES));
        Log.d("getAllScores!", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with get all rankings.");
        }
        jsonToRankingList (rankingList, json);
        return rankingList;
    }

    private static void jsonToRankingList (ArrayList<HashMap<String, String>> rankingList, JSONObject json) {

        try {
            JSONArray rankings = json.getJSONArray(ConstantData.TAG_RANKING);

            // looping through all posts according to the json object returned
            for (int i = 0; i < rankings.length(); i++) {
                JSONObject c = rankings.getJSONObject(i);
                String userName = c.getString(ConstantData.TAG_USERNAME);
                String fuel = c.getString(ConstantData.TAG_FUEL);
                String brake = c.getString(ConstantData.TAG_BRAKE);
                String distraction = c.getString(ConstantData.TAG_DISTRACTION);
                String speed = c.getString(ConstantData.TAG_SPEED);
                int total = Integer.parseInt(fuel) + Integer.parseInt(brake) + Integer.parseInt(distraction) + Integer.parseInt(speed);
                String totalString = Integer.toString(total);
                // creating new HashMap
                HashMap<String, String> map = new HashMap<>();
                map.put(ConstantData.TAG_USERNAME, userName);
                map.put(ConstantData.TAG_FUEL, fuel);
                map.put(ConstantData.TAG_BRAKE, brake);
                map.put(ConstantData.TAG_DISTRACTION, distraction);
                map.put(ConstantData.TAG_SPEED, speed);
                map.put("totalPoints", totalString);
                rankingList.add(map);
            }
            Collections.sort(rankingList, new Comparator<HashMap<String, String>>() {
                public int compare(HashMap<String, String> result1, HashMap<String, String> result2) {
                    return Integer.parseInt(result2.get("totalPoints")) - Integer.parseInt(result1.get("totalPoints"));
                }
            });
        } catch (JSONException e) {
            Log.d("RankingView", "Problem with the json data");
        }
    }

    public static JSONObject addFriend (String friend) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_SETFRIEND));
        params.add(new BasicNameValuePair("username", Session.getUserName()));
        params.add(new BasicNameValuePair("friend", friend));
        Log.d("addFriend!", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with add friend.");
        }
        return json;
    }

    public static JSONObject removeFriend (String friend) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_REMOVEFRIEND));
        params.add(new BasicNameValuePair("username", Session.getUserName()));
        params.add(new BasicNameValuePair("friend", friend));
        Log.d("removeFriend!", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with removefriend.");
        }
        return json;
    }

    public static List<String> getAllFriends (String user) {
        List<String> response = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", ConstantData.TAG_GETALLFRIENDS));
        params.add(new BasicNameValuePair("username", user));
        Log.d("getAllFriends!", "starting");
        JSONObject json = new JSONObject();
        try {
            json = new doExecuteValues(params).execute().get();
        } catch (Exception e) {
            Log.e("DBHandler Error: ", "Problem with getAllFriends.");
        }
        try {
            JSONArray friends = json.getJSONArray(ConstantData.TAG_POSTS);
            for (int i = 0; i < friends.length(); i++) {
                JSONObject c = friends.getJSONObject(i);
                response.add(c.getString("friend"));
            }
        } catch (JSONException e) {
            Log.d("RankingView", "Problem with the json data");
        }
        return response;
    }
}


