package com.ynz.fin.average233day.helpers;

import com.ynz.fin.average233day.domain.nasdaq.NasdaqStock;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class LoadNasdaqStocks {

    private static final String target = "nasdaqlisted.txt";

    private static final String pStart = "^Symbol.+NextShares$";
    private static final String pEnd = "^File Creation Time.+|||||||";

    public List<NasdaqStock> doAction() {
        List<String> strings = null;
        Path targetPath = Paths.get(target);
        try {
            strings = Files.readAllLines(targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<NasdaqStock> stocks = new ArrayList<>();

        for (String line : strings) {
            if (line.matches(pStart)) continue;
            if (line.matches(pEnd)) break;

            String[] fields = line.split("\\|");
            if (fields.length != 8) continue;

            NasdaqStock stock = new NasdaqStock();
            for (int i = 0; i < fields.length; i++) {

                switch (i) {
                    case 0:
                        stock.setSymbol(fields[0]);
                        break;
                    case 1:
                        stock.setSecurityName(fields[1]);
                        break;
                    case 2:
                        stock.setMarketCategory(fields[2]);
                        break;
                    case 3:
                        stock.setTestIssue(fields[3]);
                        break;
                    case 4:
                        stock.setFinancialStatus(fields[4]);
                        break;
                    case 5:
                        stock.setRoundLotSize(fields[5]);
                        break;
                    case 6:
                        stock.setETF(fields[6]);
                        break;
                    case 7:
                        stock.setNextShares(fields[7]);
                }
            }

            stocks.add(stock);
        }

        return stocks;
    }

    public Map<Character, TreeSet<NasdaqStock>> groupBySymbol() {
        List<NasdaqStock> stocks = this.doAction();
        return stocks.stream()
                .collect(Collectors.groupingBy(nasdaqStock -> nasdaqStock.getSymbol().charAt(0), Collectors.toCollection(TreeSet::new)));
    }


}
