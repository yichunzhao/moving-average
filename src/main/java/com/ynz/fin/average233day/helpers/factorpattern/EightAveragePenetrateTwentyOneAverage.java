package com.ynz.fin.average233day.helpers.factorpattern;

import com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors;

import java.util.Map;


public class EightAveragePenetrateTwentyOneAverage {
    private Map<LinerRegressionDataFactors.Factor, Double> line8Average;
    private Map<LinerRegressionDataFactors.Factor, Double> line21Average;

    private EightAveragePenetrateTwentyOneAverage(Map<LinerRegressionDataFactors.Factor, Double> line8Average, Map<LinerRegressionDataFactors.Factor, Double> line21Average) {
        this.line8Average = line8Average;
        this.line21Average = line21Average;
    }

    public static EightAveragePenetrateTwentyOneAverage of(Map<LinerRegressionDataFactors.Factor, Double> line8Average, Map<LinerRegressionDataFactors.Factor, Double> line21Average) {
        return new EightAveragePenetrateTwentyOneAverage(line8Average, line21Average);
    }

    public boolean penetrate() {
        double a1 = line8Average.get(LinerRegressionDataFactors.Factor.SLOP);
        double a2 = line21Average.get(LinerRegressionDataFactors.Factor.SLOP);

        double b1 = line8Average.get(LinerRegressionDataFactors.Factor.INTERCEPT);
        double b2 = line21Average.get(LinerRegressionDataFactors.Factor.INTERCEPT);

        return a1 > a2 && b1 < b2;
    }

}
