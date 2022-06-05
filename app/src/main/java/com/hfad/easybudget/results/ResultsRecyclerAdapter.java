package com.hfad.easybudget.results;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.easybudget.R;
import com.hfad.easybudget.account.Account;
import com.hfad.easybudget.util.CashFlow;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ResultsRecyclerAdapter extends RecyclerView.Adapter<ResultsRecyclerAdapter.ViewHolder> {
    private final Account account;

    public ResultsRecyclerAdapter(final Account account) {
        this.account = account;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale local = Locale.UK;
        NumberFormat fmtCurrency = NumberFormat.getCurrencyInstance(local);
        NumberFormat fmtPercent = NumberFormat.getPercentInstance();

        final List<Map<String, Double>> summaryLog = account.getSummaryLog();

        holder.initBalance.setText("Initial Balance " + fmtCurrency.format(summaryLog.get(position).get("Previous balance")));
        holder.income.setText("Income " + fmtCurrency.format(summaryLog.get(position).get("Income")));
        holder.expense.setText("Expense " + fmtCurrency.format(summaryLog.get(position).get("Expense")));
        holder.interest.setText("Interest " + fmtPercent.format(summaryLog.get(position).get("Interest")));
        holder.tax.setText("Income Tax " + fmtPercent.format(summaryLog.get(position).get("Income tax")));
        holder.interestEarned.setText("Interest Earned " + fmtCurrency.format(summaryLog.get(position).get("Interest earned")));
        holder.taxDeducted.setText("Tax Deducted " + fmtCurrency.format(summaryLog.get(position).get("Tax deducted")));
        holder.profit.setText("Profit " + fmtCurrency.format(summaryLog.get(position).get("Profit")));
        holder.balance.setText("Balance " + fmtCurrency.format(summaryLog.get(position).get("Balance")));
    }

    @Override
    public int getItemCount() {
        return account.getCycles();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView initBalance;
        TextView income;
        TextView expense;
        TextView interest;
        TextView tax;
        TextView interestEarned;
        TextView taxDeducted;
        TextView profit;
        TextView balance;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            initBalance = itemView.findViewById(R.id.results_init_balance);
            income = itemView.findViewById(R.id.results_income);
            expense = itemView.findViewById(R.id.results_expense);
            interest = itemView.findViewById(R.id.results_interest);
            tax = itemView.findViewById(R.id.results_tax);
            interestEarned = itemView.findViewById(R.id.results_interest_earned);
            taxDeducted = itemView.findViewById(R.id.results_tax_deducted);
            profit = itemView.findViewById(R.id.results_profit);
            balance = itemView.findViewById(R.id.results_balance);
        }
    }
}
