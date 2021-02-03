package com.ynz.fin.average233day.helpers.factorpattern;

import com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EightAveragePenetrateTwentyOneAverageTest {

    private Map<LinerRegressionDataFactors.Factor, Double> line1;
    private Map<LinerRegressionDataFactors.Factor, Double> line2;
    private Map<LinerRegressionDataFactors.Factor, Double> line3;

    @BeforeEach
    void setUp() {
        line1 = new LinkedHashMap<>();
        line2 = new LinkedHashMap<>();
        line3 = new LinkedHashMap<>();

        line1.put(LinerRegressionDataFactors.Factor.SLOP, 1.00);
        line1.put(LinerRegressionDataFactors.Factor.INTERCEPT, 2.00);

        line2.put(LinerRegressionDataFactors.Factor.SLOP, 0.80);
        line2.put(LinerRegressionDataFactors.Factor.INTERCEPT, 2.50);

        line3.put(LinerRegressionDataFactors.Factor.SLOP, 0.80);
        line3.put(LinerRegressionDataFactors.Factor.INTERCEPT, 2.00);
    }

    @Test
    void penetrate() {
        boolean r = EightAveragePenetrateTwentyOneAverage.of(line1, line2).penetrate();
        assertThat(r, is(true));
    }

    @Test
    void givenTwoSameLine_ReturnFalse() {
        boolean r = EightAveragePenetrateTwentyOneAverage.of(line1, line1).penetrate();
        assertFalse(r);
    }

    @Test
    void givenSameInterceptDifferentSlop_ReturnFalse() {
        boolean r = EightAveragePenetrateTwentyOneAverage.of(line1, line3).penetrate();
        assertFalse(r);
    }

    @Test
    void whenLine21PenetrateLine8_ReturnFalse() {
        boolean r = EightAveragePenetrateTwentyOneAverage.of(line2, line1).penetrate();
        assertFalse(r);
    }
}