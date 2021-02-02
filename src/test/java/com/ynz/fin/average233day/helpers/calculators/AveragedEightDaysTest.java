package com.ynz.fin.average233day.helpers.calculators;

import com.ynz.fin.average233day.helpers.accessquotes.StockLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class AveragedEightDaysTest {

    @Autowired
    private StockLoader<HistoricalQuote> stockLoader;

    @Autowired
    @Qualifier("average8days")
    private AverageCalculator<HistoricalQuote, Double> averageCalculator;

    private List<HistoricalQuote> quoteList;

    @BeforeEach
    void setUp() {
        Calendar to = new GregorianCalendar(2021, 0, 29);
        quoteList = stockLoader.loadPastMonthQuotes("tsla", to, 2);
        quoteList.parallelStream().filter(quote -> quote.getClose() != null).collect(toList());
    }

    @Test
    void testDataIsReady() {
        assertAll(
                () -> assertThat(stockLoader, is(notNullValue())),
                () -> assertThat(quoteList.size(), is(greaterThan(30))),
                () -> assertThat(quoteList.size(), is(lessThan(60)))
        );
    }

    @Test
    void testCal10Day8DayAverage() throws Exception {
        Map<HistoricalQuote, Double> result = averageCalculator.compute(quoteList);
        assertAll(
                () -> assertThat(result, is(notNullValue())),
                () -> assertThat(result.size(), is(10))
        );
    }

    @Test
    void whenInputDataIsNotEnough_ThenItThrowsException() {
        List<HistoricalQuote> fakedList = quoteList.subList(0, 10);

        Throwable exception = assertThrows(Exception.class, () -> averageCalculator.compute(fakedList));
        assertThat(exception.getMessage(), is("input data size is not enough"));
    }

}