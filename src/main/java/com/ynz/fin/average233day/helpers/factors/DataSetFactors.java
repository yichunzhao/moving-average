package com.ynz.fin.average233day.helpers.factors;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors.Factor;

public interface DataSetFactors<K extends Factor, T extends Number> {

    Map<K, T> calDataSetFactors();

    DataSetFactors<K, T> setDataList(Collection<T> dataList);

    List<T> getDataList();
}
