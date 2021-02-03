package com.ynz.fin.average233day.helpers.factors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class LinerRegressionTest {

    @Autowired
    private LinerRegression factors;

    @Test
    void solverIsInjected() {
        assertNotNull(factors);
    }

    @Test
    void testCalFactors() {
        log.info("test cal. factors");
        factors.setDataList(Arrays.asList(1.34, 4.56, 7.88, 9.60));
        assertNotNull(factors.getDataList());

        Map<LinerRegression.Factor, Double> factorDoubleMap = factors.calDataSetFactors();
        assertNotNull(factorDoubleMap);

        log.info(factorDoubleMap.toString());

        assertAll(
                () -> assertNotNull(factorDoubleMap.get(LinerRegression.Factor.INTERCEPT)),
                () -> assertNotNull(factorDoubleMap.get(LinerRegression.Factor.SLOP)),
                () -> assertNotNull(factorDoubleMap.get(LinerRegression.Factor.R_SQUARE)),
                () -> assertNotNull(factorDoubleMap.get(LinerRegression.Factor.R))
        );
    }

    @Test
    void ifDataListIsNotGiven_ThenThrowIllegalStateException() {
        log.info("test data list is not set, throw illegalStateException ");
        assertThrows(IllegalStateException.class, () -> factors.calDataSetFactors());
    }

    @Test
    void ifDataListContainNullValue_ThenThrowIllegalArgumentException() {
        log.info("if test data contains null, throw illegalArgumentException ");
        factors.setDataList(Arrays.asList(1.34, 4.56, 7.88, 9.60, null));
        assertThrows(IllegalArgumentException.class, () -> factors.calDataSetFactors());
    }

}