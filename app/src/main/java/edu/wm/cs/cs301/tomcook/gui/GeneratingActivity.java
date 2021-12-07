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

import java.util.Random;

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
    private boolean done = false, perfect, selected = false;
    private RadioButton Manual, Wizard, WallFollower, Premium, Mediocre, Soso, Shaky;
    private Thread Progress;
    protected MazeFactory factory;
    private int seed = GlobalValues.seed, skillLevel, percentdone;
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
        Random random = new Random();
        seed = random.nextInt();
        GlobalValues.seed = seed;

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
                Log.v("Generating", "done" + done);
                if (GlobalValues.mazeConfig == null) {
                    Log.v("Generating", "maze is null"); }
                else { Log.v("Generating", "maze is not null"); }
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
                    selected = true;
                Log.v(String.valueOf(this), "Premium robot selected");
                openPlayAnimationActivity();};
                break;
            case R.id.buttonMediocre:
                if (checked) {
                    sensors = "1001";
                    selected = true;
                Log.v(String.valueOf(this), "Mediocre robot selected");
                openPlayAnimationActivity();};
                break;
            case R.id.buttonSoso:
                if (checked) {
                    sensors = "0110";
                    selected = true;
                Log.v(String.valueOf(this), "So-so robot selected");
                openPlayAnimationActivity();}
                break;
            case R.id.buttonShaky:
                if (checked) {
                    sensors = "0000";
                    selected = true;
                Log.v(String.valueOf(this), "Shaky robot selected");
                openPlayAnimationActivity();}
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
                    selected = true;
                robotQuality.setVisibility(View.GONE);
                Premium.setVisibility(View.GONE);
                Mediocre.setVisibility(View.GONE);
                Soso.setVisibility(View.GONE);
                Shaky.setVisibility(View.GONE);
                openPlayManuallyActivity();}
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
                Shaky.setVisibility(View.VISIBLE);
                }
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
                    Shaky.setVisibility(View.VISIBLE);
                }
                break;
        }
        if (Progress.isAlive()) {
            Toast.makeText(this, R.string.WaitingThread, Toast.LENGTH_SHORT).show(); }
        Log.v("Geerating", "selected: " + selected);
    }

    public void openPlayManuallyActivity() {
        Intent intent = new Intent(this, PlayManuallyActivity.class);
            while (!selected || !done) {
                Log.v("Generating", "done: " + ", selected: " + selected);
            try {
                wait(); }
            catch (InterruptedException e) {
                Log.v("Generating", e.toString());
            }
        }
            Log.v("Generating", "Going to PlayManually");
        startActivity(intent);
    }

    public synchronized void openPlayAnimationActivity() {
        Intent intent = new Intent(this, PlayAnimationActivity.class);
            while (!selected || !done) {
                Log.v("Generating", "done: " + done + ", selected: " + selected);
            try {
                wait(); }
            catch (InterruptedException e) {
                Log.v("Generating", e.toString());
                }
        }
        Log.v("Generating", "Going to PlayAnimation");
        intent.putExtra("DRIVER", driver);
        intent.putExtra("SENSOR", sensors);
        startActivity(intent);
    }

    public synchronized void generateMaze() {
        factory.order(this);
        factory.waitTillDelivered();
        done = true;
        if (selected)
            notify();
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