package edu.wm.cs.cs301.tomcook.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

import edu.wm.cs.cs301.tomcook.R;

public class AMazeActivity extends AppCompatActivity {

    Spinner generationDropdown;
    Switch switchRoom;
    boolean rooms;
    Slider difficulty;
    int skillLevel;
    String algorithm;
    Button exploreButton;
    Button revisitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_maze_activity);

        generationDropdown = (Spinner) findViewById(R.id.spinnerAlgorithm);
        exploreButton = (Button) findViewById(R.id.buttonExplore);
        revisitButton = (Button) findViewById(R.id.buttonRevisit);
        switchRoom = (Switch) findViewById(R.id.switchRoom);
        difficulty = (Slider) findViewById(R.id.sliderDifficulty);

        //Spinner//
        List<String> generationAlgorithms = new ArrayList<>();
        generationAlgorithms.add(getString(R.string.DFS));
        generationAlgorithms.add(getString(R.string.Prim));
        generationAlgorithms.add(getString(R.string.Boruvka));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, generationAlgorithms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generationDropdown.setAdapter(adapter);
        generationDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                algorithm = adapter.getItem(i);
                Toast.makeText(adapterView.getContext(), "Selected: " + algorithm, Toast.LENGTH_SHORT).show();
                Log.v(String.valueOf(this), algorithm + " selected for maze generation");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Buttons//
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGeneratingActivity();
            }
        });

        revisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGeneratingActivity();
            }
        });

        switchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchRoom.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Rooms: On", Toast.LENGTH_SHORT).show(); }
                else { Toast.makeText(getApplicationContext(), "Rooms: Off", Toast.LENGTH_SHORT).show(); }
                rooms = switchRoom.isChecked();
                Log.v(String.valueOf(this), "rooms included: " + rooms);
            }
        });


        // Slider //
        difficulty.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                skillLevel = (int) value;
                Toast.makeText(getApplicationContext(), "Skill level set to " + skillLevel, Toast.LENGTH_SHORT).show();
                Log.v(String.valueOf(this), "skill level set to " + skillLevel);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void openGeneratingActivity() {
            Intent intent = new Intent(this, GeneratingActivity.class);
            startActivity(intent);
        }

}