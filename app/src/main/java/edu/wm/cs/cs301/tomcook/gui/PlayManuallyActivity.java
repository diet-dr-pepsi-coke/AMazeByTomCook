package edu.wm.cs.cs301.tomcook.gui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import javax.sql.StatementEvent;

import edu.wm.cs.cs301.tomcook.R;
import edu.wm.cs.cs301.tomcook.generation.CardinalDirection;
import edu.wm.cs.cs301.tomcook.generation.Constants;
import edu.wm.cs.cs301.tomcook.generation.Floorplan;
import edu.wm.cs.cs301.tomcook.generation.GlobalValues;
import edu.wm.cs.cs301.tomcook.generation.Maze;

public class PlayManuallyActivity extends AppCompatActivity {
    private Button map, solution, walls;
    private ImageButton walk, turnLeft, turnRight, back, zoomIn, zoomOut;
    private boolean mapMode = GlobalValues.mapMode, showSolution = GlobalValues.showSolution,
            showMaze = GlobalValues.showMaze, started;
    private MazePanel panel;
    private StatePlaying statePlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

        statePlaying = new StatePlaying();
        statePlaying.setMazeConfiguration(GlobalValues.mazeConfig);
        GlobalValues.panel = findViewById(R.id.mazePanel);
        if (GlobalValues.panel == null) {
            Log.v("Playing", "panel is null");
        } else {Log.v("Playing", "panel is not null");}
        statePlaying.start(GlobalValues.panel);

        map = (Button) findViewById(R.id.buttonMap);
        solution = (Button) findViewById(R.id.buttonSolution);
        walls = (Button) findViewById(R.id.buttonWalls);
        walk = (ImageButton) findViewById(R.id.imageWalk);
        turnLeft = (ImageButton) findViewById(R.id.imageLeft);
        turnRight = (ImageButton) findViewById(R.id.imageRight);
        back = (ImageButton) findViewById(R.id.imageBack);
        zoomIn = (ImageButton) findViewById(R.id.imageZoomIn);
        zoomOut = (ImageButton) findViewById(R.id.imageZoomOut);
        panel = (MazePanel) findViewById(R.id.mazePanel);



        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.keyDown(Constants.UserInput.UP, 0);
                // check termination, did we leave the maze?
                if (statePlaying.isOutside(statePlaying.px, statePlaying.py)) {
                    openWinningActivity(); }
                Log.v(String.valueOf(this), "Walk 1 step forward");
                //panel.setColor(0xFF00FF00);
                //panel.addFilledOval(600,400,800,600);

            }
        });
        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.keyDown(Constants.UserInput.RIGHT, 0);
                Log.v(String.valueOf(this), "Turn right");
            }
        });
        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.keyDown(Constants.UserInput.LEFT, 0);
                Log.v(String.valueOf(this), "Turn left");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.keyDown(Constants.UserInput.DOWN, 0);
                // check termination, did we leave the maze?
                if (statePlaying.isOutside(statePlaying.px, statePlaying.py)) {
                    openWinningActivity(); }
                Log.v(String.valueOf(this), "Walk 1 step backward");
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.mapView.incrementMapScale();
                Log.v(String.valueOf(this), "Maze size incremented by 10%");
            }
        });
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statePlaying.mapView.decrementMapScale();
                Log.v(String.valueOf(this), "Maze size decremented by 10%");
            }
        });
    }

    public void toggle(View view) {
        switch (view.getId()) {
            case R.id.buttonMap:
                showMaze = !showMaze;
                Log.v(String.valueOf(this), "ShowMap:" + showMaze);
                break;
            case R.id.buttonSolution:
                showSolution = !showSolution;
                Log.v(String.valueOf(this), "ShowSolution:" + showSolution);
                break;
            case R.id.buttonWalls:
                mapMode = !mapMode;
                Log.v(String.valueOf(this), "ShowWalls:" + mapMode);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }

    public void openWinningActivity() {
        Intent intent = new Intent(this, WinningActivity.class);
        startActivity(intent);
    }


}