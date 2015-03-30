package group8.com.application.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import group8.com.application.R;

/**
 * Created by enriquecordero on 27/03/15.
 */
public class LoginView extends Activity {
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_display);

        loginBtn = (Button) findViewById(R.id.loginDisplay_loginButton);


        loginBtn.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainView.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
