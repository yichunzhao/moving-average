package com.ynz.fin.average233day.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AllNasStocksHandlerTest {
    @Autowired
    private AllNasStocksHandler stocksHandler;

    @Test
    @Disabled
    void testRunFirst10Stocks() {
        stocksHandler.calAllNasStock20Days233DaysIncremental();
    }

}

