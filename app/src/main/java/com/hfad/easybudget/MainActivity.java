package com.hfad.easybudget;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    private boolean hasEntries = false;
    private EntriesFragment entriesFragment;

    private ActivityResultLauncher<Intent> getResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent mainIntent = result.getData();

                    if (mainIntent != null) {
                        String strDate =  (String)mainIntent.getExtras().get(MainActivity.EXTRA_DATE);
                        List<Integer> numericInput = (List)mainIntent.getExtras().get(MainActivity.EXTRA_NUMERIC_INPUT);
                        int initialBalance = (int)mainIntent.getExtras().get(MainActivity.EXTRA_INITIAL_BALANCE);

                        if (entriesFragment == null) {
                            entriesFragment = new EntriesFragment(strDate, numericInput);
                            hasEntries = true;
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_fragment_container, entriesFragment, null)
                                    .setReorderingAllowed(true)
                                    .commit();
                        } else {
                            entriesFragment.updateEntries(strDate, numericInput);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);

        if (savedInstanceState != null) {
            hasEntries = savedInstanceState.getBoolean("has_entries");
        }

        if (hasEntries == true) {
            fragmentTransaction.add(R.id.main_fragment_container, EntriesFragment.class, null);
        } else {
            fragmentTransaction.add(R.id.main_fragment_container, MainFragment.class, null);
        }
        fragmentTransaction.commit();

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (entriesFragment != null) {
            outState.putBoolean("has_entries", hasEntries);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}