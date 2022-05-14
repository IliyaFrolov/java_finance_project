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
        final double totalIncome = interestEarned+income;

        profitText.setText(Double.toString(profit));
        incPerceText.setText(Double.toString(income/totalIncome));
        intEarnedPercText.setText(Double.toString(interestEarned/totalIncome));
        incValText.setText(Double.toString(income));
        intEarnedValText.setText(Double.toString(interestEarned));

        incomeChart.addPieSlice(new PieModel(
                        "Income",
                        Float.parseFloat(incValText.getText().toString()),
                        Color.parseColor("#FFA726")));

        incomeChart.addPieSlice(new PieModel(
                        "Interest Earned",
                        Float.parseFloat(intEarnedValText.getText().toString()),
                        Color.parseColor("#EF5359")));

        incomeChart.startAnimation();
    }
}