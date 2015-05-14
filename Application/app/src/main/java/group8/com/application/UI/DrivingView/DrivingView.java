package group8.com.application.UI.DrivingView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import group8.com.application.Application.Controller;
import group8.com.application.R;
import group8.com.application.UI.mainView.menuView;

public class DrivingView extends Activity {

    private Button pauseButton;

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
                startActivityForResult(intent, 0);

            }
        });

    }


}