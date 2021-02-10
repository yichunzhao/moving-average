package com.ynz.fin.average233day.service;

import com.ynz.fin.average233day.domain.nasdaq.NasdaqStock;
import com.ynz.fin.average233day.helpers.fileloader.LoadNasdaqStocks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllNasStocksHandler {
    private final LoadNasdaqStocks loadNasdaqStocks;

    public void calAllNasStock20Days233DaysIncremental() {
        List<NasdaqStock> nStocks = loadNasdaqStocks.load();
        List<String> tickers = nStocks.stream().map(NasdaqStock::getSymbol).collect(toList());

        for (String ticker : tickers) {


        }

    }

}
