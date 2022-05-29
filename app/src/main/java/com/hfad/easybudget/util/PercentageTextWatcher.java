package com.hfad.easybudget.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class PercentageTextWatcher implements TextWatcher {
    public static final NumberFormat numberFormat = NumberFormat.getIntegerInstance();
    private final WeakReference<EditText> editTextWeakReference;

    public PercentageTextWatcher(EditText editText) {
        editTextWeakReference = new WeakReference<>(editText);
        numberFormat.setMaximumIntegerDigits(2);
        numberFormat.setRoundingMode(RoundingMode.FLOOR);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        EditText editText = editTextWeakReference.get();
        if (editText == null || editText.getText().toString().equals("")) {
            return;
        }
        editText.removeTextChangedListener(this);

        String value = editText.getText().toString();
        String formatted = numberFormat.format(Double.parseDouble(value));

        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }
}
