package com.ynz.fin.average233day.domain.yahoo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
@Table(name = "STOCK_HISTORICAL_QUOTES")
@Getter
@Setter
public class StockHistoricalQuote {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Integer id;

    private String symbol;

    @Temporal(TemporalType.DATE)
    private Calendar date;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal close;
    private BigDecimal adjClose;
    private Long volume;
}
