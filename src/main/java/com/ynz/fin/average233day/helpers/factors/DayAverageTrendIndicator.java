package com.ynz.fin.average233day.helpers.factors;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("dayAverageTrendIndicator")
@Scope("prototype")
@NoArgsConstructor
public class DayAverageTrendIndicator implements DataSetTrend<Double> {

    private Map<LinerRegressionDataFactors.Factor, Double> factors;

    @Override
    public DayAverageTrendIndicator setFactorMap(Map<LinerRegressionDataFactors.Factor, Double> factorMap) {
        factors = factorMap;
        return this;
    }

    @Override
    public boolean isIncremental() {
        if (factors == null) throw new IllegalStateException(" factorMap is not given");

        double rs = factors.get(LinerRegressionDataFactors.Factor.R_SQUARE);
        if (rs < 0.3) return false;

        double slop = factors.get(LinerRegressionDataFactors.Factor.SLOP);

        return slop > 0;
    }
}
