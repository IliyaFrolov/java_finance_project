package com.hfad.easybudget.results;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.easybudget.R;
import com.hfad.easybudget.account.Account;

public class CycleSummaryFragment extends Fragment {
    private final Account account;

    public CycleSummaryFragment(final Account account) {
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater
                .inflate(R.layout.fragment_cycle_summary, container, false);
        recyclerView.setAdapter(new ResultsRecyclerAdapter(account));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;
    }
}