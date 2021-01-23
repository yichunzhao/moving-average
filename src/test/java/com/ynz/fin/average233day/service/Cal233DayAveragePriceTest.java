package com.ynz.fin.average233day.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Cal233DayAveragePriceTest {

    private Cal233DayAveragePrice averagePrice = new Cal233DayAveragePrice();

    @Test
    void getStockOneYearBefore() throws IOException {
        List<HistoricalQuote> historicalQuotes = averagePrice.getStockOneYearBefore("tdy", LocalDate.of(2020, 12, 31));
        assertThat(historicalQuotes.size(), is(greaterThan(254)));
    }

    @Test
    void calc20DaysPre233DaysPriceAverage() throws IOException {
        List<HistoricalQuote> historicalQuotes =
                averagePrice.getStockOneYearBefore("tdy", LocalDate.of(2020, 12, 31));
        Map<HistoricalQuote, Double> averagePrices = averagePrice.cal20Days233Average(historicalQuotes);
        assertThat(averagePrices.size(), is(20));
    }

    @Test
    void determine20Days233AverageIncrementalOrNot() throws IOException {
        assertTrue(averagePrice.isIncremental("tdy", LocalDate.of(2020, 12, 31)));
    }

    @Test
    void checkNullValues() {
        assertTrue(averagePrice.isIncremental("acamu", LocalDate.of(2021, 1, 22)));
    }

    @Test
    @Disabled
    void determineDataSetFirstDay() {
        LocalDate firstDay = averagePrice.determineFirstDayOfDataSet(LocalDate.of(2020, 12, 31));
        assertThat(firstDay, is(LocalDate.of(2019, 12, 31)));
    }

}