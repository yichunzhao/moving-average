package com.ynz.fin.average233day.helpers.factors;


import java.util.Map;

public class DayAverageTrendIndicator implements DataSetTrend<Double> {
    private DataSetFactors<LinerRegression.Factor, Double> setFactors;

    public DayAverageTrendIndicator(DataSetFactors<LinerRegression.Factor, Double> setFactors) {
        this.setFactors = setFactors;
    }

    @Override
    public boolean isIncremental() {
        Map<LinerRegression.Factor, Double> factors = setFactors.calDataSetFactors();

        double rSquare = factors.get(LinerRegression.Factor.R_SQUARE);
        if (rSquare < 0.2) return false;

        double slop = factors.get(LinerRegression.Factor.SLOP);
        return slop > 1;
    }
}
