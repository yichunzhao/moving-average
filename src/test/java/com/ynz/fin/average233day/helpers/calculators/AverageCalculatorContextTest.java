package com.ynz.fin.average233day.helpers.calculators;

import com.ynz.fin.average233day.helpers.accessquotes.StockLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest
class AverageCalculatorContextTest {

    @Autowired
    private StockLoader<HistoricalQuote> stockLoader;

    @Autowired
    private AverageCalculatorContext calculatorContext;

    private List<HistoricalQuote> quoteList;

    @BeforeEach
    void setUp() {
        Calendar to = new GregorianCalendar(2021, 0, 29);
        quoteList = stockLoader.loadPastMonthQuotes("tdy", to, 13);
        quoteList.parallelStream().filter(quote -> quote.getClose() != null).collect(toList());
    }

    @Test
    void testAverage8daysCalculator() {
        Map<HistoricalQuote, Double> result = calculatorContext.execute(quoteList, Average8Days.class);
        assertAll(
                () -> assertThat(result, is(notNullValue())),
                () -> assertThat(result.size(), is(10))
        );
    }

    @Test
    void testAverage21daysCalculator() {
        Map<HistoricalQuote, Double> result = calculatorContext.execute(quoteList, Average21Days.class);
        assertAll(
                () -> assertThat(result, is(notNullValue())),
                () -> assertThat(result.size(), is(10))
        );
    }

    @Test
    void testAverage233daysCalculator() {
        Map<HistoricalQuote, Double> result = calculatorContext.execute(quoteList, Average233Days.class);
        assertAll(
                () -> assertThat(result, is(notNullValue())),
                () -> assertThat(result.size(), is(20))
        );
    }

}