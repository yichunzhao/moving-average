package com.ynz.fin.average233day.service;

import java.util.Calendar;
import java.util.List;

public interface LinePatterns {

    List<String> findAllPenetratePatternsByTickers(List<String> tickers, Calendar to);
}
