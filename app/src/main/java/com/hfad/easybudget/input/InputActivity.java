package com.hfad.easybudget.input;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hfad.easybudget.home.MainActivity;
import com.hfad.easybudget.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

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
        double initialBalance = 0.00;

        for (int i = 0; i < rootView.getChildCount(); i++) {
            final View inputTextView = rootView.getChildAt(i);

            if (!(inputTextView instanceof EditText))
                continue;

            final String inputText = ((EditText) inputTextView).getText().toString();

            switch (inputTextView.getId()) {
                case R.id.input_edit_init:
                    initialBalance = inputText.isEmpty() ? 0.00 : Double.parseDouble(inputText);

                case R.id.input_edit_income:
                    numericInput.put("Income", inputText.isEmpty() ? 0.00 : Double.parseDouble(inputText));
                    break;

                case R.id.input_edit_expense:
                    numericInput.put("Expense", inputText.isEmpty() ? 0.00 : Double.parseDouble(inputText));
                    break;

                case R.id.input_edit_interest:
                    numericInput.put("Interest", inputText.isEmpty() ? 0.00 : Double.parseDouble(inputText));
                    break;

                case R.id.input_edit_tax:
                    numericInput.put("Tax", inputText.isEmpty() ? 0.00 : Double.parseDouble(inputText));
                    break;
            }
        }

        Intent mainIntent = new Intent();
        mainIntent.putExtra(MainActivity.EXTRA_DATE, strDate);
        mainIntent.putExtra(MainActivity.EXTRA_NUMERIC_INPUT, (Serializable) numericInput);
        mainIntent.putExtra(MainActivity.EXTRA_INITIAL_BALANCE, initialBalance);

        setResult(Activity.RESULT_OK, mainIntent);

        finish();
    }
}
