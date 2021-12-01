package edu.wm.cs.cs301.tomcook.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import edu.wm.cs.cs301.tomcook.R;

public class PlayAnimationActivity extends AppCompatActivity {

    TextView front, left, right, back;
    boolean frontOn = true, leftOn = true, rightOn = true, backOn = true, playing = false, mapShown = false;
    ProgressBar energy;
    int animationSpeed = 100;
    Slider speed;
    ImageButton zoomIn, zoomOut;
    Button play, showMap;
    MazePanel panel;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

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
                animationSpeed = (int) value;
                Log.v(String.valueOf(this), "animation speed set to " + animationSpeed);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playing == false) {
                    //start the animation
                    Log.v(String.valueOf(this), "animation has started playing");
                    play.setBackgroundResource(R.drawable.pause);

                    playing = true;
                }
                else {
                    //pause the animation
                    Log.v(String.valueOf(this), "animation has stopped");
                    play.setBackgroundResource(R.drawable.play);

                    playing = false;
                }
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // maze zooms in
                Log.v(String.valueOf(this), "Maze size incremented by 10%");
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // maze zooms out
                Log.v(String.valueOf(this), "Maze size decremented by 10%");
            }
        });
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapShown==false) {
                // turn on map
                    mapShown = true;
                Log.v(String.valueOf(this), "ShowMap: On"); }
                else {
                    // turn map off
                    mapShown = false;
                    Log.v(String.valueOf(this), "ShowMap: Off"); }
                }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }

    @SuppressLint("ResourceAsColor")
    public void isOn() {
        if (frontOn == true) {
            front.setTextColor(R.color.green);
        } else {
            front.setTextColor(R.color.red);
        }
        if (leftOn == true) {
            left.setTextColor(R.color.green);
        } else {
            left.setTextColor(R.color.red);
        }
        if (rightOn == true) {
            right.setTextColor(R.color.green);
        } else {
            right.setTextColor(R.color.red);
        }
        if (backOn == true) {
            back.setTextColor(R.color.green);
        } else {
            back.setTextColor(R.color.red);
        }
    }

    public void openLosingActivity() {
        Intent intent = new Intent(this, LosingActivity.class);
        startActivity(intent);
    }

    public void openWinningActivity() {
        Intent intent = new Intent(this, WinningActivity.class);
        startActivity(intent);
    }
}