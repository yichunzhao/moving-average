package com.ynz.fin.average233day.repo;

import com.ynz.fin.average233day.domain.nasdaq.NasdaqStock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NasdaqStockRepository extends CrudRepository<NasdaqStock, Integer> {
}
