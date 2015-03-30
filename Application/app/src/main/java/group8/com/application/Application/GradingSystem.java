package group8.com.application.Application;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Hampus on 2015-03-18.
 */
public abstract class GradingSystem {

    private float currentSpeed;
    private int lastDistractionLevel;
    private int goodFuelCount, badFuelCount;
    private boolean shouldAddToBrakeCount = true;
    private boolean shouldAddToGoodFuelCount = true, shouldAddToBadFuelCount = true;

    private double totalScore;
    private double speedScore = 50, distractionLevelScore = 50, brakingScore = 50, fuelConsumptionScore = 50;

    private CountDownTimer timer;

    public double getTotalScore() {

        totalScore = (speedScore + distractionLevelScore + brakingScore + fuelConsumptionScore) / 4;

        return totalScore;

    }

    public double getSpeedScore() {

        return speedScore;

    }

    public double getDistractionLevelScore() {

        return distractionLevelScore;

    }

    public double getBrakingScore() {

        return brakingScore;

    }

    public double getFuelConsumptionScore() {

        return fuelConsumptionScore;

    }

    public void updateSpeedScore() {}

    public void updateDistractionLevelScore(int distractionLevel) {

        if(distractionLevel == 3 && lastDistractionLevel < 3)
            distractionLevelScore--;

        if(distractionLevel == 4 && lastDistractionLevel < 4)
            distractionLevelScore -= 2;

        if(distractionLevel == 0 && lastDistractionLevel != 0)
            distractionLevelScore++;

        lastDistractionLevel = distractionLevel;

    }

    public void updateBrakingScore(int brake, TextView db) {

        if(brake == 0) { // If the brake is in a "Released" state.

            shouldAddToBrakeCount = true; // The car has released the brake before it brakes again

            startNewBrakeTimer(db); // Start a new countdown. When the countdown has finnished, the braking score increases by 1.

        } else { // If the brake is in "Pressed state"

            if(timer != null)
                timer.cancel();

            if (shouldAddToBrakeCount) // If the brake is active and it has been released between brakes(it should not count the same brake several times)
                brakingScore--;

        }

    }

    public void updateFuelConsumptionScore(float fuelConsumption) {

        if(fuelConsumption > 60.0) { // If the fuel consumption is "good".
            fuelConsumptionScore++;

        } else // If the fuel consumption is "bad" and it has been good before(it should not count the same fuel count several times)
            fuelConsumptionScore--;



    }

    private void startNewBrakeTimer(final TextView db) {

        timer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                System.out.println("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                brakingScore++;
                db.setText(Double.toString(getBrakingScore()));
                startNewBrakeTimer(db);
            }
        }.start();

    }

}
