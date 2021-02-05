package com.ynz.fin.average233day.service;


import com.ynz.fin.average233day.helpers.accessquotes.StockHistoricalQuoteLoader;
import com.ynz.fin.average233day.helpers.calculators.AverageCalculatorContext;
import com.ynz.fin.average233day.helpers.calculators.AverageTwentyOneDays;
import com.ynz.fin.average233day.helpers.calculators.AveragedEightDays;
import com.ynz.fin.average233day.helpers.factorpattern.EightAveragePenetrateTwentyOneAverage;
import com.ynz.fin.average233day.helpers.factors.DayAverageTrendIndicator;
import com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors;
import com.ynz.fin.average233day.utils.filestore.ResultFileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yahoofinance.histquotes.HistoricalQuote;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EightDaysPenetrate20Days {
    @Value("${file.storage.filename}")
    private String filename;

    @Value("${file.storage.resultDir}")
    private String folderName;

    private final StockHistoricalQuoteLoader quoteLoader;
    private final AverageCalculatorContext calculatorContext;
    private final LinerRegressionDataFactors linerRegressionDataFactors;
    private final DayAverageTrendIndicator dayAverageTrendIndicator;

    /**
     * find all 10-day-8DayAverage penetrate 10-day-21DayAverage
     *
     * @param tickers List<String> target tickers.
     * @param to      Calendar a reference date
     * @return List<String> tickers showing the desired pattern
     */
    public List<String> findAllPenetratePatternsByTickers(List<String> tickers, Calendar to) {
        if (tickers == null || tickers.isEmpty()) throw new IllegalArgumentException("tickers is empty");
        if (to == null) throw new IllegalArgumentException("calendar is not given");

        ResultFileStorage rs = ResultFileStorage.create(folderName, filename);
        rs.saveLine("P-list " + LocalDateTime.now().toString());

        ArrayList<String> penetrateList = new ArrayList<>();

        for (String ticker : tickers) {

            //load quotes in history
            List<HistoricalQuote> quotes = quoteLoader.loadPastMonthQuotes(ticker, to, 2);
            if (quotes == null) continue;

            //filter out null value
            List<HistoricalQuote> validatedQuotes = quotes.stream().filter(q -> q.getClose() != null).collect(toList());

            //cal. 10-day-8DayAverage
            Map<HistoricalQuote, Double> tenDay8DayAverage = calculatorContext.execute(validatedQuotes, AveragedEightDays.class);
            if (tenDay8DayAverage == null) continue;

            //cal. 10-day-21DayAverage
            Map<HistoricalQuote, Double> tenDay21DayAverage = calculatorContext.execute(validatedQuotes, AverageTwentyOneDays.class);
            if (tenDay21DayAverage == null) continue;

            //cal. liner regression factors
            linerRegressionDataFactors.setDataList(new ArrayList(tenDay8DayAverage.values()));
            Map<LinerRegressionDataFactors.Factor, Double> tenDay8AverageRegressionFactors = linerRegressionDataFactors.calDataSetFactors();

            //cal. liner regression factors
            linerRegressionDataFactors.setDataList(new ArrayList(tenDay21DayAverage.values()));
            Map<LinerRegressionDataFactors.Factor, Double> tenDay21AverageRegressionFactors = linerRegressionDataFactors.calDataSetFactors();

            //data set trend
            dayAverageTrendIndicator.setFactorMap(tenDay8AverageRegressionFactors);
            boolean day8TrendIncrease = dayAverageTrendIndicator.isIncremental();

            dayAverageTrendIndicator.setFactorMap(tenDay21AverageRegressionFactors);
            boolean day21TrendIncrease = dayAverageTrendIndicator.isIncremental();

            //penetrate?
            if (day8TrendIncrease && day21TrendIncrease) {
                boolean isP = EightAveragePenetrateTwentyOneAverage
                        .of(tenDay8AverageRegressionFactors, tenDay21AverageRegressionFactors)
                        .penetrate();
                if (isP) penetrateList.add(ticker);
            } else {
                continue;
            }

            rs.saveLine(" " + ticker + " ");
        }
        return penetrateList;
    }

}
