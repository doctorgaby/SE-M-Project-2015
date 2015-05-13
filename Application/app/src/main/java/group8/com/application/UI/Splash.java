package group8.com.application.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import group8.com.application.R;

/**
 * Created by doctorgaby on 5/13/2015.
 */
public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, MainView.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}

