package com.hfad.easybudget.input;

import androidx.appcompat.app.AppCompatActivity;

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

        setResult(Activity.RESULT_OK);
        finish();
    }
}