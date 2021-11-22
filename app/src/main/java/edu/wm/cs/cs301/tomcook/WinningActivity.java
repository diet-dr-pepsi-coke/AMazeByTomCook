package edu.wm.cs.cs301.tomcook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.wm.cs.cs301.tomcook.R;

public class WinningActivity extends AppCompatActivity {

    TextView odometer, shortestPath, consumption;
    Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);

        odometer = (TextView) findViewById(R.id.textViewOdometerWon);
        shortestPath = (TextView) findViewById(R.id.textViewShortestWon);
        consumption = (TextView) findViewById(R.id.textViewConsumptionWon);
        playAgain = (Button) findViewById(R.id.buttonPlayAgainWon);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart();
            }
        });
        odometer.setText(getString(R.string.odometer) + " N/A for now");
        shortestPath.setText(getString(R.string.shortest) + " N/A for now");
        consumption.setText(getString(R.string.Consumption) + " N/A for now");
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