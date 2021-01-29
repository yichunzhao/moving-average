package com.ynz.fin.average233day.helpers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest
@Slf4j
class StockHistoricalQuoteLoaderTest {
    @Autowired
    private StockHistoricalQuoteLoader stockLoader;

    @Test
    @DisplayName("ask yahoo for historical quotes")
    void getHistoricalQuotesByTicker() throws Exception {
        Calendar to = new GregorianCalendar(2021, 0, 29);
        Calendar from = new GregorianCalendar(2021, 0, 29);
        from.add(Calendar.MONTH, -2);
        log.info("Query history quotes from time: " + from.getTime() + " to time: " + to.getTime());

        List<HistoricalQuote> quotes = stockLoader.loadHistoricalQuotesDaily("tdy", from, to);
        assertThat(quotes.size(), is(greaterThan(30)));
    }

    @Test
    @DisplayName("Ticker BMCH returns null historical quotes ")
    void getHistoricalQuotes8days() throws Exception {
        Calendar to = new GregorianCalendar(2021, 0, 29);
        Calendar from = new GregorianCalendar(2021, 0, 29);
        from.add(Calendar.MONTH, -2);
        log.info("Query history quotes from time: " + from.getTime() + " to time: " + to.getTime());

        List<HistoricalQuote> quotes = stockLoader.loadHistoricalQuotesDaily("BMCH", from, to);
        assertThat(quotes, is(nullValue()));
    }

}