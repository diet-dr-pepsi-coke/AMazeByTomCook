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
import edu.wm.cs.cs301.tomcook.generation.GlobalValues;
import edu.wm.cs.cs301.tomcook.generation.Maze;
import edu.wm.cs.cs301.tomcook.generation.MazeContainer;
import edu.wm.cs.cs301.tomcook.generation.Order;
import edu.wm.cs.cs301.tomcook.generation.MazeFactory;

public class GeneratingActivity extends AppCompatActivity implements Order {
    private ProgressBar progressBar;
    private String driver, sensors, filename;
    private TextView generating, robotQuality, textProgress;
    private Handler handler;
    private boolean done = false, perfect;
    private RadioButton Manual, Wizard, WallFollower, Premium, Mediocre, Soso, Shaky;
    private Thread Progress;
    protected MazeFactory factory;
    private int seed, skillLevel, percentdone;
    private Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        factory = new MazeFactory();
        filename = null;
        factory = new MazeFactory();
        skillLevel = GlobalValues.skillLevel;
        builder = GlobalValues.builder;
        perfect = GlobalValues.perfect;
        seed = GlobalValues.seed;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textProgress = (TextView) findViewById(R.id.textProgress);
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
                generateMaze();
                if (GlobalValues.mazeConfig == null) {
                    Log.v("Generating", "maze is null"); }
                else { Log.v("Generating", "maze is not null"); }
                openPlayManuallyActivity();
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
                Log.v(String.valueOf(this), "Premium robot selected"); };
                break;
            case R.id.buttonMediocre:
                if (checked) {
                    sensors = "1001";
                Log.v(String.valueOf(this), "Mediocre robot selected"); };
                break;
            case R.id.buttonSoso:
                if (checked) {
                    sensors = "0110";
                Log.v(String.valueOf(this), "So-so robot selected"); }
                break;
            case R.id.buttonShaky:
                if (checked) {
                    sensors = "0000";
                Log.v(String.valueOf(this), "Shaky robot selected"); }
                break;
        }
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

    public void generateMaze() {
        factory.order(this);
        factory.waitTillDelivered();
    }

    @Override
    public int getSkillLevel() {
        return skillLevel;
    }

    @Override
    public Builder getBuilder() {
        return builder;
    }

    @Override
    public boolean isPerfect() {
        return perfect;
    }

    @Override
    public int getSeed() {
        return seed;
    }

    @Override
    public void deliver(Maze mazeConfig) {

    }

    @Override
    public void updateProgress(int percentage) {
        if (this.percentdone < percentage && percentage <= 100) {
            this.percentdone = percentage;
            progressBar.setProgress(percentdone);
        }
    }
}