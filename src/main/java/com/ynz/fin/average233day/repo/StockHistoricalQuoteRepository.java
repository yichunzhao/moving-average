package com.ynz.fin.average233day.repo;

import com.ynz.fin.average233day.domain.yahoo.StockHistoricalQuote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoricalQuoteRepository extends CrudRepository<StockHistoricalQuote, Integer> {
}
