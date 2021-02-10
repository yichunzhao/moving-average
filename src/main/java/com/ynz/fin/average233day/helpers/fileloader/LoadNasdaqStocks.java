package com.ynz.fin.average233day.helpers.fileloader;

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

@Component("allNasdaqStocks")
public class LoadNasdaqStocks implements FileLoader<NasdaqStock> {

    private static final String target = "nasdaqlisted.txt";

    private static final String pStart = "^Symbol.+NextShares$";
    private static final String pEnd = "^File Creation Time.+|||||||";

    public List<NasdaqStock> load() {
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
            stock.setSymbol(fields[0]);
            stock.setSecurityName(fields[1]);
            stock.setMarketCategory(fields[2]);
            stock.setTestIssue(fields[3]);
            stock.setFinancialStatus(fields[4]);
            stock.setRoundLotSize(fields[5]);
            stock.setETF(fields[6]);
            stock.setNextShares(fields[7]);
            stocks.add(stock);
        }

        return stocks;
    }

    public Map<Character, TreeSet<NasdaqStock>> groupBySymbol() {
        List<NasdaqStock> stocks = this.load();
        return stocks.stream()
                .collect(Collectors.groupingBy(nasdaqStock -> nasdaqStock.getSymbol().charAt(0), Collectors.toCollection(TreeSet::new)));
    }

}
