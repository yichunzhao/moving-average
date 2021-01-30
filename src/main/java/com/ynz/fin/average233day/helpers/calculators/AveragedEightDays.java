package com.ynz.fin.average233day.helpers.calculators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Calculating 8-day average,for a consecutive days given a dataSet.
 */
@Component("average8days")
@Slf4j
public class AveragedEightDays implements AverageCalculator<HistoricalQuote, Double> {
    //days to cal. 8-day average
    private int daySpan;

    private int minDataSetSize;

    public AveragedEightDays(@Qualifier("10Days") int daySpan) {
        this.daySpan = daySpan;
        minDataSetSize = daySpan + 8;
    }

    @Override
    public Map<HistoricalQuote, Double> compute(List<HistoricalQuote> quotes) throws Exception {
        if (quotes.size() < minDataSetSize) throw new Exception("input data size is not enough");

        Map<HistoricalQuote, Double> historicalQuoteDoubleMap = new LinkedHashMap<>();

        //reverse the list; now the latest are stored at the most left side.
        Collections.reverse(quotes);

        for (int i = 0; i < daySpan; i++) {
            Double average = quotes.subList(i + 1, i + 9).stream()
                    .mapToDouble(q -> q.getClose().doubleValue()).average()
                    .orElseThrow(() -> new Exception("double average is not present"));
            historicalQuoteDoubleMap.put(quotes.get(i), average);
        }
        return historicalQuoteDoubleMap;
    }
}
