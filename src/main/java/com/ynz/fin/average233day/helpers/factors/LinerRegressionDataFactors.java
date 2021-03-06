package com.ynz.fin.average233day.helpers.factors;

import com.ynz.fin.average233day.utils.NumberFormatter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.ynz.fin.average233day.helpers.factors.LinerRegressionDataFactors.Factor;

@Component("linerRegressionFactor")
@Scope("prototype")
@RequiredArgsConstructor
public class LinerRegressionDataFactors implements DataSetFactors<Factor, Double> {
    public enum Factor {
        SLOP, INTERCEPT, R_SQUARE, R
    }

    private final SimpleRegression regression;

    private List<Double> dataList;

    @Override
    public DataSetFactors<Factor, Double> setDataList(Collection<Double> dataList) {
        this.dataList = new ArrayList<>(dataList);
        return this;
    }

    @Override
    public List<Double> getDataList() {
        return this.dataList;
    }

    @Override
    public Map<Factor, Double> calDataSetFactors() {
        if (dataList == null || dataList.isEmpty()) throw new IllegalStateException("dataList is not given");
        if (!dataList.stream().noneMatch(aDouble -> aDouble == null))
            throw new IllegalArgumentException(" dataList contains null value... ");

        Map<Factor, Double> factors = new LinkedHashMap<>();

        double[] independent = dataList.stream().mapToDouble(Double::doubleValue).toArray();

        double[][] data = new double[dataList.size()][2];

        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = i;
            data[i][1] = independent[i];
        }

        regression.clear();
        regression.addData(data);

        double slop = regression.getSlope();
        factors.put(Factor.SLOP, NumberFormatter.of(slop).round());

        double intercept = regression.getIntercept();
        factors.put(Factor.INTERCEPT, NumberFormatter.of(intercept).round());

        double r = regression.getR();
        factors.put(Factor.R, NumberFormatter.of(r).round());

        double rSquare = regression.getRSquare();
        factors.put(Factor.R_SQUARE, NumberFormatter.of(rSquare).round());

        return factors;
    }

}
