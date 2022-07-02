package com.hfad.easybudget.input;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hfad.easybudget.home.MainActivity;
import com.hfad.easybudget.R;
import com.hfad.easybudget.util.PercentageTextWatcher;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        final ViewGroup rootView = (ViewGroup) findViewById(R.id.input_constraint_layout);

        EditText interestText = ((TextInputLayout) findViewById(R.id.input_edit_interest)).getEditText();
        EditText taxText = ((TextInputLayout) findViewById(R.id.input_edit_tax)).getEditText();

        interestText.addTextChangedListener(new PercentageTextWatcher(interestText));
        taxText.addTextChangedListener(new PercentageTextWatcher(taxText));

        Button inputButton = findViewById(R.id.input_button);
        inputButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DatePicker datePicker = findViewById(R.id.date_picker_input);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = format.format(calendar.getTime());

        final HashMap<String, Double> numericInput = new HashMap<>();
        final ViewGroup rootView = (ViewGroup) findViewById(R.id.input_constraint_layout);

        for (int i = 0; i < rootView.getChildCount(); i++) {
            final View inputTextView = rootView.getChildAt(i);

            if (!(inputTextView instanceof TextInputLayout))
                continue;

            final String inputText = ((TextInputLayout) inputTextView).getEditText().getText().toString();
            final String value = inputText.replaceAll("[£,]", "");

            switch (inputTextView.getId()) {
                case R.id.input_edit_income:
                    numericInput.put("Income", inputText.isEmpty() ? 0.00 : Double.parseDouble(value));
                    break;

                case R.id.input_edit_expense:
                    numericInput.put("Expense", inputText.isEmpty() ? 0.00 : Double.parseDouble(value));
                    break;

                case R.id.input_edit_interest:
                    numericInput.put("Interest", inputText.isEmpty() ? 0.00 : Double.parseDouble(inputText)/100);
                    break;

                case R.id.input_edit_tax:
                    numericInput.put("Tax", inputText.isEmpty() ? 0.00 : Double.parseDouble(inputText)/100);
                    break;
            }
        }

        Intent mainIntent = new Intent();
        mainIntent.putExtra(MainActivity.EXTRA_DATE, strDate);
        mainIntent.putExtra(MainActivity.EXTRA_NUMERIC_INPUT, (Serializable) numericInput);

        setResult(Activity.RESULT_OK, mainIntent);
        finish();
    }
}
