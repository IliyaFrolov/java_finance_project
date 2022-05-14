package com.hfad.easybudget.results;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hfad.easybudget.util.CashFlow;

import java.util.ArrayList;

public class ResultsPagerAdapter extends FragmentStateAdapter {
    private ArrayList<CashFlow> cashFlowList;
    private double initBalance;

    public ResultsPagerAdapter(FragmentActivity fragmentActivity,
                               ArrayList<CashFlow> cashFlowList,
                               double initBalance) {
        super(fragmentActivity);
        this.cashFlowList = cashFlowList;
        this.initBalance = initBalance;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TotalSummaryFragment();

            case 1:
                return new CycleSummaryFragment(cashFlowList, initBalance);

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
