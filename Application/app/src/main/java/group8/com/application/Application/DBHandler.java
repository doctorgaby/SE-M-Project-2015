package group8.com.application.Application;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group8.com.application.Foundation.JSONParser;
import group8.com.application.Model.ConstantData;
import group8.com.application.Model.DataList;

public abstract class DBHandler {
    //private static JSONParser jsonParser = new JSONParser();

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
    //                              ***** Helper Methods *****
    //                              *****                *****
    //                              **************************

    /**
     * @param
     */
    private static void jsonToList(DataList list, JSONObject json) {
        try {
            JSONArray data = json.getJSONArray(ConstantData.TAG_POSTS);
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                //gets the content of each JSON object
                Integer speed = c.getInt(ConstantData.TAG_SPEED);
                Integer brake = c.getInt(ConstantData.TAG_BRAKE);
                Integer fuel = c.getInt(ConstantData.TAG_FUEL);
                Integer distraction = c.getInt(ConstantData.TAG_DISTRACTION);
                Integer measuredAt = c.getInt(ConstantData.TAG_MEASUREDAT);
                list.setBrake(measuredAt, brake);
                list.setDriverDistractionLevel(measuredAt, distraction);
                list.setSpeed(measuredAt, speed);
                list.setFuelConsumption(measuredAt, fuel);
                Log.d("number: " + i, "value: " + measuredAt);
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


