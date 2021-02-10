package com.ynz.fin.average233day.helpers.calculators;

import com.ynz.fin.average233day.utils.NumberFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Calculating 233-day average,for a consecutive day-span as given a dataSet.
 */
@Component("average233days")
@Slf4j
public class Average233Days implements AverageCalculator<HistoricalQuote, Double> {
    private final int movingDays = 233;

    //cal. 20-day MA
    private final int daySpan;
    private final int minDataSetSize;

    public Average233Days(@Qualifier("20Days") int daySpan) {
        this.daySpan = daySpan;
        minDataSetSize = daySpan + (movingDays - 1);
    }

    @Override
    public SortedMap<HistoricalQuote, Double> compute(List<HistoricalQuote> quotes) throws Exception {
        if (quotes.size() < minDataSetSize) throw new Exception("input data size is not enough");

        SortedMap<HistoricalQuote, Double> historicalQuoteDoubleMap = new TreeMap<>(Comparator.comparing(HistoricalQuote::getDate));

        //reverse the list; now the latest are stored at the most left side.
        Collections.reverse(quotes);

        //sublist(fromIndex,toIndex); toIndex:excluded
        for (int i = 0; i < daySpan; i++) {
            Double average = quotes.subList(i, i + movingDays).stream()
                    .mapToDouble(q -> q.getClose().doubleValue()).average()
                    .orElseThrow(() -> new Exception("double average is not present"));
            historicalQuoteDoubleMap.put(quotes.get(i), NumberFormatter.of(average).round());
        }
        return historicalQuoteDoubleMap;
    }
}
