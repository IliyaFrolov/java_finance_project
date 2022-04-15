package com.hfad.easybudget.results;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hfad.easybudget.R;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ViewPager2 resultsPager = findViewById(R.id.pager_results);
        resultsPager.setAdapter(new ResultsPagerAdapter(this));

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

class ResultsPagerAdapter extends FragmentStateAdapter {

    public ResultsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TotalSummaryFragment();

            case 1:
                return new CycleSummaryFragment();

            case 2:
                return new AveragesSummaryFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}