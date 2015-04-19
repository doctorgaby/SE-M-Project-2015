package group8.com.application.Application.Database;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

import group8.com.application.Foundation.JSONParser;
import group8.com.application.Model.ConstantData;

/**
 * Created by enriquecordero on 17/04/15.
 */
public class doExecuteValues extends AsyncTask<String, String, JSONObject> {
    List<NameValuePair> params;
    String url = ConstantData.INDEX_URL;
    String post = "POST";
    private static JSONParser jsonParser = new JSONParser();

    public doExecuteValues(List<NameValuePair> params) {
        this.params = params;
    }

    protected JSONObject doInBackground(String... args) {
        JSONObject json = jsonParser.makeHttpRequest(url, post, params);
        return json;
    }
}