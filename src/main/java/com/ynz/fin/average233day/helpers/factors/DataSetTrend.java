package com.ynz.fin.average233day.helpers.factors;


/**
 * give a list of a T type data, we may determine its trend.
 * @param <T>
 */
public interface DataSetTrend<T extends Number> {
    boolean isIncremental();
}
