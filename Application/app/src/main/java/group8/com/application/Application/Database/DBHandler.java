package group8.com.application.Application.Database;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group8.com.application.Application.Session;
import group8.com.application.Foundation.JSONParser;
import group8.com.application.Model.ConstantData;
import group8.com.application.Model.DataList;

public abstract class DBHandler {
    //private static JSONParser jsonParser = new JSONParser();

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
        // getting measurement details by making HTTP request
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
        // getting measurement details by making HTTP request
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
        // getting measurement details by making HTTP request
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
        // getting measurement details by making HTTP request
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
    public static void setMeasurements(String user) {

        JSONObject json = new JSONObject();
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
            // Could not execute request
            Log.d("DBHandler.java", "Exception in setMeasurements(): " + ex.getMessage());
        }

        Log.d("success", success);

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
    public static int attemptLogin(String tag, String username, String password) {

        // Variables
        JSONParser jsonParser = new JSONParser();
        JSONObject json = new JSONObject();
        int success;

        // Attempting to register user
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

        // Log the results to the console
        if(success == 1)
            Log.d("Login Successful!", json.toString());
        else try {
            Log.d("Login Failure!", json.getString(ConstantData.TAG_MESSAGE));
        } catch(JSONException ex) {
            // Can't get json String
        }

        return success;

    }

    /**
     * Verifies the username and password.
     *
     * @return 0, if the login was unsuccessful.
     *         1, if the login was successful.
     * */
    public static int registerUser(String username, String password) {

        // Variables
        JSONParser jsonParser = new JSONParser();
        JSONObject json = new JSONObject();
        int success;

        // Attempting to register user
        try {

            // Building Parameters
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

        // Log the results to the console
        if(success == 1)
            Log.d("Login Successful!", json.toString());
        else try {
            Log.d("Login Failure!", json.getString(ConstantData.TAG_MESSAGE));
        } catch(JSONException ex) {
            // Can't get json String
        }

        return success;

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
/*
    private class doExecuteValues extends AsyncTask <String, String, JSONObject> {
        List<NameValuePair> params;
        String url;
        String post;
        JSONParser jsonParser;

        public doExecuteValues (String url, String post, List<NameValuePair> params, JSONParser jsonParser) {
            //params = params2;
            //url = url2;
            //post = post2;
            this.params = params;
            this.url = url;
            this.post = post;
            this.jsonParser = jsonParser;
        }

        protected JSONObject doInBackground(String... args) {
            JSONObject json = jsonParser.makeHttpRequest(url, post, params);
            return json;
        }
    }*/
}


