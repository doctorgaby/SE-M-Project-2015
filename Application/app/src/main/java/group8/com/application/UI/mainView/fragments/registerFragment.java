package group8.com.application.UI.mainView.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.R;

public class registerFragment extends Fragment implements View.OnClickListener {
    private ProgressDialog pDialog;
    Button register;
    TextView username;
    TextView password;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment,
                container, false);
        this.view = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        register = (Button) getView().findViewById(R.id.registerButton);
        register.setOnClickListener(this);
        username = (TextView) getView().findViewById(R.id.username);
        password = (TextView) getView().findViewById(R.id.password);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.registerButton:
                new CreateUser().execute();
                break;
        }
    }

    class CreateUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getView().getContext());
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            // Check for success tag
            JSONObject success;
            String user = username.getText().toString();
            String pass = password.getText().toString();

            success = Controller.registerUser(user, pass); // Actual call to database
            String message = "Error.";
            try {
                message = success.getString(ConstantData.TAG_MESSAGE);
                if (success.getInt(ConstantData.TAG_SUCCESS) == 1) {

                    // save user data
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getView().getContext());
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
