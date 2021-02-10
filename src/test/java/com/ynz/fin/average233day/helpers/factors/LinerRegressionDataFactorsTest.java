package com.ynz.fin.average233day.helpers.factors;

import com.ynz.fin.average233day.helpers.accessquotes.StockLoader;
import com.ynz.fin.average233day.helpers.calculators.Average233Days;
import com.ynz.fin.average233day.helpers.calculators.AverageCalculatorContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors.Factor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class LinerRegressionDataFactorsTest {

    @Autowired
    private LinerRegressionDataFactors factors;

    @Autowired
    @Qualifier("yahooHistoryQuoteLoader")
    private StockLoader<HistoricalQuote> stockLoader;

    @Autowired
    private AverageCalculatorContext calculatorContext;

    @Test
    void solverIsInjected() {
        assertAll(
                () -> assertNotNull(factors),
                () -> assertNotNull(stockLoader),
                () -> assertNotNull(calculatorContext)
        );
    }

    @Test
    void testCalFactors() {
        log.info("test cal. factors");
        factors.setDataList(Arrays.asList(1.34, 4.56, 7.88, 9.60));
        assertNotNull(factors.getDataList());

        Map<Factor, Double> factorDoubleMap = factors.calDataSetFactors();
        assertNotNull(factorDoubleMap);

        log.info(factorDoubleMap.toString());

        assertAll(
                () -> assertNotNull(factorDoubleMap.get(Factor.INTERCEPT)),
                () -> assertNotNull(factorDoubleMap.get(Factor.SLOP)),
                () -> assertNotNull(factorDoubleMap.get(Factor.R_SQUARE)),
                () -> assertNotNull(factorDoubleMap.get(Factor.R))
        );
    }

    @Test
    void ifDataListIsNotGiven_ThenThrowIllegalStateException() {
        log.info("test data list is not set, throw illegalStateException ");
        assertThrows(IllegalStateException.class, () -> factors.calDataSetFactors());
    }

    @Test
    void ifDataListContainNullValue_ThenThrowIllegalArgumentException() {
        log.info("if test data contains null, throw illegalArgumentException ");
        factors.setDataList(Arrays.asList(1.34, 4.56, 7.88, 9.60, null));
        assertThrows(IllegalArgumentException.class, () -> factors.calDataSetFactors());
    }

    @Test
    void givenTickerOPINI233DayMA_ItReturnsNegativeSlop() {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 9);

        List<HistoricalQuote> quotes = stockLoader.loadPastMonthQuotes("OPINI", to, 13);
        Map<HistoricalQuote, Double> historicalQuoteDoubleMap = calculatorContext.execute(quotes, Average233Days.class);
        assertNotNull(historicalQuoteDoubleMap);

        List<Double> averages = new ArrayList<>(historicalQuoteDoubleMap.values());

        factors.setDataList(averages);
        Map<Factor, Double> factorDoubleMap = factors.calDataSetFactors();
        assertAll(
                () -> assertNotNull(factorDoubleMap),
                () -> assertThat(factorDoubleMap.get(Factor.SLOP), is(lessThanOrEqualTo(0D)))
        );
    }

    @Test
    void givenTickerTDY233DayMA_ItReturnsPositiveSlop() {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 9);

        List<HistoricalQuote> quotes = stockLoader.loadPastMonthQuotes("TDY", to, 13);
        Map<HistoricalQuote, Double> historicalQuoteDoubleMap = calculatorContext.execute(quotes, Average233Days.class);
        assertNotNull(historicalQuoteDoubleMap);

        List<Double> averages = new ArrayList<>(historicalQuoteDoubleMap.values());

        factors.setDataList(averages);
        Map<Factor, Double> factorDoubleMap = factors.calDataSetFactors();
        assertAll(
                () -> assertNotNull(factorDoubleMap),
                () -> assertThat(factorDoubleMap.get(Factor.SLOP), is(greaterThan(0D)))
        );
    }

    @Test
    void givenTickerTSLA233DayMA_ItReturnsPositiveSlop() {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 9);

        List<HistoricalQuote> quotes = stockLoader.loadPastMonthQuotes("TSLA", to, 13);
        Map<HistoricalQuote, Double> historicalQuoteDoubleMap = calculatorContext.execute(quotes, Average233Days.class);
        assertNotNull(historicalQuoteDoubleMap);

        List<Double> averages = new ArrayList<>(historicalQuoteDoubleMap.values());

        factors.setDataList(averages);
        Map<Factor, Double> factorDoubleMap = factors.calDataSetFactors();
        assertAll(
                () -> assertNotNull(factorDoubleMap),
                () -> assertThat(factorDoubleMap.get(Factor.SLOP), is(greaterThan(0D)))
        );
    }

}