package com.hfad.easybudget.results;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hfad.easybudget.account.Account;
import com.hfad.easybudget.util.CashFlow;

import java.util.ArrayList;

public class ResultsPagerAdapter extends FragmentStateAdapter {
    private Account account;

    public ResultsPagerAdapter(FragmentActivity fragmentActivity, Account account) {
        super(fragmentActivity);
        this.account = account;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TotalSummaryFragment();

            case 1:
                return new CycleSummaryFragment(account);

            case 2:
                return new AveragesSummaryFragment(account);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
