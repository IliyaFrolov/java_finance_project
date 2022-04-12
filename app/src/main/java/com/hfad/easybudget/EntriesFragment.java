package com.hfad.easybudget;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class EntriesFragment extends Fragment {
    private EntriesAdapter entriesAdapter = new EntriesAdapter();
    private String strDate;
    private List<Integer> numericInput;

    public EntriesFragment() {
        super();
    }

    public EntriesFragment(String strDate, List<Integer> numericInput) {
        super();
        this.strDate = strDate;
        this.numericInput = numericInput;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            entriesAdapter = savedInstanceState.getParcelable("entries_adapter");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater
                .inflate(R.layout.fragment_entries, container, false);
        entriesAdapter.addEntry(strDate, numericInput);
        recyclerView.setAdapter(entriesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public void updateEntries(String strDate, List<Integer> numericInput) {
        entriesAdapter.addEntry(strDate, numericInput);
        entriesAdapter.notifyItemInserted(entriesAdapter.getEntryPos());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("entries_adapter", entriesAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}