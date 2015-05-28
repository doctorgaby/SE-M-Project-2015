package group8.com.application.UI.DrivingView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import group8.com.application.Application.Controller;
import group8.com.application.R;
import group8.com.application.UI.mainView.menuView;
import group8.com.application.UI.alert.BrakesActivity;
import group8.com.application.UI.alert.DistractionActivity;
import group8.com.application.UI.alert.FuelActivity;
import group8.com.application.UI.alert.SpeedActivity;

public class DrivingView extends Activity {

    private Button pauseButton;
    private CountDownTimer alertTimer;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);

        pauseButton = (Button) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Controller.stopGrading();
                Intent intent = new Intent(v.getContext(), menuView.class);
                startActivity(intent);

            }
        });

        // The context is used to open the alert intents
        context = this.getBaseContext();
        startTimer();

    }

    /**
     * Start the timer, used to evaluate the alerts.
     * */
    private void startTimer() {

        // The timer runs for one second. When the timer is finished, it evaluates the alerts, and displays them if needed.
        // When the timer finishes, it restarts.
        alertTimer = new CountDownTimer(1000, 1000) {

                public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                if(Controller.evaluateSpeedAlert()) {
                    Intent intent = new Intent(context, SpeedActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }

                if(Controller.evaluateFuelConsumptionAlert()) {
                    Intent intent = new Intent(context, FuelActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }

                if(Controller.evaluateBrakeAlert()) {
                    Intent intent = new Intent(context, BrakesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }

                if(Controller.evaluateDriverDistractionLevelAlert()) {
                    Intent intent = new Intent(context, DistractionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }

                alertTimer.start();

            }

        }.start();

    }

}