package com.ynz.fin.average233day.helpers.factors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Component("linerRegressionFactor")
@RequiredArgsConstructor(staticName = "of")
public class LinerRegressionDataFactors implements DataSetFactors {
    public enum Factor {
        SLOP, INTERCEPT, R_SQUARE, R
    }

    private final SimpleRegression regression;

    private List<Double> dataList;

    @Override
    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    @Override
    public List getDataList() {
        return this.dataList;
    }

    @Override
    public Map<Factor, Double> calDataSetFactors() {
        if (dataList == null) throw new IllegalStateException("dataList is not given");
        if (!dataList.stream().noneMatch(aDouble -> aDouble == null))
            throw new IllegalArgumentException(" dataList contains null value... ");

        Map<Factor, Double> factors = new Hashtable<>();

        double[] independent = dataList.stream().mapToDouble(Double::doubleValue).toArray();

        double[][] data = new double[dataList.size()][2];

        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = i;
            data[i][1] = independent[i];
        }

        regression.addData(data);

        factors.put(Factor.R_SQUARE, regression.getRSquare());
        factors.put(Factor.SLOP, regression.getSlope());
        factors.put(Factor.INTERCEPT, regression.getIntercept());
        factors.put(Factor.R, regression.getR());

        return factors;
    }

}
