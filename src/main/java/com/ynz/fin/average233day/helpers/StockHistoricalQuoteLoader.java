package com.ynz.fin.average233day.helpers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Component("yahooHistoryQuoteLoader")
@Slf4j
public class StockHistoricalQuoteLoader implements StockLoader<HistoricalQuote> {

    @Override
    public List<HistoricalQuote> loadHistoricalQuotesDaily(String ticker, Calendar from, Calendar to) throws IOException {
        List<HistoricalQuote> historicalQuotes;

        Stock found = YahooFinance.get(ticker, from, to, Interval.DAILY);
        if (found == null) return null;
        historicalQuotes = found.getHistory(from, to, Interval.DAILY);

        return historicalQuotes;
    }

    /**
     * ref. to current date to, load previous n-month quotes. if api throw exception, or quotes is found empty;
     * both cases it return null.
     *
     * @param ticker          String
     * @param to              Calendar
     * @param pastMonthNumber int
     * @return List<HistoricalQuote>
     */
    @Override
    public List<HistoricalQuote> loadPastMonthQuotes(String ticker, Calendar to, int pastMonthNumber) {
        List<HistoricalQuote> quotes;

        Calendar from = (Calendar) to.clone();
        from.add(Calendar.MONTH, (-1) * pastMonthNumber);

        try {
            quotes = loadHistoricalQuotesDaily(ticker, from, to);
            if (quotes.isEmpty()) return null;

        } catch (IOException e) {
            log.error("loading ticker: " + ticker + "averagedDays loading historical quotes", e);
            return null;
        }
        return quotes;
    }

}
