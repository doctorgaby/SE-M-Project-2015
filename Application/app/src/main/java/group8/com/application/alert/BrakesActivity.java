
package group8.com.application.alert;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import group8.com.application.R;


public class BrakesActivity extends Activity {


    CountDownTimer cdt;  // Countdown timer that specifies how long the activity is visible

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brakes);

        cdt = new CountDownTimer(4000, 1000) {  //4 seconds

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                //Intent nextScreen = new Intent(getApplicationContext(), MainView.class);

                finishAndRemoveTask ();

            }
        };

        //Start the timer
        cdt.start();

    }



}
