package com.ynz.fin.average233day.service;


import com.ynz.fin.average233day.domain.nasdaq.NasdaqStock;
import com.ynz.fin.average233day.helpers.accessquotes.StockHistoricalQuoteLoader;
import com.ynz.fin.average233day.helpers.fileloader.LoadNasdaqStocks;
import com.ynz.fin.average233day.utils.filestore.ResultFileStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class TickerFilter {
    @Value("${file.storage.filename2}")
    private String filename;

    @Value("${file.storage.resultDir}")
    private String folderName;

    private final LoadNasdaqStocks loadNasdaqStocks;
    private final StockHistoricalQuoteLoader quoteLoader;

    List<String> filterAllByPrice(Double priceLimit, Calendar from, Calendar to) {
        List<String> list = new ArrayList<>();

        //get all tickers
        List<NasdaqStock> stocks = loadNasdaqStocks.load();
        List<String> tickers = stocks.stream().map(NasdaqStock::getSymbol).collect(toList());

        ResultFileStorage rs = ResultFileStorage.create(folderName, filename);
        rs.saveLine("all Nas tickers whose price less than " + priceLimit);

        //load quotes
        for (String ticker : tickers) {
            try {
                List<HistoricalQuote> quotes = quoteLoader.loadHistoricalQuotesDaily(ticker, from, to);
                if (quotes == null || quotes.isEmpty()) continue;

                if (quotes.get(0).getClose() == null) continue;

                //write the ticker to a file
                if (quotes.get(0).getClose().doubleValue() < priceLimit) {
                    list.add(ticker);
                    rs.saveLine(ticker);
                }
            } catch (IOException e) {
                log.warn("loading historical quotes from Yahoo AIP: ", e);
                continue;
            }
        }

        return list;
    }

}
