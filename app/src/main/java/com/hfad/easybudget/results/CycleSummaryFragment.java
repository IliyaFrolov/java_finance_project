package com.hfad.easybudget.results;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.easybudget.R;
import com.hfad.easybudget.account.Account;
import com.hfad.easybudget.util.CashFlow;
import com.hfad.easybudget.util.Cycle;

import java.util.ArrayList;

public class CycleSummaryFragment extends Fragment {
    private ArrayList<CashFlow> cashFlowList;
    private double initBalance;

    public CycleSummaryFragment() {
        super();
    }

    public CycleSummaryFragment(final ArrayList<CashFlow> cashFlowList, final double initBalance) {
        super();
        this.cashFlowList = cashFlowList;
        this.initBalance = initBalance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            cashFlowList = savedInstanceState.getParcelableArrayList(ResultsActivity.EXTRA_CASHFLOW);
            initBalance = savedInstanceState.getDouble(ResultsActivity.EXTRA_INIT_BALANCE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Account account = Account.createAccount(initBalance, Cycle.MONTH, cashFlowList);

        RecyclerView recyclerView = (RecyclerView) inflater
                .inflate(R.layout.fragment_cycle_summary, container, false);
        recyclerView.setAdapter(new ResultsRecyclerAdapter(account));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ResultsActivity.EXTRA_CASHFLOW, cashFlowList);
        outState.putDouble(ResultsActivity.EXTRA_INIT_BALANCE, initBalance);
    }
}