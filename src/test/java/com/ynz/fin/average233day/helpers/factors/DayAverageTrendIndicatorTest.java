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

    private List<Double> dataListDec;
    private Map<LinerRegressionDataFactors.Factor, Double> factorDoubleMapDec;

    @BeforeEach
    void setUp() {
        dataList = Arrays.asList(1.23, 2.89, 3.45, 4.56);
        regressionDataFactors.setDataList(dataList);
        factorDoubleMap = regressionDataFactors.calDataSetFactors();

        dataListDec = Arrays.asList(599.56, 478.34, 345.67, 223.44);
        regressionDataFactors.setDataList(dataListDec);
        factorDoubleMapDec = regressionDataFactors.calDataSetFactors();
    }

    @Test
    void isIncremental() {
        log.info("indicating a data list trend ");
        trendIndicator.setFactorMap(factorDoubleMap);

        assertTrue(trendIndicator.isIncremental());
    }

    @Test
    void whenGivenDataListDec_ItReturnsFalse() {
        log.info("indicating a data list trend Dec ");
        trendIndicator.setFactorMap(factorDoubleMapDec);

        assertFalse(trendIndicator.isIncremental());
    }

    @Test
    void whenFactorMapIsNotGiven_ThrowIllegalStateException() {
        log.info("when factorMap is not given ");
        assertThrows(IllegalStateException.class, () -> trendIndicator.isIncremental());
    }
}