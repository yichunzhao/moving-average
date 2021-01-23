package com.ynz.fin.average233day.service;

import com.ynz.fin.average233day.utils.CalendarAdapter;
import com.ynz.fin.average233day.utils.LocalDateAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class Cal233DayAveragePrice {
    private static final int preCount = 20 + 1;
    private static final int dayOfAverage = 233;

    public List<HistoricalQuote> getStockOneYearBefore(String stock, LocalDate refDate) throws IOException {
        DayOfWeek dayOfWeek = refDate.getDayOfWeek();
        if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            throw new IllegalArgumentException("weekend is not a valid reference day.");
        }

        List<HistoricalQuote> historicalQuotes = null;

        try {
            //Stock found = YahooFinance.get(stock);
            Calendar from = LocalDateAdapter.of(refDate.minus(13, ChronoUnit.MONTHS)).toCalendar();
            //Calendar from = LocalDateAdapter.of(determineFirstDayOfDataSet(refDate)).toCalendar();
            Calendar to = LocalDateAdapter.of(refDate).toCalendar();
            Stock found = YahooFinance.get(stock, from, to, Interval.DAILY);
            if (found == null) return null;

            historicalQuotes = found.getHistory(from, to, Interval.DAILY);
        } catch (IOException e) {
            throw e;
        }

        return historicalQuotes;
    }

    public LocalDate determineFirstDayOfDataSet(LocalDate refDate) {
        List<HistoricalQuote> historicalQuotes = null;
        try {
            Stock found = YahooFinance.get("tdy");
            Calendar calendar = LocalDateAdapter.of(refDate.minus(13, ChronoUnit.MONTHS)).toCalendar();
            historicalQuotes = found.getHistory(calendar, Interval.DAILY);

        } catch (IOException e) {
            log.error("method: determineFirstDayOfDataSet ", e);
        }

        int size = historicalQuotes.size();
        List<HistoricalQuote> partOfQuotes = historicalQuotes.subList(size - preCount - dayOfAverage, size - 1);

        return CalendarAdapter.of(partOfQuotes.get(0).getDate()).toLocalDate();
    }

    public List<HistoricalQuote> get20DaysBeforeLastDay(List<HistoricalQuote> historicalQuotes) {
        int size = historicalQuotes.size();
        return historicalQuotes.subList(size - 21, size - 1);
    }

    public HistoricalQuote getLastDay(List<HistoricalQuote> historicalQuotes) {
        return historicalQuotes.get(historicalQuotes.size());
    }

    public Map<HistoricalQuote, Double> cal20Days233Average(List<HistoricalQuote> historicalQuotes) {
        Map<HistoricalQuote, Double> average233 = new LinkedHashMap<>();

        int size = historicalQuotes.size();

        //20 days before last day
        List<HistoricalQuote> preLast20Days = get20DaysBeforeLastDay(historicalQuotes);

        for (int i = size - preCount; i < size - 1; i++) {
            double avg = historicalQuotes.subList(i - dayOfAverage, i - 1).stream()
                    .map(HistoricalQuote::getAdjClose)
                    .collect(Collectors.averagingDouble(BigDecimal::doubleValue));

            average233.put(historicalQuotes.get(i), avg);
        }

        return average233;
    }

    public boolean isIncremental(String stock, LocalDate startDate) {
        List<HistoricalQuote> historicalQuotes = new ArrayList<>();
        try {
            historicalQuotes = getStockOneYearBefore(stock, startDate);
        } catch (IOException e) {
            return false;
        }

        if (historicalQuotes.size() < 255) {

            return false;
        }

        Map<HistoricalQuote, Double> average20Days = cal20Days233Average(historicalQuotes);

        List<Double> averages = new ArrayList<>(average20Days.values());

        int size = averages.size();
        return averages.get(0) < averages.get(size - 1);
    }

}
