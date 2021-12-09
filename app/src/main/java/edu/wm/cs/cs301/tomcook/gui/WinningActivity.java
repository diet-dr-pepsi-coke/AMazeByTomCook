package edu.wm.cs.cs301.tomcook.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.wm.cs.cs301.tomcook.R;
import edu.wm.cs.cs301.tomcook.generation.GlobalValues;
import edu.wm.cs.cs301.tomcook.gui.AMazeActivity;

public class WinningActivity extends AppCompatActivity {

    TextView odometer, shortestPath, consumption;
    Button playAgain;
    private int stepsWalked, shortestPathTaken, energy;
    private String origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        Intent intent = this.getIntent();
        stepsWalked = intent.getIntExtra("STEPS_WALKED", 0);
        shortestPathTaken = GlobalValues.mazeConfig.getDistanceToExit(
                GlobalValues.mazeConfig.getStartingPosition()[0],
                GlobalValues.mazeConfig.getStartingPosition()[1]);
        origin = intent.getStringExtra("ORIGIN");
        energy = 3500 - intent.getIntExtra("ENERGY_CONSUMED", 0);


        odometer = (TextView) findViewById(R.id.textViewOdometerWon);
        shortestPath = (TextView) findViewById(R.id.textViewShortestWon);
        consumption = (TextView) findViewById(R.id.textViewConsumptionWon);
        playAgain = (Button) findViewById(R.id.buttonPlayAgainWon);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart();
                Log.v(String.valueOf(this), "Returning to Title Screen");
            }
        });
        odometer.setText(getString(R.string.odometer) + " " + stepsWalked);
        shortestPath.setText(getString(R.string.shortest) + " " + shortestPathTaken);
        if (origin.equals("PlayManually")) {
            consumption.setText(getString(R.string.Consumption) + " N/A"); }
        else {
            consumption.setText(getString(R.string.Consumption) + " " + energy);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
    public void restart() {
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
}