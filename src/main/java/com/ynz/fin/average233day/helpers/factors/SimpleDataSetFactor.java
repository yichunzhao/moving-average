package com.ynz.fin.average233day.helpers.factors;

import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * a simple method,
 */
@Component("simpleFactor")
public class SimpleDataSetFactor implements DataSetTrend<Integer> {
    private List<Integer> detailList;

    public SimpleDataSetFactor(List<Integer> detailList) {
        this.detailList = detailList;
    }

    @Override
    public boolean isIncremental() {
        Deque<Integer> queue = new LinkedList<>(detailList);

        boolean f = false;

        for (int i = 0; i < queue.size(); i++) {
            Integer b = queue.poll();
            if (queue.isEmpty()) break;
            Integer a = queue.peek();
            if (b == a) continue;
            if (b > queue.peek()) {
                f = false;
            } else {
                f = true;
            }
        }

        return f;
    }
}
