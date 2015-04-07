package group8.com.application.UI.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group8.com.application.Application.Session;
import group8.com.application.Foundation.JSONParser;
import group8.com.application.R;
import group8.com.application.UI.MainView;

public class LoginView extends Activity implements OnClickListener {
    private EditText username, pass;
    private Button mSubmit;
    private Button mRegister;
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    // php login script location:
    // testing from a real server:
    private static final String LOGIN_URL = "http://semprojectgroup8.site50.net/project_systems_dev/index.php";
    // JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ACTION_LOGIN = "login";
    private static final String TAG_ACTION_FBLOGIN = "fblogin";

    //Facebook part
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Facebook Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //Regular initialization
        setContentView(R.layout.login_display);
        // setup input fields
        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        // setup buttons
        mSubmit = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);
        // register listeners
        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);

        //Facebook part

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebook: ", "SUCCESS");
                /*Profile profile = Profile.getCurrentProfile();
                GraphRequest request = new LoginClient.Request(
                        loginResult.getAccessToken(),
                        "/{user-id}",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                            }
                        }
                ).executeAsync();


                And at some point add
                new AttemptLogin().execute(TAG_ACTION_FBLOGIN);
                */

            }

            @Override
            public void onCancel() {
                Log.d("Facebook: ", "CANCEL");
                Toast.makeText(LoginView.this, "Canceled to log in with Facebook.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Facebook: ", "ERROR");
                Toast.makeText(LoginView.this, "Fail to log in with Facebook.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                new AttemptLogin().execute(TAG_ACTION_LOGIN);
                break;
            case R.id.register:
                Intent i = new Intent(this, RegisterView.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginView.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Check for success tag
            int success;
            String tag = args[0];
            String user;
            if (tag.equals(TAG_ACTION_FBLOGIN))
                user = "temp";                          ///////NEEDS TO CHANGE!!!!
            else
                user = username.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("action", tag));
                params.add(new BasicNameValuePair("username", user));
                params.add(new BasicNameValuePair("password", password));
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                // check your log for json response
                Log.d("Login attempt", json.toString());
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    // save user data
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(LoginView.this);
                    Editor edit = sp.edit();
                    edit.putString("username", user);
                    edit.commit();
                    Session.restart(user);
                    Intent i = new Intent(LoginView.this, MainView.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(LoginView.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
