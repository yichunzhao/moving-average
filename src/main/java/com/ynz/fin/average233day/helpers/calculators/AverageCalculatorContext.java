package com.ynz.fin.average233day.helpers.calculators;


import lombok.extern.slf4j.Slf4j;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.List;
import java.util.Map;


@Slf4j
public class AverageCalculatorContext {

    private AverageCalculator averageCalculator;

    public AverageCalculatorContext(AverageCalculator averageCalculator) {
        this.averageCalculator = averageCalculator;
    }

    public Map<HistoricalQuote, Double> execute(List<HistoricalQuote> quotes) {

        Map<HistoricalQuote, Double> historicalQuoteDoubleMap;

        try {
            historicalQuoteDoubleMap = averageCalculator.compute(quotes);
        } catch (Exception e) {
            log.error("average calculation context: ", e);
            return null;
        }

        return historicalQuoteDoubleMap;
    }


}
