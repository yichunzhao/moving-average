package com.ynz.fin.average233day.helpers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertAll;


class IncrementalNasdaqStocksTest {

    private FileLoader<String> fileLoader = new IncrementalNasdaqStocks();

    @Test
    void loadAllIncrementalTickersFromFile() {
        List<String> tickers = fileLoader.doAction();

        assertAll(
                () -> assertThat(tickers.size(), is(greaterThan(1900))),
                () -> assertThat(tickers.size(), is(lessThan(2000)))
        );
    }

}