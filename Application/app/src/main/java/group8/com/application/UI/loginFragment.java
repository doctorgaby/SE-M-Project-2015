package group8.com.application.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.R;

public class loginFragment extends Fragment implements View.OnClickListener {

    private ProgressDialog pDialog;
    Button login;
    EditText username;
    EditText password;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,
                container, false);
        this.view = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        login = (Button) view.findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.loginButton:
                new AsyncLogin().execute(ConstantData.TAG_ACTION_LOGIN);
                break;
            default:
                break;
        }
    }


    class AsyncLogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            Log.d("Preexecute", "Pre");
            super.onPreExecute();
            pDialog = new ProgressDialog(getView().getContext());
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Check for success tag
            JSONObject json;
            String tag = args[0];
            String user = "";

            if (tag.equals(ConstantData.TAG_ACTION_FBLOGIN))
                user = args[1];
            else if (tag.equals(ConstantData.TAG_ACTION_LOGIN))
                user = username.getText().toString();

            String pass = password.getText().toString();
            json = Controller.attemptLogin(tag, user, pass); // Actual call to database

            String message="Error.";
            try {
                message = json.getString(ConstantData.TAG_MESSAGE);
                if (json.getInt(ConstantData.TAG_SUCCESS) == 1) {
                    // save user data (only saves it on normal logins.
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(view.getContext());
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", user);
                    edit.apply();
                    Session.restart(user);

                    Fragment fragment = new start_menu_fragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.menuViewFrame, fragment, ConstantData.TAG_STARTFRAGMENT);
                    transaction.commit();
                }
            }
            catch (JSONException e) {
                Log.e("Register Error", e.toString());
            }

            return message;
        }

        protected void onPostExecute(String message) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            Toast.makeText(getActivity().getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
