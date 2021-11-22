package edu.wm.cs.cs301.tomcook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.tomcook.R;

public class GeneratingActivity extends AppCompatActivity {
    ProgressBar progressBar;
    String driver, sensors;
    TextView generating, robotQuality;
    Handler handler;
    boolean done = false;
    RadioButton Manual, Wizard, WallFollower, Premium, Mediocre, Soso, Shaky;
    Thread Progress;
    Button buttonManual, buttonAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        generating = (TextView) findViewById(R.id.textGenerating);
        robotQuality = (TextView) findViewById(R.id.textSensors);
        Manual = (RadioButton) findViewById(R.id.buttonManual);
        Wizard = (RadioButton) findViewById(R.id.buttonWizard);
        WallFollower = (RadioButton) findViewById(R.id.buttonWallFollower);
        Premium = (RadioButton) findViewById(R.id.buttonPremium);
            Premium.setVisibility(View.GONE);
        Mediocre = (RadioButton) findViewById(R.id.buttonMediocre);
            Mediocre.setVisibility(View.GONE);
        Soso = (RadioButton) findViewById(R.id.buttonSoso);
            Soso.setVisibility(View.GONE);
        Shaky = (RadioButton) findViewById(R.id.buttonShaky);
            Shaky.setVisibility(View.GONE);
        robotQuality.setVisibility(View.GONE);
        handler = new Handler();
        buttonManual = (Button) findViewById(R.id.buttonGo);
        buttonAnimation = (Button) findViewById(R.id.buttonAnimation);

        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayManuallyActivity();
            }
        });
        buttonAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayAnimationActivity();
            }
        });

        Progress = new Thread(new Runnable() {
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
                done = true;
            }
        });
        Progress.start();
        if (done == true) {
            if (driver == null) {
                Toast.makeText(this, R.string.WaitingSelection, Toast.LENGTH_LONG).show();
            }
            else if (driver == "Manual") {
                openPlayManuallyActivity();
            }
            else {
                openPlayAnimationActivity();
            }
        }
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
        if (Progress.isAlive()) {
        Toast.makeText(this, R.string.WaitingThread, Toast.LENGTH_LONG).show(); }
    }

    public void onRadioButtonDriverClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.buttonManual:
                if (checked) {
                    driver = "Manual";
                robotQuality.setVisibility(View.GONE);
                Premium.setVisibility(View.GONE);
                Mediocre.setVisibility(View.GONE);
                Soso.setVisibility(View.GONE);
                Shaky.setVisibility(View.GONE); }
                break;
            case R.id.buttonWizard:
                if (checked) {
                    driver = "Wizard";
                robotQuality.setVisibility(View.VISIBLE);
                Premium.setVisibility(View.VISIBLE);
                Mediocre.setVisibility(View.VISIBLE);
                Soso.setVisibility(View.VISIBLE);
                Shaky.setVisibility(View.VISIBLE); }
                break;
            case R.id.buttonWallFollower:
                if (checked) {
                    driver = "WallFollower";
                    robotQuality.setVisibility(View.VISIBLE);
                    Premium.setVisibility(View.VISIBLE);
                    Mediocre.setVisibility(View.VISIBLE);
                    Soso.setVisibility(View.VISIBLE);
                    Shaky.setVisibility(View.VISIBLE); }
                break;
        }
        if (Progress.isAlive()) {
            Toast.makeText(this, R.string.WaitingThread, Toast.LENGTH_LONG).show(); }
    }

    public void openPlayManuallyActivity() {
        Intent intent = new Intent(this, PlayManuallyActivity.class);
        startActivity(intent);
    }

    public void openPlayAnimationActivity() {
        Intent intent = new Intent(this, PlayAnimationActivity.class);
        startActivity(intent);
    }
}