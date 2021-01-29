package com.ynz.fin.average233day.helpers;

import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Component("yahooHistoryQuoteLoader")
public class StockHistoricalQuoteLoader implements StockLoader<HistoricalQuote> {

    @Override
    public List<HistoricalQuote> loadHistoricalQuotesDaily(String ticker, Calendar from, Calendar to) throws IOException {
        List<HistoricalQuote> historicalQuotes;

        Stock found = YahooFinance.get(ticker, from, to, Interval.DAILY);
        if (found == null) return null;
        historicalQuotes = found.getHistory(from, to, Interval.DAILY);

        return historicalQuotes;
    }
}
