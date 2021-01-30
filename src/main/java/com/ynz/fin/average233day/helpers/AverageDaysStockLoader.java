package com.ynz.fin.average233day.helpers;

import java.util.Calendar;
import java.util.List;

public interface AverageDaysStockLoader<T> {
    /**
     * load 8 days from a reference date(included).
     *
     * @param ticker String stock ticker
     * @param to     Calender ending point
     * @return List<HistoricalQuote>
     */
    List<T> load8DaysQuotes(String ticker, Calendar to);

    /**
     * load 21 days from a reference date(included).
     *
     * @param ticker String stock ticker
     * @param to     Calender ending point
     * @return List<HistoricalQuote>
     */
    List<T> load21DaysQuotes(String ticker, Calendar to);

}
