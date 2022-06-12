package com.hfad.easybudget.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ExpCategories implements Serializable {
    private Map<String, Double> catFlow = new HashMap<>();
    private static Map<String, Double> catTotals = new HashMap<>();

    public ExpCategories(double groceries, double eatingOut, double memberships, double bills, double travelling, double shopping, double other) {
        catFlow.put("Groceries", groceries);
        catFlow.put("Eating out", eatingOut);
        catFlow.put("Memberships", memberships);
        catFlow.put("Bills", bills);
        catFlow.put("Travelling",  travelling);
        catFlow.put("Shopping", shopping);
        catFlow.put("Other", other);
        catFlow.entrySet().stream().forEach(entry -> ExpCategories.catTotals.merge(entry.getKey(), entry.getValue(), Double::sum));
    }

    public double getExpense() {
        return catFlow.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public static Map<String, Double> getCatTotals() {
        return catTotals;
    }
}
