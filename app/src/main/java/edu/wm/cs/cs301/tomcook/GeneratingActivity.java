package edu.wm.cs.cs301.tomcook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class GeneratingActivity extends AppCompatActivity {
    ProgressBar progressBar;
    String driver;
    String sensors;
    TextView generating;
    Handler handler;
    RadioButton Manual, Wizard, WallFollower, Premium, Mediocre, Soso, Shaky;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        generating = (TextView) findViewById(R.id.textGenerating);
        Manual = (RadioButton) findViewById(R.id.buttonManual);
        Wizard = (RadioButton) findViewById(R.id.buttonWizard);
        WallFollower = (RadioButton) findViewById(R.id.buttonWallFollower);
        Premium = (RadioButton) findViewById(R.id.buttonPremium);
        Mediocre = (RadioButton) findViewById(R.id.buttonMediocre);
        Soso = (RadioButton) findViewById(R.id.buttonSoso);
        Shaky = (RadioButton) findViewById(R.id.buttonShaky);
        handler = new Handler();

        new Thread(new Runnable() {
            int percent = progressBar.getProgress();

            public void run() {
                while (percent < 100) {
                    percent += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(percent);
                            generating.setText(percent + "%");
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void onRadioButtonSensorsClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.buttonPremium:
                if (checked)
                    sensors = "1111";
                break;
            case R.id.buttonMediocre:
                if (checked)
                    sensors = "1001";
                break;
            case R.id.buttonSoso:
                if (checked)
                    sensors = "0110";
                break;
            case R.id.buttonShaky:
                if (checked)
                    sensors = "0000";
                break;
        }
    }

    public void onRadioButtonDriverClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.buttonManual:
                if (checked)
                    driver = "Manual";
                break;
            case R.id.buttonWizard:
                if (checked)
                    driver = "Wizard";
                break;
            case R.id.buttonWallFollower:
                if (checked)
                    driver = "WallFollower";
                break;
        }
    }
}