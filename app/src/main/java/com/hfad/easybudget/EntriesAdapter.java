package com.hfad.easybudget;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.ViewHolder> {
    private List<String> dates = new ArrayList<>();
    private HashMap<String, List<String>> groups = new HashMap<>();
    private List<String> items = Arrays.asList("Income", "Expense", "Interest", "Tax");

    public List<String> getdates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public HashMap<String, List<String>> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, List<String>> groups) {
        this.groups = groups;
    }

    public void addEntry(String strDate, List<Integer> numericInput) {
        dates.add(strDate);
        groups.put(strDate, IntStream.range(0, items.size())
        .mapToObj(i -> new String(items.get(i)+numericInput.get(i)))
        .collect(Collectors.toList()));
    }

    public int getEntryPos() {
        return dates.size()-1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_entry, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.entryText.setText(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView entryText;
        CardView entryCardView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            entryCardView = itemView;
            entryText = itemView.findViewById(R.id.entry_text);
        }
    }
}
