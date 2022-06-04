package com.hfad.easybudget.results;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.easybudget.R;
import com.hfad.easybudget.util.CashFlow;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.Locale;

public class TotalSummaryFragment extends Fragment {
    private double profit = CashFlow.getTotals().get("Profit");
    private double income = CashFlow.getTotals().get("Income");
    private double interestEarned = CashFlow.getTotals().get("Interest earned");
    private double tax = CashFlow.getTotals().get("Income tax");
    private double taxDeducted = CashFlow.getTotals().get("Tax deducted");
    private PieChart incomeChart;
    private TextView profitText, incPerceText, intEarnedPercText, incValText, intEarnedValText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_total_summary, container, false);

        incomeChart = rootView.findViewById(R.id.pie_chart_income_total);
        profitText = rootView.findViewById(R.id.profit_total);
        incPerceText = rootView.findViewById(R.id.income_percent_total);
        intEarnedPercText = rootView.findViewById(R.id.intEarned_percent_total);
        incValText = rootView.findViewById(R.id.income_value_total);
        intEarnedValText = rootView.findViewById(R.id.intEarned_value_total);
        setData();
        return rootView;
    }

    private void setData() {
        Locale local = Locale.UK;
        NumberFormat fmtCurrency = NumberFormat.getCurrencyInstance(local);
        NumberFormat fmtPercent = NumberFormat.getPercentInstance();

        final double totalIncome = interestEarned + income;

        profitText.setText(fmtCurrency.format(profit));
        incPerceText.setText(fmtPercent.format((totalIncome == 0) ? 0: income / totalIncome ));
        intEarnedPercText.setText(fmtPercent.format((totalIncome == 0) ? 0 : interestEarned / totalIncome));
        incValText.setText(fmtCurrency.format(income));
        intEarnedValText.setText(fmtCurrency.format(interestEarned));

        incomeChart.addPieSlice(new PieModel(
                "Income",
                (float) income,
                Color.parseColor("#FFA726")));

        incomeChart.addPieSlice(new PieModel(
                "Interest Earned",
                (float) interestEarned,
                Color.parseColor("#EF5359")));

        incomeChart.startAnimation();
    }
}