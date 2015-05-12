package group8.com.application.UI.DrivingView.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import group8.com.application.Application.Controller;
import group8.com.application.R;

/**
 * Created by Hampus on 2015-05-11.
 */
public class DistractionFragment extends Fragment {

    private CountDownTimer pointsTimer;
    private LinearLayout background;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        startTimer();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_distraction, container, false);
        background = (LinearLayout) view.findViewById(R.id.distraction_fragment_background);

        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private Color evaluatePoints(int points) {
        return null;
    }

    private void startTimer() {

        pointsTimer = new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                int driverDistractionLevelScore = Controller.getDriverDistractionLevelScore();

                if(driverDistractionLevelScore >= 65)
                    background.setBackgroundResource(R.drawable.fragment_background_green);
                else if(driverDistractionLevelScore >= 50)
                    background.setBackgroundResource(R.drawable.fragment_background_yellow);
                else
                    background.setBackgroundResource(R.drawable.fragment_background_red);

                pointsTimer.start();
            }

        }.start();

    }

}