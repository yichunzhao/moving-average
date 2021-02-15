package com.ynz.fin.average233day.helpers.factorpattern;

import com.ynz.fin.average233day.helpers.accessquotes.StockHistoricalQuoteLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class SoundPenetrateIdentifierTest {
    @Autowired
    private StockHistoricalQuoteLoader quoteLoader;

    @Autowired
    private SoundPenetrateIdentifier identifier;

    @Test
    void givenALLK_RefToFeb6_ThenPenetrateIsFalse() throws Exception {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<HistoricalQuote> quotes = quoteLoader.loadPastMonthQuotes("ALLK", to, 13);
        Map<PenetrateIdentifier.ResultType, String> rs = identifier.handleSingle(quotes);
        assertThat(rs.get(PenetrateIdentifier.ResultType.IS_PENETRATED), is(Boolean.FALSE.toString()));
    }

    @Test
    void givenNVAX_RefToFeb6_ThenPenetrateIsFalse() throws Exception {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<HistoricalQuote> quotes = quoteLoader.loadPastMonthQuotes("NVAX", to, 13);
        Map<PenetrateIdentifier.ResultType, String> rs = identifier.handleSingle(quotes);
        assertThat(rs.get(PenetrateIdentifier.ResultType.IS_PENETRATED), is(Boolean.FALSE.toString()));
    }

    @Test
    void givenNullQuotes_ThenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> identifier.handleSingle(null));
    }

    @Test
    void givenEmptyQuotes_ThenThrowException() {
        Throwable exception = assertThrows(Exception.class, () -> identifier.handleSingle(new ArrayList()));
        assertThat(exception.getMessage(), is("input data size is not enough"));
    }

}