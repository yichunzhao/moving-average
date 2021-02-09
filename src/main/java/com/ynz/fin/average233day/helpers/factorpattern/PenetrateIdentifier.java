package com.ynz.fin.average233day.helpers.factorpattern;

import lombok.RequiredArgsConstructor;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor(staticName = "of")
public class PenetrateIdentifier {
    public enum ResultType {
        IS_PENETRATED, FEATURE
    }

    private static final String pattern = "[0]+[1]+";
    private final Map<HistoricalQuote, Double> eightDayAvg;
    private final Map<HistoricalQuote, Double> twentyOneDayAvg;

    public Map<ResultType, String> penetrate() throws Exception {
        if (eightDayAvg == null || twentyOneDayAvg == null || eightDayAvg.isEmpty() || twentyOneDayAvg.isEmpty())
            throw new Exception("DataSet is NULL or EMPTY");

        if (eightDayAvg.size() != twentyOneDayAvg.size())
            throw new Exception("Both datasets have different sizes");

        Set<HistoricalQuote> eightKeySet = eightDayAvg.keySet();
        Set<HistoricalQuote> twentyOneKeySet = twentyOneDayAvg.keySet();

        if (!eightKeySet.containsAll(twentyOneKeySet) || !twentyOneKeySet.containsAll(eightKeySet))
            throw new Exception("Both time series is not identical");

        Map<ResultType, String> results = new HashMap<>();

        Set<HistoricalQuote> historicalQuotes = eightKeySet;

        //price pattern
        StringBuilder feature = new StringBuilder();

        for (HistoricalQuote h : historicalQuotes) {
            Double eDayAvg = eightDayAvg.get(h);
            Double tDayAvg = twentyOneDayAvg.get(h);
            feature.append(eDayAvg > tDayAvg ? 1 : 0);
        }

        Boolean matched = feature.toString().matches(pattern);
        results.put(ResultType.FEATURE, feature.toString());
        results.put(ResultType.IS_PENETRATED, matched.toString());

        return results;
    }

}
