
package group8.com.application.alert;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

import group8.com.application.R;


public class FuelActivity extends Activity {


    CountDownTimer cdt;  // Countdown timer that specifies how long the activity is visible

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fuel);

        cdt = new CountDownTimer(4000, 1000) {  //4 seconds

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                //Intent nextScreen = new Intent(getApplicationContext(), mainView.class);

                finish();

            }
        };

        //Start the timer
        cdt.start();

        // Warning sound
        MediaPlayer mpAlert = MediaPlayer.create(this,R.raw.audio2);

        mpAlert.start();

    }



}
