package com.ynz.fin.average233day.helpers.calculators;

import java.util.List;
import java.util.SortedMap;

public interface AverageCalculator<T, R> {
    /**
     * remember: should check the null values for each quote.
     *
     * @param quotes List<HistoricalQuote>
     * @return List<Double> average values
     */

    SortedMap<T, R> compute(List<T> quotes) throws Exception;
}
