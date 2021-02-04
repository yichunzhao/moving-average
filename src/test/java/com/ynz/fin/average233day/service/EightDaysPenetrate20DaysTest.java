package com.ynz.fin.average233day.service;

import com.ynz.fin.average233day.helpers.fileloader.IncrementalNasdaqStocks;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class EightDaysPenetrate20DaysTest {
    @Autowired
    private EightDaysPenetrate20Days eightDaysPenetrate20Days;

    @Autowired
    private IncrementalNasdaqStocks incrementalNasdaqStocks;

    @Test
    void determinePenetratePatternByOneTicker() {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 4);

        List<String> pList = eightDaysPenetrate20Days.findAllPenetratePatternsByTickers(Arrays.asList("ABUS"), to);
        assertThat(pList, hasSize(0));
    }

    @Test
    @Disabled
    void determinePenetratePatternBy233DayIncrementalTickers() {
        List<String> tickers = incrementalNasdaqStocks.load();
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 4);

        List<String> pList = eightDaysPenetrate20Days.findAllPenetratePatternsByTickers(tickers, to);
        log.info("penetrate");
        log.info(pList.toString());

        assertThat(pList.size(), is(0));
    }

    @Test
    void determinePatternByTickerAAWW() {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 4);

        List<String> pList = eightDaysPenetrate20Days.findAllPenetratePatternsByTickers(Arrays.asList("AAWW"), to);
        assertThat(pList, hasSize(0));
    }

    @Test
    @DisplayName("ticker EVGBC throw Exception")
    void solvingNumberFormatException_DueToTickerEVGBC() {
        Calendar to = Calendar.getInstance();
        to.set(2021, 1, 4);

        List<String> pList = eightDaysPenetrate20Days.findAllPenetratePatternsByTickers(Arrays.asList("EVGBC"), to);
        assertThat(pList, hasSize(0));
    }

}