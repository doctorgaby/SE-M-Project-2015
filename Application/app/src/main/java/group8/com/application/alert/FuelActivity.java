
package group8.com.application.alert;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;

import group8.com.application.R;


public class FuelActivity extends Activity {


    CountDownTimer cdt;  // Countdown timer that specifies how long the activity is visible

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        cdt = new CountDownTimer(3000, 1000) {  //The 3000 is 3 seconds

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                // Defines a new activity to change to- it is supposed to chane to the main view when program
                // but for now for demonstration purposes it will change to another activity untill main view is implemente
                Intent nextScreen = new Intent(getApplicationContext(), DistractionActivity.class); //Change to main activity

                // starting new activity
                startActivity(nextScreen);
            }
        };

        //Start the timer
        cdt.start();







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fuel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
