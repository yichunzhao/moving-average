package com.ynz.fin.average233day.service;

import com.ynz.fin.average233day.domain.nasdaq.NasdaqStock;
import com.ynz.fin.average233day.helpers.LoadNasdaqStocks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllNasStocksHandler {
    private final Cal233DayAveragePrice cal233DayAveragePrice;
    private final LoadNasdaqStocks loadNasdaqStocks;

    public void calAllNasStock20Days233DaysIncremental() {
        try {
            //file to store result
            File file = new File("AllNasStock20Day233Incremental.txt");
            File fileNot = new File("NasStock20Day233NotIncremental.txt");
            FileWriter fileWriter = new FileWriter(file);
            FileWriter fileWriterNot = new FileWriter(fileNot);

            //ref. date
            LocalDate refDate = LocalDate.of(2021, 01, 22);
            fileWriter.append("ref. date:" + refDate.toString()).append("\n");
            fileWriterNot.append("ref. date:" + refDate.toString()).append("\n");

            //all stocks
            List<NasdaqStock> stocks = loadNasdaqStocks.doAction().stream().collect(toList());

            //cal
            for (NasdaqStock stock : stocks) {
                String symbol = stock.getSymbol();

                boolean isIncremental = cal233DayAveragePrice.isIncremental(symbol, refDate);
                if (isIncremental) {
                    fileWriter.append(Integer.toString(stocks.indexOf(stock))).append(" " + symbol).append("\n");
                } else {
                    fileWriterNot.append(Integer.toString(stocks.indexOf(stock))).append(symbol).append("\n");
                }
            }

            fileWriter.flush();
            fileWriterNot.flush();
            fileWriter.close();
            fileWriterNot.close();

        } catch (IOException e) {
            log.error("AllNasStocksHandler...", e);
        }

    }

}
