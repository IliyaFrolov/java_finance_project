package com.hfad.easybudget.results;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hfad.easybudget.R;
import com.hfad.easybudget.account.Account;
import com.hfad.easybudget.util.CashFlow;
import com.hfad.easybudget.util.Cycle;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    public final static String EXTRA_CASHFLOW = "cashflow";
    public final static String EXTRA_INIT_BALANCE = "init_balance";
    private ArrayList<CashFlow> cashFlowList;
    private Double initBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent resultsIntent = getIntent();

        if (resultsIntent != null) {
            cashFlowList = resultsIntent.getParcelableArrayListExtra(ResultsActivity.EXTRA_CASHFLOW);
            initBalance = (Double) resultsIntent.getExtras().get(EXTRA_INIT_BALANCE);
        }
        ResultsPagerAdapter pagerAdapter = new ResultsPagerAdapter(this, cashFlowList, initBalance);

        ViewPager2 resultsPager = findViewById(R.id.pager_results);
        resultsPager.setAdapter(pagerAdapter);

        setSupportActionBar(findViewById(R.id.toolbar_results));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TabLayout tabResults = findViewById(R.id.tab_results);
        new TabLayoutMediator(tabResults, resultsPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Total Summary");
                    break;
                case 1:
                    tab.setText("Cycle Summary");
                    break;
                case 2:
                    tab.setText("Averages Summary");
                    break;
            }
        }).attach();
    }
}

