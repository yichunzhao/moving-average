package com.ynz.fin.average233day.helpers;

import com.ynz.fin.average233day.domain.nasdaq.NasdaqStock;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertAll;

class LoadNasdaqStocksTest {

    private final LoadNasdaqStocks loadNasdaqStocks = new LoadNasdaqStocks();

    @Test
    void testLoadStocksIntoList() {
        List<NasdaqStock> stocks = loadNasdaqStocks.doAction();

        assertAll(
                () -> assertThat(stocks.size(), is(greaterThan(3000))),
                () -> assertThat(stocks.size(), is(lessThan(4000))),
                () -> assertThat(stocks.get(0).getSymbol(), is("AACG")),
                () -> assertThat(stocks.get(1).getSymbol(), is("AACQ")),
                () -> assertThat(stocks.get(stocks.size() - 1).getSymbol(), is("ZYXI"))
        );
    }

    @Test
    void printIntoExcelFile() {
        List<NasdaqStock> stocks = loadNasdaqStocks.doAction();
        File nasdaqStocksPlainTextFile = null;
        try {
            Files.deleteIfExists(Paths.get("nasdaqStocks.txt"));

            nasdaqStocksPlainTextFile = new File("nasdaqStocks.txt");

            FileWriter writer = new FileWriter(nasdaqStocksPlainTextFile);
            for (NasdaqStock stock : stocks) {
                writer.write(stock.toPlainText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(nasdaqStocksPlainTextFile.length(), is(greaterThan(0L)));
    }

    @Test
    void groupStocksBySymbol() {
        Map<Character, TreeSet<NasdaqStock>> groupStocks = loadNasdaqStocks.groupBySymbol();
        assertThat(groupStocks.size(), is(greaterThan(20)));
        assertThat(groupStocks.size(), is(lessThanOrEqualTo(26)));
    }

}