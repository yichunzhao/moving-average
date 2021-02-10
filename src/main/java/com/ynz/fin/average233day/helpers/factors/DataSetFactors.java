package com.ynz.fin.average233day.helpers.factors;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DataSetFactors<K extends LinerRegressionDataFactors.Factor, T extends Number >  {

    Map<K, T> calDataSetFactors();

    void setDataList(Collection<T> dataList);
    List<T> getDataList();
}
