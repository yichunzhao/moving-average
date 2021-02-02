package com.ynz.fin.average233day.helpers.factors;


import java.util.Map;

public class DayAverageTrendIndicator implements DataSetTrend<Double> {
    private DataSetFactors<LinerRegressionDataSetFactor.Factor, Double> setFactors;

    public DayAverageTrendIndicator(DataSetFactors<LinerRegressionDataSetFactor.Factor, Double> setFactors) {
        this.setFactors = setFactors;
    }

    @Override
    public boolean isIncremental() {
        Map<LinerRegressionDataSetFactor.Factor, Double> factors = setFactors.calDataSetFactors();

        double rSquare = factors.get(LinerRegressionDataSetFactor.Factor.R_SQUARE);
        if (rSquare < 0.2) return false;

        double slop = factors.get(LinerRegressionDataSetFactor.Factor.SLOP);
        return slop > 1 ? true : false;
    }
}
