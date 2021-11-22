package edu.wm.cs.cs301.tomcook;

import android.content.Intent;
import android.os.Bundle;
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
    }

    public void toggle(View view) {
        switch (view.getId()) {
            case R.id.buttonMap:
                if (mapShown==false) {
                    mapShown=true; }
                else {mapShown=false;}
                break;
            case R.id.buttonSolution:
                if (solutionShown==false) {
                    solutionShown=true; }
                else {solutionShown=false;}
                break;
            case R.id.buttonWalls:
                if (wallsShown==false) {
                    wallsShown=true; }
                else {wallsShown=false;}
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