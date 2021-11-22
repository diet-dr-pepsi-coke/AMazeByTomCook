package edu.wm.cs.cs301.tomcook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.tomcook.R;

public class PlayManuallyActivity extends AppCompatActivity {
    Button map, solution, walls, shortCut;
    ImageButton walk, turnLeft, turnRight, back, zoomIn, zoomOut;
    boolean mapShown = false, solutionShown = false, wallsShown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

        map = (Button) findViewById(R.id.buttonMap);
        solution = (Button) findViewById(R.id.buttonSolution);
        walls = (Button) findViewById(R.id.buttonWalls);
        shortCut = (Button) findViewById(R.id.buttonShortCut);
        walk = (ImageButton) findViewById(R.id.imageWalk);
        turnLeft = (ImageButton) findViewById(R.id.imageLeft);
        turnRight = (ImageButton) findViewById(R.id.imageRight);
        back = (ImageButton) findViewById(R.id.imageBack);
        zoomIn = (ImageButton) findViewById(R.id.imageZoomIn);
        zoomOut = (ImageButton) findViewById(R.id.imageZoomOut);

        shortCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWinningActivity();
            }
        });
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // robot walks
                Log.v(String.valueOf(this), "Walk 1 step forward");
            }
        });
        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // robot turns right
                Log.v(String.valueOf(this), "Turn right");
            }
        });
        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // robot turns left
                Log.v(String.valueOf(this), "Turn left");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // robot moves backwards
                Log.v(String.valueOf(this), "Walk 1 step backward");
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // maze increases in size
                Log.v(String.valueOf(this), "Maze size incremented by 10%");
            }
        });
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // maze decreases in size
                Log.v(String.valueOf(this), "Maze size decremented by 10%");
            }
        });
    }

    public void toggle(View view) {
        switch (view.getId()) {
            case R.id.buttonMap:
                if (mapShown==false) {
                    mapShown=true;
                Log.v(String.valueOf(this), "ShowMap: On");
                }
                else {mapShown=false;
                    Log.v(String.valueOf(this), "ShowMap: Off");}
                break;
            case R.id.buttonSolution:
                if (solutionShown==false) {
                    solutionShown=true;
                    Log.v(String.valueOf(this), "ShowSolution: On");}
                else {solutionShown=false;
                    Log.v(String.valueOf(this), "ShowSolution: Off");}
                break;
            case R.id.buttonWalls:
                if (wallsShown==false) {
                    wallsShown=true;
                    Log.v(String.valueOf(this), "ShowWalls: On");}
                else {wallsShown=false;
                    Log.v(String.valueOf(this), "ShowWalls: Off");}
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