package com.hfad.easybudget.home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
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
            Fragment mainFrag = getSupportFragmentManager().findFragmentByTag("main_fragment");

            if (mainFrag == null) {
                Intent resultsIntent = new Intent(this, ResultsActivity.class);
                TextInputLayout initBalanceText = findViewById(R.id.main_editText_init_balance);
                String initBalanceString = initBalanceText.getEditText().getText().toString();
                Double initBalance = initBalanceString.isEmpty() ? 0.00 : Double.parseDouble(initBalanceString);
                resultsIntent.putParcelableArrayListExtra(ResultsActivity.EXTRA_CASHFLOW, cashFlowList);
                resultsIntent.putExtra(ResultsActivity.EXTRA_INIT_BALANCE, initBalance);
                startActivity(resultsIntent);
            } else {
                Toast.makeText(this, "Add entries first!", Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_main);
        fab.setOnClickListener((View view) -> {
            Intent inputIntent = new Intent(this, InputActivity.class);
            getResult.launch(inputIntent);
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(R.id.main_fragment_container, MainFragment.class, null, "main_fragment").commit();
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
}