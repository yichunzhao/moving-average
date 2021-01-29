package com.ynz.fin.average233day.helpers;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public interface StockLoader<T> {

    List<T> loadHistoricalQuotesDaily(String ticker, Calendar start, Calendar end) throws Exception;
}
