package com.ynz.fin.average233day.helpers.factors;


import java.util.Map;

/**
 * give a list of a T type data, we may determine its trend.
 * @param <T>
 */
public interface DataSetTrend<T extends Number> {
    boolean isIncremental();

    void setFactorMap(Map<LinerRegressionDataFactors.Factor, T> factorMap);
}
