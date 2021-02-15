package com.ynz.fin.average233day.service;

import com.ynz.fin.average233day.domain.nasdaq.NasdaqStock;
import com.ynz.fin.average233day.helpers.factorpattern.PenetrateIdentifier.ResultType;
import com.ynz.fin.average233day.helpers.fileloader.LoadNasdaqStocks;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Slf4j
class AllNasStocksHandlerTest {

    @Autowired
    private LoadNasdaqStocks loadNasdaqStocks;

    @Autowired
    private AllNasStocksHandler stocksHandler;

    @Test
    @Disabled
    void testRunFirst10Stocks() {
        List<String> tickers = loadNasdaqStocks.load().stream().map(NasdaqStock::getSymbol).collect(toList());
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);
        stocksHandler.findManyTickers(tickers, to);
    }

    @Test
    void givenPList_ExploringPatterns() {
        List<String> plist = Arrays.asList("ALLK", "ANGI", "AVXL", "CSGP", "GNPX", "HHR", "HYRE", "LACQ", "LANC", "NVAX", "OKTA", "SAVA", "THCB", "TWNK", "WDAY");

        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        Map<String, Map<ResultType, String>> results = stocksHandler.findManyTickers(plist, to);
        log.info("test results table: ", results.toString());

        assertThat(results.size(), is(greaterThan(0)));
    }

    @Test
    void givenANGI_ExploringPatterns() {
        List<String> plist = Arrays.asList("ANGI");

        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        Map<String, Map<ResultType, String>> results = stocksHandler.findManyTickers(plist, to);

        assertAll(
                () -> assertThat(results.get("ANGI").size(), is(2)),
                () -> assertThat(results.get("ANGI").get(ResultType.IS_PENETRATED), is(Boolean.TRUE.toString()))
        );
    }

}

