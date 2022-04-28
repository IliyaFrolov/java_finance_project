package com.hfad.easybudget.home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hfad.easybudget.R;
import com.hfad.easybudget.results.ResultsActivity;
import com.hfad.easybudget.input.InputActivity;
import com.hfad.easybudget.util.CashFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_NUMERIC_INPUT = "numericInput";
    private boolean hasEntries = false;
    private ArrayList<CashFlow> cashFlowList = new ArrayList<>();

    private ActivityResultLauncher<Intent> getResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent mainIntent = result.getData();

                    if (mainIntent != null) {
                        String strDate =  (String)mainIntent.getExtras().get(MainActivity.EXTRA_DATE);
                        HashMap<String, Double> numericInput = (HashMap)mainIntent.getExtras().get(MainActivity.EXTRA_NUMERIC_INPUT);
                        cashFlowList.add(new CashFlow(
                                numericInput.get("Income"),
                                numericInput.get("Expense"),
                                numericInput.get("Interest"),
                                numericInput.get("Tax")
                        ));

                        if (!hasEntries) {
                            hasEntries = true;
                            EntriesFragment entriesFragment = new EntriesFragment();
                            entriesFragment.updateEntries(strDate, numericInput);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_fragment_container, entriesFragment, null)
                                    .setReorderingAllowed(true)
                                    .commit();
                        } else {
                            EntriesFragment entriesFragment =
                                    (EntriesFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                            entriesFragment.updateEntries(strDate, numericInput);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(R.id.main_fragment_container, MainFragment.class, null).commit();
        } else {
            hasEntries = savedInstanceState.getBoolean("has_entries");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("has_entries", hasEntries);
        outState.putParcelableArrayList("cash_flow_list", cashFlowList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}