package edu.wm.cs.cs301.tomcook.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

        Progress = new Thread(new Runnable() {
            int percent = progressBar.getProgress();

            public void run() {
                Log.v(String.valueOf(this), "creating maze");
                while (percent < 100) {
                    percent += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(percent);
                            generating.setText(percent + "%");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (driver == null) {
                    Toast.makeText(GeneratingActivity.this, R.string.WaitingSelection, Toast.LENGTH_SHORT).show();
                }
                else if (driver == "Manual") {
                    openPlayManuallyActivity();
                }
                else {
                    openPlayAnimationActivity();
                }
            }
        });
        Progress.start();
    }

    public void onRadioButtonSensorsClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.buttonPremium:
                if (checked) {
                    sensors = "1111";
                Log.v(String.valueOf(this), "Premium robot selected");
                Toast.makeText(this, "Premium robot selected", Toast.LENGTH_SHORT).show(); }
                break;
            case R.id.buttonMediocre:
                if (checked) {
                    sensors = "1001";
                Log.v(String.valueOf(this), "Mediocre robot selected");
                Toast.makeText(this, "Mediocre robot selected", Toast.LENGTH_SHORT).show(); }
                break;
            case R.id.buttonSoso:
                if (checked) {
                    sensors = "0110";
                Log.v(String.valueOf(this), "So-so robot selected");
                Toast.makeText(this, "So-so robot selected", Toast.LENGTH_SHORT).show(); }
                break;
            case R.id.buttonShaky:
                if (checked) {
                    sensors = "0000";
                Log.v(String.valueOf(this), "Shaky robot selected");
                Toast.makeText(this, "Shaky robot selected", Toast.LENGTH_SHORT).show(); }
                break;
        }
        if (Progress.isAlive()) {
        Toast.makeText(this, R.string.WaitingThread, Toast.LENGTH_SHORT).show(); }
    }

    public void onRadioButtonDriverClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.buttonManual:
                if (checked) {
                    driver = "Manual";
                    Log.v(String.valueOf(this), "Manual driver selected");
                    Toast.makeText(this, "Manual driver selected", Toast.LENGTH_SHORT).show();
                robotQuality.setVisibility(View.GONE);
                Premium.setVisibility(View.GONE);
                Mediocre.setVisibility(View.GONE);
                Soso.setVisibility(View.GONE);
                Shaky.setVisibility(View.GONE); }
                break;
            case R.id.buttonWizard:
                if (checked) {
                    driver = "Wizard";
                    Log.v(String.valueOf(this), "Wizard driver selected");
                    Toast.makeText(this, "Wizard driver selected", Toast.LENGTH_SHORT).show();
                robotQuality.setVisibility(View.VISIBLE);
                Premium.setVisibility(View.VISIBLE);
                Mediocre.setVisibility(View.VISIBLE);
                Soso.setVisibility(View.VISIBLE);
                Shaky.setVisibility(View.VISIBLE); }
                break;
            case R.id.buttonWallFollower:
                if (checked) {
                    driver = "WallFollower";
                    Log.v(String.valueOf(this), "Wizard driver selected");
                    Toast.makeText(this, "Wizard driver selected", Toast.LENGTH_SHORT).show();
                    robotQuality.setVisibility(View.VISIBLE);
                    Premium.setVisibility(View.VISIBLE);
                    Mediocre.setVisibility(View.VISIBLE);
                    Soso.setVisibility(View.VISIBLE);
                    Shaky.setVisibility(View.VISIBLE); }
                break;
        }
        if (Progress.isAlive()) {
            Toast.makeText(this, R.string.WaitingThread, Toast.LENGTH_SHORT).show(); }
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