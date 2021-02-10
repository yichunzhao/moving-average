package com.ynz.fin.average233day.service;


import com.ynz.fin.average233day.helpers.accessquotes.StockHistoricalQuoteLoader;
import com.ynz.fin.average233day.helpers.calculators.AverageCalculatorContext;
import com.ynz.fin.average233day.helpers.calculators.Average21Days;
import com.ynz.fin.average233day.helpers.calculators.Average8Days;
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

import static com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors.Factor.INTERCEPT;
import static com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors.Factor.R_SQUARE;
import static com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors.Factor.SLOP;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EightDaysPenetrate20Days implements LinePatterns {
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
    @Override
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
            Map<HistoricalQuote, Double> tenDay8DayAverage = calculatorContext.execute(validatedQuotes, Average8Days.class);
            if (tenDay8DayAverage == null) continue;

            //cal. 10-day-21DayAverage
            Map<HistoricalQuote, Double> tenDay21DayAverage = calculatorContext.execute(validatedQuotes, Average21Days.class);
            if (tenDay21DayAverage == null) continue;

            //cal. liner regression factors
            linerRegressionDataFactors.setDataList(new ArrayList(tenDay8DayAverage.values()));
            Map<LinerRegressionDataFactors.Factor, Double> ten8AverageRegressionFactors = linerRegressionDataFactors.calDataSetFactors();

            //cal. liner regression factors
            linerRegressionDataFactors.setDataList(new ArrayList(tenDay21DayAverage.values()));
            Map<LinerRegressionDataFactors.Factor, Double> tenDay21AverageRegressionFactors = linerRegressionDataFactors.calDataSetFactors();

            //data set trend
            dayAverageTrendIndicator.setFactorMap(ten8AverageRegressionFactors);
            boolean day8TrendIncrease = dayAverageTrendIndicator.isIncremental();

            dayAverageTrendIndicator.setFactorMap(tenDay21AverageRegressionFactors);
            boolean day21TrendIncrease = dayAverageTrendIndicator.isIncremental();

            rs.saveLine(" ......... ticker " + ticker + " .............");

            rs.saveLine("8-days-averages");
            List<Double> valuesOf8DaysAvr = new ArrayList<>(tenDay8DayAverage.values());
            rs.saveLine(valuesOf8DaysAvr.toString());

            String factors8Days = ten8AverageRegressionFactors.get(SLOP) + " " + ten8AverageRegressionFactors.get(INTERCEPT) + " " + ten8AverageRegressionFactors.get(R_SQUARE);
            rs.saveLine(factors8Days);

            rs.saveLine("21-days-averages");
            List<Double> valuesOf21DaysAvr = new ArrayList<>(tenDay21DayAverage.values());
            rs.saveLine(valuesOf21DaysAvr.toString());

            String factors10Days = tenDay21AverageRegressionFactors.get(SLOP) + " " + tenDay21AverageRegressionFactors.get(INTERCEPT) + " " + tenDay21AverageRegressionFactors.get(R_SQUARE);
            rs.saveLine(factors10Days);

            //penetrate?
            if (day8TrendIncrease && day21TrendIncrease) {
                boolean isP = EightAveragePenetrateTwentyOneAverage
                        .of(ten8AverageRegressionFactors, tenDay21AverageRegressionFactors)
                        .penetrate();
                if (isP) {
                    penetrateList.add(ticker);
                    rs.saveLine("pList: " + ticker);

                    rs.saveLine(" ++++++++  ticker " + ticker + " .............");
                }
            } else {
                continue;
            }
        }
        return penetrateList;
    }


}
