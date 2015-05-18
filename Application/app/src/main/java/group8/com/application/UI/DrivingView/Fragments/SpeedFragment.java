package group8.com.application.UI.DrivingView.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import group8.com.application.Application.Controller;
import group8.com.application.R;

public class SpeedFragment extends Fragment {

    private CountDownTimer pointsTimer;
    private LinearLayout background;
    private TextView scoreTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        startTimer();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_speed, container, false);
        background = (LinearLayout) view.findViewById(R.id.speed_fragment_background);
        scoreTextView = (TextView) view.findViewById(R.id.speed_fragment_score);

        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void startTimer() {

        pointsTimer = new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                int speedScore = Controller.getSpeedScore();

                if(speedScore >= 65)
                    background.setBackgroundResource(R.drawable.fragment_background_green);
                else if(speedScore >= 50)
                    background.setBackgroundResource(R.drawable.fragment_background_yellow);
                else
                    background.setBackgroundResource(R.drawable.fragment_background_red);

                scoreTextView.setText("" + speedScore);

                pointsTimer.start();

            }

        }.start();

    }

}