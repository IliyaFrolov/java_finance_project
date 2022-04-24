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
    private List<String> items = Arrays.asList("Income", "Expense", "Interest", "Tax");
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

    public void addEntry(String strDate, HashMap<String, Double> numericInput) {
        dates.add(strDate);
        System.out.println(items.get(0));
        System.out.println(numericInput.get(items.get(0)));
        groups.put(strDate, IntStream.range(0, items.size())
        .mapToObj(i -> items.get(i)+" "+numericInput.get(items.get(i)))
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
        holder.headerText.setText(dates.get(position));
        holder.incomeText.setText(groups.get(dates.get(position)).get(0));
        holder.expenseText.setText(groups.get(dates.get(position)).get(1));
        holder.interestText.setText(groups.get(dates.get(position)).get(2));
        holder.taxText.setText(groups.get(dates.get(position)).get(3));
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.headerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isExpanded = !isExpanded;
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LinearLayout expandableLayout;
        TextView headerText, incomeText, expenseText, interestText, taxText;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
            expandableLayout = itemView.findViewById(R.id.entry_expandable_layout);
            headerText = itemView.findViewById(R.id.entry_header_text);
            incomeText = itemView.findViewById(R.id.entry_income_text);
            expenseText = itemView.findViewById(R.id.entry_expense_text);
            interestText = itemView.findViewById(R.id.entry_interest_text);
            taxText = itemView.findViewById(R.id.entry_tax_text);
        }
    }
}
