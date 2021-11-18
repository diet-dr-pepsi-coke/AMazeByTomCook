package edu.wm.cs.cs301.tomcook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class AMazeActivity extends AppCompatActivity {

    Spinner generationDropdown;
    String algorithm;
    Button exploreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_maze_activity);

        generationDropdown = (Spinner) findViewById(R.id.spinnerAlgorithm);
        exploreButton = (Button) findViewById(R.id.buttonExplore);

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
                Toast.makeText(adapterView.getContext(), "Selected: " + algorithm, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGeneratingActivity();
            }
        });
    }

        public void openGeneratingActivity() {
            Intent intent = new Intent(this, GeneratingActivity.class);
            startActivity(intent);
        }

}