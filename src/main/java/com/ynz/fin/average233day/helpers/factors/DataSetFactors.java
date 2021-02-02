package com.ynz.fin.average233day.helpers.factors;

import java.util.Map;

public interface DataSetFactors<K extends LinerRegressionDataSetFactor.Factor, T extends Number >  {

    public Map<K, T> calDataSetFactors();
}
