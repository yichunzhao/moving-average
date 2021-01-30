package com.ynz.fin.average233day.helpers;

import java.util.List;
import java.util.Map;

public interface AverageCalculator<T, R> {
    Map<T, R> compute(List<T> data) throws Exception;
}
