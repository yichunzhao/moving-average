package com.ynz.fin.average233day.helpers.factors;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SimpleDataSetFactorTest {
    private SimpleDataSetFactor simpleDataSetFactor = new SimpleDataSetFactor();

    @Test
    void givenIncrementalDataList_ItReturnTrue() {
        List<Integer> integerList = Arrays.asList(1, 3, 5, 7, 9);
        boolean result = simpleDataSetFactor.isIncremental(integerList);
        assertTrue(result);
    }

    @Test
    void givenDecrementalDataList_ItReturnFalse() {
        List<Integer> integerList = Arrays.asList(9, 6, 3);
        boolean result = simpleDataSetFactor.isIncremental(integerList);
        assertFalse(result);

    }

    @Test
    void givenListContainingEqualityItems_ItReturnFalse() {
        List<Integer> integerList = Arrays.asList(2, 2, 2);
        boolean result = simpleDataSetFactor.isIncremental(integerList);
        assertFalse(result);
    }

    @Test
    void givenListIncrementalButContainEqualItems_ItReturnTrue() {
        List<Integer> integerList = Arrays.asList(4, 5, 5, 7);
        boolean result = simpleDataSetFactor.isIncremental(integerList);
        assertTrue(result);

    }

}