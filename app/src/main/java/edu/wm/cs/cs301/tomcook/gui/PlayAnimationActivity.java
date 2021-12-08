package edu.wm.cs.cs301.tomcook.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import edu.wm.cs.cs301.tomcook.R;
import edu.wm.cs.cs301.tomcook.generation.Constants;
import edu.wm.cs.cs301.tomcook.generation.GlobalValues;

public class PlayAnimationActivity extends AppCompatActivity {

    TextView front, left, right, back;
    boolean frontOn = true, leftOn = true, rightOn = true, backOn = true, playing = false, mapShown  = GlobalValues.showMaze;
    ProgressBar energy;
    int animationSpeed = 100, odometer = GlobalValues.stepsWalked, energyLeft=3500;
    Slider speed;
    ImageButton zoomIn, zoomOut;
    Button play, showMap;
    MazePanel panel;
    private String driverString, sensorsString;
    private StatePlaying statePlaying;
    private ReliableRobot robot;
    private RobotDriver driver;
    private Handler handler;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);
        Intent intent = this.getIntent();
        driverString = intent.getStringExtra("DRIVER");
        sensorsString = intent.getStringExtra("SENSOR");

        handler = new Handler();

        statePlaying = new StatePlaying();
        statePlaying.setMazeConfiguration(GlobalValues.mazeConfig);
        GlobalValues.panel = findViewById(R.id.mazePanel);
        if (GlobalValues.panel == null) {
            Log.v("Playing", "panel is null");
        } else {Log.v("Playing", "panel is not null");}
        statePlaying.start(GlobalValues.panel);

        energy = (ProgressBar) findViewById(R.id.progressBarBattery);
        play = (Button) findViewById(R.id.imagePlay);
        showMap = (Button) findViewById(R.id.buttonShowMap);
        zoomIn = (ImageButton) findViewById(R.id.imageZoomIn);
        zoomOut = (ImageButton) findViewById(R.id.imageZoomOut);
        speed = (Slider) findViewById(R.id.sliderSpeed);
        front = (TextView) findViewById(R.id.textViewFront);
        right = (TextView) findViewById(R.id.textViewRight);
        left = (TextView) findViewById(R.id.textViewLeft);
        back = (TextView) findViewById(R.id.textViewBack);
        panel = (MazePanel) findViewById(R.id.mazePanel);
        isOn();
        speed.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                switch ((int) value) {
                    case 50:
                        animationSpeed = 750;
                        break;
                    case 100:
                        animationSpeed = 500;
                        break;
                    case 150:
                        animationSpeed = 250;
                        break;
                    case 200:
                        animationSpeed = 50;
                        break;
                }
                //TODO
                // add toasts to tell user what speed they selected
                Toast.makeText(getApplicationContext(), "Speed: " + value + "%", Toast.LENGTH_SHORT).show();
                Log.v(String.valueOf(this), "animation speed set to " + animationSpeed);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playing == false) {
                    handler.removeCallbacks(traverseMaze);
                    handler.postDelayed(traverseMaze, 50);
                    Log.v(String.valueOf(this), "animation has started playing");
                    play.setBackgroundResource(R.drawable.pause);

                    playing = true;
                }
                else {
                    handler.removeCallbacks(traverseMaze);
                    Log.v(String.valueOf(this), "animation has stopped");
                    play.setBackgroundResource(R.drawable.play);

                    playing = false;
                }
            }
        });
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.keyDown(Constants.UserInput.ZOOMOUT, 0);
                Log.v(String.valueOf(this), "Maze size incremented by 10%");
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.keyDown(Constants.UserInput.ZOOMIN, 0);
                Log.v(String.valueOf(this), "Maze size decremented by 10%");
            }
        });
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.keyDown(Constants.UserInput.TOGGLELOCALMAP, 0);
                statePlaying.keyDown(Constants.UserInput.TOGGLEFULLMAP, 0);
                statePlaying.keyDown(Constants.UserInput.TOGGLESOLUTION, 0);
                statePlaying.draw();
                Log.v("Animation", "Map shown: " + mapShown);
                }
        });


        if (driverString.equals("Wizard")) {
            robot = new ReliableRobot();
            driver = new Wizard();
        }
        else {
            robot = new ReliableRobot();
            driver = new WallFollower();
        }
        setUpAnimation();
    }

    Runnable traverseMaze = new Runnable() {
        @Override
        public void run() {
            move();
            handler.postDelayed(this, animationSpeed);
            if (robot.isAtExit()) {
                openWinningActivity();
            }
            if (robot.hasStopped()) {
                openLosingActivity();
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
        finish();
    }

    private void move() {
        try {
            driver.drive1Step2Exit();
        } catch (Exception e) {
            Log.v("Exception", e.toString());
        }
        energy.setProgress(3500-(int)robot.getBatteryLevel());
        Log.v("Animation", "energy " + robot.getBatteryLevel());
    }

    private void setUpAnimation() {
        // give the robot and driver the pertinent information for distance sensing,
        // room sensing, and exit sensing, etc.
        driver.setRobot(robot);
        driver.setMaze(GlobalValues.mazeConfig);
        robot.setController(statePlaying);
        robot.setBatteryLevel(3500);
        robot.resetOdometer();
        // this method must be called after the robot has its controller set to the
        // one pertaining to this maze
        robot.setMaze(GlobalValues.mazeConfig);
    }

    @SuppressLint("ResourceAsColor")
    public void isOn() {
        if (frontOn) {
            front.setTextColor(R.color.green);
        } else {
            front.setTextColor(R.color.red);
        }
        if (leftOn) {
            left.setTextColor(R.color.green);
        } else {
            left.setTextColor(R.color.red);
        }
        if (rightOn) {
            right.setTextColor(R.color.green);
        } else {
            right.setTextColor(R.color.red);
        }
        if (backOn) {
            back.setTextColor(R.color.green);
        } else {
            back.setTextColor(R.color.red);
        }
    }

    public void openLosingActivity() {
        handler.removeCallbacks(traverseMaze);
        Intent intent = new Intent(this, LosingActivity.class);
        intent.putExtra("ORIGIN", "PlayWinning");
        intent.putExtra("STEPS_WALKED", odometer);
        finish();
        startActivity(intent);
    }

    public void openWinningActivity() {
        handler.removeCallbacks(traverseMaze);
        Intent intent = new Intent(this, WinningActivity.class);
        intent.putExtra("ORIGIN", "PlayWinning");
        intent.putExtra("STEPS_WALKED", odometer);
        finish();
        startActivity(intent);
    }
}