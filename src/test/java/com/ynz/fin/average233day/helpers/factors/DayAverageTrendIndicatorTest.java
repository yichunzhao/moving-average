package com.ynz.fin.average233day.helpers.factors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class DayAverageTrendIndicatorTest {

    @Autowired
    private DayAverageTrendIndicator trendIndicator;

    @Autowired
    private LinerRegressionDataFactors regressionDataFactors;
    private List<Double> dataList;
    private Map<LinerRegressionDataFactors.Factor, Double> factorDoubleMap;

    private List<Double> dataList1;
    private Map<LinerRegressionDataFactors.Factor, Double> factorDoubleMap1;

    private List<Double> dataList2;
    private Map<LinerRegressionDataFactors.Factor, Double> factorDoubleMap2;

    @BeforeEach
    void setUp() {
        //ticker: MRSN ref. date: Tue Feb 09 12:00:00 CET 2021;8-day MA
        dataList = Arrays.asList(20.53, 20.24, 19.94, 19.61, 19.52, 19.64, 19.87, 20.11, 20.6, 21.28);
        regressionDataFactors.setDataList(dataList);
        factorDoubleMap = regressionDataFactors.calDataSetFactors();

        //ticker: ALLK ref. date: Sat Feb 06 12:00:00 CET 2021; 8-day MA
        dataList1 = Arrays.asList(128.51, 129.2, 129.38, 129.12, 129.9, 130.66, 131.62, 132.37, 133.63, 135.66);
        regressionDataFactors.setDataList(dataList1);
        factorDoubleMap1 = regressionDataFactors.calDataSetFactors();

        //ticker: ALLK ref. date: Sat Feb 06 12:00:00 CET 2021; 21-day MA
        dataList2 = Arrays.asList(133.29, 132.26, 131.23, 129.99, 129.53, 129.25, 129.07, 129.29, 130.17, 131.06);
        regressionDataFactors.setDataList(dataList2);
        factorDoubleMap2 = regressionDataFactors.calDataSetFactors();
    }

    @Test
    void whenGivenDataListVaryAroundConstant_ItReturnsFalse() {
        log.info("indicating a data list trend ");
        assertFalse(trendIndicator.setFactorMap(factorDoubleMap).isIncremental());
    }

    @Test
    void whenGivenDataListIncrease_ItReturnsTrue() {
        log.info("indicating a data list trend Dec ");
        assertTrue(trendIndicator.setFactorMap(factorDoubleMap1).isIncremental());
    }

    @Test
    void whenGivenDataListDec_ItReturnsFalse() {
        log.info("indicating a data list trend Dec ");
        assertFalse(trendIndicator.setFactorMap(factorDoubleMap2).isIncremental());
    }

    @Test
    void whenFactorMapIsNotGiven_ThrowIllegalStateException() {
        log.info("when factorMap is not given ");
        assertThrows(IllegalStateException.class, () -> trendIndicator.setFactorMap(null).isIncremental());
    }

}