package com.ynz.fin.average233day.helpers.calculators;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Component("average21days")
public class AverageTwentyOneDays implements AverageCalculator<HistoricalQuote, Double> {

    private int daySpan;

    private int minDataSetSize;

    public AverageTwentyOneDays(@Qualifier("10Days") int daySpan) {
        this.daySpan = daySpan;
        minDataSetSize = daySpan + 20;
    }

    @Override
    public Map<HistoricalQuote, Double> compute(List<HistoricalQuote> quotes) throws Exception {
        if (quotes.size() < minDataSetSize) throw new Exception("input data size is not enough");

        Map<HistoricalQuote, Double> historicalQuoteDoubleMap = new LinkedHashMap<>();

        //reverse the list; now the latest are stored at the most left side.
        Collections.reverse(quotes);

        for (int i = 0; i < daySpan; i++) {
            Double average = quotes.subList(i , i + 21).stream()
                    .mapToDouble(q -> q.getClose().doubleValue()).average()
                    .orElseThrow(() -> new Exception("double average is not present"));
            historicalQuoteDoubleMap.put(quotes.get(i), average);
        }
        return historicalQuoteDoubleMap;
    }
}
