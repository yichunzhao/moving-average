package com.ynz.fin.average233day.domain.nasdaq;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NASDAQ_STOCKS")
@Getter
@Setter
public class NasdaqStock implements Comparable<NasdaqStock> {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(unique = true)
    private String symbol;
    private String securityName;
    private String marketCategory;
    private String testIssue;
    private String financialStatus;
    private String roundLotSize;
    private String eTF;
    private String nextShares;

    private Boolean dataNotEnough;
    private Boolean is20Day233AvgIncremental;

    public NasdaqStock() {
    }

    @Builder
    public NasdaqStock(String symbol, String securityName, String marketCategory, String testIssue, String financialStatus, String roundLotSize, String eTF, String nextShares) {
        this.symbol = symbol;
        this.securityName = securityName;
        this.marketCategory = marketCategory;
        this.testIssue = testIssue;
        this.financialStatus = financialStatus;
        this.roundLotSize = roundLotSize;
        this.eTF = eTF;
        this.nextShares = nextShares;
    }

    public String toPlainText() {
        StringBuilder sb = new StringBuilder(symbol);
        sb.append(",").append(securityName);
        sb.append(",").append(marketCategory);
        sb.append(",").append(testIssue);
        sb.append(",").append(financialStatus);
        sb.append(",").append(roundLotSize);
        sb.append(",").append(eTF);
        sb.append(",").append(nextShares);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public int compareTo(NasdaqStock nasdaqStock) {
        return getSymbol().compareTo(nasdaqStock.getSymbol());
    }
}
