package com.hfad.easybudget;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hfad.easybudget.input.InputActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> getResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, EntriesFragment.class, null)
                            .setReorderingAllowed(true)
                            .commit();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_fragment_container, MainFragment.class, null)
                .commit();

        Button buttonCalculate = findViewById(R.id.button_calculate);
        buttonCalculate.setOnClickListener((View view) -> {
            Intent resultsIntent = new Intent(this, ResultsActivity.class);
            startActivity(resultsIntent);
        });

        FloatingActionButton fab = findViewById(R.id.fab_main);
        fab.setOnClickListener((View view) -> {
            Intent inputIntent = new Intent(this, InputActivity.class);
            getResult.launch(inputIntent);
        });
    }
}