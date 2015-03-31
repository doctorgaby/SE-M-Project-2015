package group8.com.application.UI.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import group8.com.application.R;

public class LoginView extends Activity {
    Button loginBtn;
    Button backBtn;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_display);
        loginBtn = (Button) findViewById(R.id.loginViewLoginButton);
        backBtn = (Button) findViewById(R.id.loginViewBackButton);
        username = (EditText) findViewById(R.id.loginViewusernameTxt);
        password = (EditText) findViewById(R.id.loginViewPasswordTxt);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DoLogin
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), IntroView.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}