package com.hfad.easybudget.util;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum Cycle implements Serializable {
    DAY, WEEK, MONTH;

    private Map<String, Double> averages = new HashMap<>();
    private Map<String, Double> catAverages = new HashMap<>();

    public void calcAverages(Map<String, Double> totals, double cycles, Map<String, Double> catTotals) {
        if (cycles == 0) {
            return;
        }
        
        averages = totals.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()/cycles));
        catAverages = catTotals.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()/cycles));
    }

    public Map<String, Double> getAverages() {
        return averages;
    }

    public Map<String, Double> getCatAverages() {
        return catAverages;
    }

}