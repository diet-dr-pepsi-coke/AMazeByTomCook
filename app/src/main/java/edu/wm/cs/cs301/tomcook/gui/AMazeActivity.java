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
import edu.wm.cs.cs301.tomcook.generation.GlobalValues;
import edu.wm.cs.cs301.tomcook.generation.Order;

public class AMazeActivity extends AppCompatActivity {

    private Spinner generationDropdown;
    private Switch switchRoom;
    private boolean perfect = GlobalValues.perfect;
    private Slider difficulty;
    private int skillLevel = GlobalValues.skillLevel;
    private String algorithm;
    private Button exploreButton, revisitButton;
    private Order.Builder builder = GlobalValues.builder;

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
                Log.v(String.valueOf(this), algorithm + " selected for maze generation");
                switch (algorithm) {
                    case "DFS":
                        builder = Order.Builder.DFS;
                        break;
                    case "Prim":
                        builder = Order.Builder.Prim;
                        break;
                    case "Boruvka":
                        builder = Order.Builder.Boruvka;
                        break;
                }
                GlobalValues.builder = builder;
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
                perfect = switchRoom.isChecked();
                GlobalValues.perfect = perfect;
                Log.v(String.valueOf(this), "rooms included: " + perfect);
            }
        });


        // Slider //
        difficulty.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                skillLevel = (int) value;
                GlobalValues.skillLevel = skillLevel;
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