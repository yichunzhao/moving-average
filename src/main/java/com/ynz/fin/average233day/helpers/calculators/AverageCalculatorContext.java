package com.ynz.fin.average233day.helpers.calculators;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component("averageContext")
@RequiredArgsConstructor
@Slf4j
public class AverageCalculatorContext {

    private final AveragedEightDays eightDays;
    private final AverageTwentyOneDays twentyOneDays;
    private final Averaged233Days averaged233Days;

    public <T extends AverageCalculator<HistoricalQuote, Double>> Map<HistoricalQuote, Double> execute(List<HistoricalQuote> quotes, Class<T> strategy) {
        AverageCalculator<HistoricalQuote, Double> averageCalculator;

        if (strategy.equals(AveragedEightDays.class)) averageCalculator = eightDays;
        else if (strategy.equals(AverageTwentyOneDays.class)) averageCalculator = twentyOneDays;
        else if (strategy.equals(Averaged233Days.class)) averageCalculator = averaged233Days;
        else throw new IllegalArgumentException("strategy is not existed");

        List<HistoricalQuote> quotesClone = new ArrayList<>(quotes);

        Map<HistoricalQuote, Double> historicalQuoteDoubleMap;

        try {
            historicalQuoteDoubleMap = averageCalculator.compute(quotesClone);
        } catch (Exception e) {
            log.warn("average calculation context: ", e);
            return null;
        }

        return historicalQuoteDoubleMap;
    }

}
