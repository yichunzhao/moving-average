package com.ynz.fin.average233day.helpers.factorpattern;

import com.ynz.fin.average233day.helpers.accessquotes.StockHistoricalQuoteLoader;
import com.ynz.fin.average233day.helpers.calculators.AverageCalculatorContext;
import com.ynz.fin.average233day.helpers.calculators.Average21Days;
import com.ynz.fin.average233day.helpers.calculators.Average8Days;
import com.ynz.fin.average233day.helpers.factorpattern.PenetrateIdentifier.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class PenetrateIdentifierTest {

    @Autowired
    private StockHistoricalQuoteLoader quoteLoader;

    @Autowired
    private AverageCalculatorContext calculatorContext;

    @Test
    void givenALLK_PenetrateReturnTrue() throws Exception {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<HistoricalQuote> quotes = quoteLoader.loadPastMonthQuotes("ALLK", to, 2);
        assertNotNull(quotes);

        //filter out null value
        List<HistoricalQuote> validatedQuotes = quotes.stream().filter(q -> q.getClose() != null).collect(toList());

        //cal. 10-day-8DayAverage
        Map<HistoricalQuote, Double> eightDayAverage = calculatorContext.execute(validatedQuotes, Average8Days.class);
        assertNotNull(eightDayAverage);

        //cal. 10-day-21DayAverage
        Map<HistoricalQuote, Double> twentyOneDayAverage = calculatorContext.execute(validatedQuotes, Average21Days.class);
        assertNotNull(twentyOneDayAverage);

        Map<ResultType, String> results = PenetrateIdentifier.of(eightDayAverage, twentyOneDayAverage).penetrate();
        assertTrue(Boolean.parseBoolean(results.get(ResultType.IS_PENETRATED)));
    }

    @Test
    void givenANGI_PenetrateReturnTrue() throws Exception {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<HistoricalQuote> quotes = quoteLoader.loadPastMonthQuotes("ANGI", to, 2);
        assertNotNull(quotes);

        //filter out null value
        List<HistoricalQuote> validatedQuotes = quotes.stream().filter(q -> q.getClose() != null).collect(toList());

        //cal. 10-day-8DayAverage
        Map<HistoricalQuote, Double> eightDayAverage = calculatorContext.execute(validatedQuotes, Average8Days.class);
        assertNotNull(eightDayAverage);

        //cal. 10-day-21DayAverage
        Map<HistoricalQuote, Double> twentyOneDayAverage = calculatorContext.execute(validatedQuotes, Average21Days.class);
        assertNotNull(twentyOneDayAverage);

        Map<ResultType, String> results = PenetrateIdentifier.of(eightDayAverage, twentyOneDayAverage).penetrate();
        assertTrue(Boolean.parseBoolean(results.get(ResultType.IS_PENETRATED)));
    }

    @Test
    void givenSAVA_PenetrateReturnFalse() throws Exception {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<HistoricalQuote> quotes = quoteLoader.loadPastMonthQuotes("SAVA", to, 2);
        assertNotNull(quotes);

        //filter out null value
        List<HistoricalQuote> validatedQuotes = quotes.stream().filter(q -> q.getClose() != null).collect(toList());

        //cal. 10-day-8DayAverage
        Map<HistoricalQuote, Double> eightDayAverage = calculatorContext.execute(validatedQuotes, Average8Days.class);
        assertNotNull(eightDayAverage);

        //cal. 10-day-21DayAverage
        Map<HistoricalQuote, Double> twentyOneDayAverage = calculatorContext.execute(validatedQuotes, Average21Days.class);
        assertNotNull(twentyOneDayAverage);

        Map<ResultType, String> results = PenetrateIdentifier.of(eightDayAverage, twentyOneDayAverage).penetrate();
        assertFalse(Boolean.parseBoolean(results.get(ResultType.IS_PENETRATED)));
    }

    @Test
    void givenTSLA_PenetrateReturnFalse() throws Exception {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<HistoricalQuote> quotes = quoteLoader.loadPastMonthQuotes("TSLA", to, 2);
        assertNotNull(quotes);

        //filter out null value
        List<HistoricalQuote> validatedQuotes = quotes.stream().filter(q -> q.getClose() != null).collect(toList());

        //cal. 10-day-8DayAverage
        Map<HistoricalQuote, Double> eightDayAverage = calculatorContext.execute(validatedQuotes, Average8Days.class);
        assertNotNull(eightDayAverage);

        //cal. 10-day-21DayAverage
        Map<HistoricalQuote, Double> twentyOneDayAverage = calculatorContext.execute(validatedQuotes, Average21Days.class);
        assertNotNull(twentyOneDayAverage);

        Map<ResultType, String> results = PenetrateIdentifier.of(eightDayAverage, twentyOneDayAverage).penetrate();
        assertFalse(Boolean.parseBoolean(results.get(ResultType.IS_PENETRATED)));
    }

}