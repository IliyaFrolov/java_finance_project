package com.hfad.easybudget.input;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.hfad.easybudget.EntriesFragment;
import com.hfad.easybudget.MainActivity;
import com.hfad.easybudget.R;
import com.hfad.easybudget.util.CashFlow;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = format.format(calendar.getTime());

        String initText = ((EditText) findViewById(R.id.input_edit_init)).getText().toString();
        int initialBalance = initText.isEmpty() ? 0 : Integer.parseInt(initText);

        String incomeText = ((EditText) findViewById(R.id.input_edit_income)).getText().toString();
        int income = incomeText.isEmpty() ? 0 : Integer.parseInt(incomeText);

        String expenseText = ((EditText) findViewById(R.id.input_edit_expense)).getText().toString();
        int expense = expenseText.isEmpty() ? 0 : Integer.parseInt(expenseText);

        String interestText = ((EditText) findViewById(R.id.input_edit_interest)).getText().toString();
        int interest = interestText.isEmpty() ? 0 : Integer.parseInt(interestText);

        String taxText = ((EditText) findViewById(R.id.input_edit_tax)).getText().toString();
        int tax = taxText.isEmpty() ? 0 : Integer.parseInt(taxText);

        List<Integer> numericInput = Arrays.asList(income, expense, interest, tax);

        Intent mainIntent = new Intent();
        mainIntent.putExtra(MainActivity.EXTRA_DATE, strDate);
        mainIntent.putExtra(MainActivity.EXTRA_NUMERIC_INPUT, (Serializable) numericInput);
        mainIntent.putExtra(MainActivity.EXTRA_INITIAL_BALANCE, initialBalance);

        setResult(Activity.RESULT_OK, mainIntent);
        finish();
    }
}