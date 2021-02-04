package com.ynz.fin.average233day.service;

import com.ynz.fin.average233day.domain.nasdaq.NasdaqStock;
import com.ynz.fin.average233day.helpers.fileloader.LoadNasdaqStocks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllNasStocksHandler {
    private final Cal233DayAveragePrice cal233DayAveragePrice;
    private final LoadNasdaqStocks loadNasdaqStocks;
    private StringBuilder sbIncremental = new StringBuilder();
    private StringBuilder sbNotIncremental = new StringBuilder();

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
            //List<NasdaqStock> stocks = loadNasdaqStocks.doAction().stream().skip(1418).collect(toList());
            List<NasdaqStock> sts = loadNasdaqStocks.load();

            Optional<NasdaqStock> target = sts.stream().filter(st -> st.getSymbol().equals("HTHT")).findAny();
            NasdaqStock lastEnding = target.orElseThrow(() -> new IllegalStateException("the specific stock is not found in the stocks"));
            int index = sts.indexOf(lastEnding);

            List<NasdaqStock> stocks = loadNasdaqStocks.load().stream().skip(index + 1).collect(toList());

            //List<NasdaqStock> stocks = loadNasdaqStocks.doAction().stream().filter(st -> st.getSymbol().equals("TSLA")).collect(toList());
            //cal
            for (NasdaqStock stock : stocks) {
                String symbol = stock.getSymbol();

                boolean isIncremental = cal233DayAveragePrice.isIncremental(symbol, refDate);
                if (isIncremental) {
                    sbIncremental.append(Integer.toString(stocks.indexOf(stock))).append(" " + symbol).append("\n");
                } else {
                    sbNotIncremental.append(Integer.toString(stocks.indexOf(stock))).append(symbol).append("\n");
                }
            }

            fileWriter.write(sbIncremental.toString());
            fileWriterNot.write(sbNotIncremental.toString());

            fileWriter.flush();
            fileWriterNot.flush();

            fileWriter.close();
            fileWriterNot.close();
        } catch (IOException e) {
            log.error("AllNasStocksHandler...", e);
        }

    }

}
