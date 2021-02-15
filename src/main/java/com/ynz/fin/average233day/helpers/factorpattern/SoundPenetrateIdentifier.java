package com.ynz.fin.average233day.helpers.factorpattern;


import com.ynz.fin.average233day.helpers.calculators.Average21Days;
import com.ynz.fin.average233day.helpers.calculators.Average233Days;
import com.ynz.fin.average233day.helpers.calculators.Average8Days;
import com.ynz.fin.average233day.helpers.calculators.AverageCalculatorContext;
import com.ynz.fin.average233day.helpers.factorpattern.PenetrateIdentifier.ResultType;
import com.ynz.fin.average233day.helpers.factors.DayAverageTrendIndicator;
import com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors;
import com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors.Factor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class SoundPenetrateIdentifier {
    private final AverageCalculatorContext calculatorContext;
    private final LinerRegressionDataFactors factorCalculator;
    private final DayAverageTrendIndicator trendIndicator;

    public Map<ResultType, String> handleSingle(@NonNull List<HistoricalQuote> quotes) throws Exception {
        List<HistoricalQuote> validatedQuotes = Optional.ofNullable(quotes)
                .orElseThrow(() -> new IllegalArgumentException("input quotes is a null"))
                .stream().filter(q -> q.getClose() != null).collect(toList());

        //results are sorted according to Calendar date in A-dir
        //consider to throw exception, rather than returning null.
        SortedMap<HistoricalQuote, Double> ma233 = calculatorContext.execute(validatedQuotes, Average233Days.class);

        //liner regression results
        Map<Factor, Double> factors = factorCalculator.setDataList(ma233.values()).calDataSetFactors();

        //identifying trend
        boolean isMa233Incremental = trendIndicator.setFactorMap(factors).isIncremental();

        //p-Last > p-Ma-233-last
        Double pMA233Last = ma233.get(ma233.lastKey());
        Double pLast = quotes.get(quotes.size() - 1).getClose().doubleValue();

        Map<ResultType, String> resultMap = new HashMap<>();

        if (isMa233Incremental && pLast > pMA233Last) {
            //8-day-MA
            SortedMap<HistoricalQuote, Double> ma8 = calculatorContext.execute(validatedQuotes, Average8Days.class);
            Map<Factor, Double> ma8Factors = factorCalculator.setDataList(ma8.values()).calDataSetFactors();
            boolean flag8 = trendIndicator.setFactorMap(ma8Factors).isIncremental();

            //21-Day-MA
            SortedMap<HistoricalQuote, Double> ma21 = calculatorContext.execute(validatedQuotes, Average21Days.class);
            Map<Factor, Double> ma21Factors = factorCalculator.setDataList(ma21.values()).calDataSetFactors();
            boolean flag21 = trendIndicator.setFactorMap(ma21Factors).isIncremental();

            if (flag8 && flag21) {
                resultMap = PenetrateIdentifier.of(ma8, ma21).penetrate();
            } else {
                resultMap.put(ResultType.IS_PENETRATED, Boolean.FALSE.toString());
            }
        }

        return resultMap;
    }

}
