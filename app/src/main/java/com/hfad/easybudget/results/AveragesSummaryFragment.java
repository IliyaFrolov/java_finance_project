package com.hfad.easybudget.results;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.hfad.easybudget.R;
import com.hfad.easybudget.account.Account;
import com.hfad.easybudget.util.Cycle;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AveragesSummaryFragment extends Fragment {
    private final Account account;

    public AveragesSummaryFragment(final Account account) {
        super();
        this.account = account;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_averages_summary, container, false);
        Locale local = Locale.UK;
        NumberFormat fmtCurrency = NumberFormat.getCurrencyInstance(local);

        TextView averageIncomeText = rootView.findViewById(R.id.average_income_textView);
        TextView averageIntEarnedText = rootView.findViewById(R.id.average_int_earned_textView);
        TextView averageExpenseText = rootView.findViewById(R.id.average_expense_textView);
        TextView averageTaxDeductedText = rootView.findViewById(R.id.average_tax_deducted_textView);

        MaterialButtonToggleGroup toggleButtonGroup = rootView.findViewById(R.id.toggle_button_averages_results);
        toggleButtonGroup.addOnButtonCheckedListener((button, checkedId, isChecked) -> {
            final int checkedButtonId = button.getCheckedButtonId();
            final MaterialButton checkedButton = button.findViewById(checkedButtonId);

            if (checkedButton == null) {
                return;
            }
            final Cycle cycleType = account.getCycleType();
            Map<String, Double> averages;

            if (checkedButton.getText().equals("Daily")) {
                averages = cycleType.DAY.getAverages();
            } else if (checkedButton.getText().equals("Weekly")) {
                averages = cycleType.WEEK.getAverages();
            } else if (checkedButton.getText().equals("Monthly")) {
                averages = cycleType.MONTH.getAverages();
            } else {
                averages = null;
            }
            averageIncomeText.setText(fmtCurrency.format(averages.get("Income")));
            averageIntEarnedText.setText(fmtCurrency.format(averages.get("Interest earned")));
            averageExpenseText.setText(fmtCurrency.format(averages.get("Expense")));
            averageTaxDeductedText.setText(fmtCurrency.format(averages.get("Tax deducted")));
        });
        return rootView;
    }
}