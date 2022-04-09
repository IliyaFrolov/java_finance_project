package com.hfad.easybudget;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hfad.easybudget.account.Main;
import com.hfad.easybudget.input.InputActivity;
import com.hfad.easybudget.util.CashFlow;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_NUMERIC_INPUT = "numericInput";
    public static final String EXTRA_INITIAL_BALANCE = "initialBalance";
    private static ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter();
    
    private ActivityResultLauncher<Intent> getResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent mainIntent = result.getData();

                    if (mainIntent != null) {
                        System.out.println("MainActivity expandableListAdapter being created!");
                        final String strDate =  (String) mainIntent.getExtras().get(MainActivity.EXTRA_DATE);
                        final List<Integer> numericInput = (List) mainIntent.getExtras().get(MainActivity.EXTRA_NUMERIC_INPUT);
                        final int initialBalance = (int) mainIntent.getExtras().get(MainActivity.EXTRA_INITIAL_BALANCE);
                        final List<String> childrenTitles = Arrays.asList("Income", "Expense", "Interest", "Tax");
                        MainActivity.expandableListAdapter.initExpandableListAdapter(strDate, numericInput, childrenTitles, this);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment_container, EntriesFragment.class, null)
                                .setReorderingAllowed(true)
                                .commit();
                        getSupportFragmentManager().executePendingTransactions();
                        ExpandableListView expandableListView = findViewById(R.id.entry_expandable_view);
                        System.out.println(expandableListView == null);
                        expandableListView.setAdapter(MainActivity.expandableListAdapter);

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MainActivity onCreate() called!");
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

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity onStart() called!");
    }
}