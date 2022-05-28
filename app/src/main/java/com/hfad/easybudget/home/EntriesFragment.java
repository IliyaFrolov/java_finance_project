package com.hfad.easybudget.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.easybudget.R;

import java.util.HashMap;
import java.util.List;

public class EntriesFragment extends Fragment {
    private EntriesAdapter entriesAdapter = new EntriesAdapter();

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
        recyclerView.setAdapter(entriesAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public void updateEntries(String strDate, HashMap<String, Double> numericInput) {
        entriesAdapter.addEntry(strDate, numericInput);
        entriesAdapter.notifyItemInserted(entriesAdapter.getEntryPos());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("entries_adapter", entriesAdapter);
    }
}