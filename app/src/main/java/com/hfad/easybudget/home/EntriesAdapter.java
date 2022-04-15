package com.hfad.easybudget.home;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.easybudget.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.ViewHolder> implements Parcelable {
    private List<String> dates = new ArrayList<>();
    private HashMap<String, List<String>> groups = new HashMap<>();
    private List<String> items = Arrays.asList("Income ", "Expense ", "Interest ", "Tax ");
    private boolean isExpanded = false;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public EntriesAdapter createFromParcel(Parcel in) {
            return new EntriesAdapter(in);
        }

        public EntriesAdapter[] newArray(int size) {
            return new EntriesAdapter[size];
        }
    };

    public EntriesAdapter() {
        super();
    }

    private EntriesAdapter(Parcel in) {
        super();
        in.readList(dates, EntriesAdapter.class.getClassLoader());
        in.readMap(groups, EntriesAdapter.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeList(dates);
        parcel.writeMap(groups);
    }

    public void addEntry(String strDate, List<Integer> numericInput) {
        dates.add(strDate);
        groups.put(strDate, IntStream.range(0, items.size())
        .mapToObj(i -> items.get(i)+numericInput.get(i))
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
        LinearLayout expandableLayout = holder.entryCardView.findViewById(R.id.entry_expandable_layout);

        ((TextView)expandableLayout.findViewById(R.id.entry_income_text))
                .setText(groups.get(dates.get(position)).get(0));
        ((TextView)expandableLayout.findViewById(R.id.entry_expense_text))
                .setText(groups.get(dates.get(position)).get(1));
        ((TextView)expandableLayout.findViewById(R.id.entry_interest_text))
                .setText(groups.get(dates.get(position)).get(2));
        ((TextView)expandableLayout.findViewById(R.id.entry_tax_text))
                .setText(groups.get(dates.get(position)).get(3));

        holder.entryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    isExpanded = false;
                    expandableLayout.setVisibility(View.VISIBLE);
                } else {
                    isExpanded = true;
                    expandableLayout.setVisibility(view.GONE);
                }
            }
        });

        ((TextView)holder.entryCardView.findViewById(R.id.entry_header_text))
                .setText(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView entryCardView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            entryCardView = itemView;
        }
    }
}
