package com.ynz.fin.average233day.helpers.factors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Component("linerRegressionFactor")
@RequiredArgsConstructor(staticName = "of")
public class LinerRegressionDataSetFactor implements DataSetFactors {
    public enum Factor {
        SLOP, INTERCEPT, R_SQUARE, R
    }

    private SimpleRegression regression;

    private final List<Double> dataList;

    @Autowired
    public void setRegression(SimpleRegression regression) {
        this.regression = regression;
    }

    @Override
    public Map<Factor, Double> calDataSetFactors() {
        if (dataList == null) throw new IllegalStateException("dataList is not given");
        if (!dataList.stream().noneMatch(aDouble -> aDouble == null))
            throw new IllegalArgumentException(" dataList contains null value... ");

        Map<Factor, Double> factors = new Hashtable<>();

        double[] independent = dataList.stream().mapToDouble(Double::doubleValue).toArray();

        double[][] data = new double[dataList.size()][];

        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < 2; j++) {
                data[i][j] = i;
                data[i][j] = independent[i];
            }
        }

        regression.addData(data);

        factors.put(Factor.R_SQUARE, regression.getRSquare());
        factors.put(Factor.SLOP, regression.getSlope());
        factors.put(Factor.INTERCEPT, regression.getIntercept());
        factors.put(Factor.R, regression.getR());

        return factors;
    }


}
