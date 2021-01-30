package com.ynz.fin.average233day.helpers.accessquotes;

import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public interface StockLoader<T> {
    /**
     * load quotes from a date to a date.
     *
     * @param ticker String stock ticker
     * @param from   Calender starting point
     * @param to     Calender ending point
     * @return List<HistoricalQuote>
     * @throws IOException YahooFinance API exception
     */
    List<T> loadHistoricalQuotesDaily(String ticker, Calendar from, Calendar to) throws IOException;

    /**
     * ref. to current date to, load previous n-month quotes. if the quotes is not existed, it return null.
     *
     * @param ticker          String
     * @param to              Calendar
     * @param pastMonthNumber int
     * @return List<HistoricalQuote>
     */
    List<HistoricalQuote> loadPastMonthQuotes(String ticker, Calendar to, int pastMonthNumber);

}
