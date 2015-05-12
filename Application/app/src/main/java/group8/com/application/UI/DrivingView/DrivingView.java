package group8.com.application.UI.DrivingView;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import group8.com.application.R;

/**
 * Created by Hampus on 2015-05-12.
 */
public class DrivingView extends Activity {

    private Fragment speedFragment, fuelFragment, brakeFragment, distractionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);

        FragmentManager fragmentManager = getFragmentManager();
        speedFragment = fragmentManager.findFragmentById(R.id.driving_view_speed);

    }


}