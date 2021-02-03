package com.ynz.fin.average233day.helpers.factors;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component("dayAverageTrendIndicator")
@NoArgsConstructor
public class DayAverageTrendIndicator implements DataSetTrend<Double> {

    private Map<LinerRegressionDataFactors.Factor, Double> factors;

    @Override
    public void setFactorMap(Map<LinerRegressionDataFactors.Factor, Double> factorMap) {
        factors = factorMap;
    }

    @Override
    public boolean isIncremental() {
        if (factors == null) throw new IllegalStateException(" factorMap is not given");

        double rSquare = factors.get(LinerRegressionDataFactors.Factor.R_SQUARE);
        if (rSquare < 0.2) return false;

        double slop = factors.get(LinerRegressionDataFactors.Factor.SLOP);
        return slop > 1;
    }
}
