package com.ynz.fin.average233day.service;

import com.ynz.fin.average233day.helpers.accessquotes.StockHistoricalQuoteLoader;
import com.ynz.fin.average233day.helpers.factorpattern.PenetrateIdentifier.ResultType;
import com.ynz.fin.average233day.helpers.factorpattern.SoundPenetrateIdentifier;
import com.ynz.fin.average233day.helpers.fileloader.LoadNasdaqStocks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllNasStocksHandler {

    private final SoundPenetrateIdentifier identifier;
    private final StockHistoricalQuoteLoader quoteLoader;


    /**
     * find through tickers that ma8 penetrate ma21
     */
    public Map<String, Map<ResultType, String>> findManyTickers(List<String> tickers, Calendar to) {
        Map<String, Map<ResultType, String>> tickerResultMap = new LinkedHashMap<>();

        for (String ticker : tickers) {

            List<HistoricalQuote> quotes = quoteLoader.loadPastMonthQuotes(ticker, to, 13);
            if (quotes == null || quotes.isEmpty()) continue;

            try {
                tickerResultMap.put(ticker, identifier.handleSingle(quotes));
            } catch (Exception e) {
                log.warn("find ma8 penetrating ma21 patterns through a list of tickers ", e);
                continue;
            }
        }

        return tickerResultMap;
    }

}
