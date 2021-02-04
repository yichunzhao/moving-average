package com.ynz.fin.average233day.utils;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor(staticName = "of")
public class NumberFormatter {
    private final double number;

    public double round() {
        if (Double.isNaN(number)) return Double.NaN;
        return BigDecimal.valueOf(this.number).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
