package com.ynz.fin.average233day.service;

import com.ynz.fin.average233day.helpers.fileloader.IncrementalNasdaqStocks;
import com.ynz.fin.average233day.helpers.fileloader.ResultFolderTickerLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class Updated8AvgPenetrate21AvgTest {

    @Autowired
    private Updated8AvgPenetrate21Avg updated8AvgPenetrate21Avg;

    @Autowired
    private IncrementalNasdaqStocks incrementalNasdaqStocks;

    @Test
    void givenALLK_PenetrateIsTrue() {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<String> pList = updated8AvgPenetrate21Avg.findAllPenetratePatternsByTickers(Arrays.asList("ALLK"), to);
        assertThat(pList, hasSize(1));
    }

    /**
     * weak penetrate
     */
    @Test
    void givenNVAX_PenetrateIsTrue() {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<String> pList = updated8AvgPenetrate21Avg.findAllPenetratePatternsByTickers(Arrays.asList("NVAX"), to);
        assertThat(pList, hasSize(0));
    }

    @Test
    void givenPList_ExploringPatterns() {
        List<String> plist = Arrays.asList("ALLK", "ANGI", "AVXL", "CSGP", "GNPX", "HHR", "HYRE", "LACQ", "LANC", "NVAX", "OKTA", "SAVA", "THCB", "TWNK", "WDAY");

        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<String> results = updated8AvgPenetrate21Avg.findAllPenetratePatternsByTickers(plist, to);
        assertThat(results.size(), is(9));
    }

    @Test
    @Disabled
    void determinePenetratePatternBy233DayIncrementalTickers() {
        List<String> tickers = incrementalNasdaqStocks.load();
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 4);

        //tickers.stream().skip(1000).collect(toList())
        List<String> pList = updated8AvgPenetrate21Avg.findAllPenetratePatternsByTickers(tickers, to);
        log.info("penetrate");
        log.info(pList.toString());

        assertThat(pList.size(), is(0));
    }

    @Test
    @Disabled
    void givenBoth10Day8DayAnd21DayAverageBothPositive_FindOUt8Penetrate21() {
        List<String> tickers = ResultFolderTickerLoader.of("10Day8DayAvrAnd21DayAverBothPositive.txt").load();
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 6);

        List<String> pList = updated8AvgPenetrate21Avg.findAllPenetratePatternsByTickers(tickers, to);
        log.info("penetrate");
        log.info(pList.toString());

        assertThat(pList.size(), is(greaterThan(1)));
    }

}