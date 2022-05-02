package com.hfad.easybudget.results;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hfad.easybudget.account.Account;

public class ResultsPagerAdapter extends FragmentStateAdapter {
    private final Account account;

    public ResultsPagerAdapter(@NonNull FragmentActivity fragmentActivity, final Account account) {
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
                return new AveragesSummaryFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
