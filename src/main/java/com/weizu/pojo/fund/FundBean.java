package com.weizu.pojo.fund;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

@Alias("fundBean")
public class FundBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;
    private String name;
    private String type;
    private String manager;
    private String fundScale;
    private BigDecimal lastMonthGrowth;
    private BigDecimal lastThreeMonthGrowth;
    private BigDecimal lastSixMonthGrowth;
    private BigDecimal lastYearGrowth;
    private Double buyMin;
    private BigDecimal buyRate;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {return  name; }
    public void setName(String name) {this.name = name; }
    public String getType() {return  type; }
    public void setType(String type) {this.type = type; }
    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }
    public String getFundScale() {return  fundScale; }
    public void setFundScale(String fundScale) {this.fundScale = fundScale; }
    public BigDecimal getLastMonthGrowth() {return  lastMonthGrowth; }
    public  void  setLastMonthGrowth(BigDecimal lastMonthGrowth) {this.lastMonthGrowth = lastMonthGrowth; }
    public  BigDecimal getLastThreeMonthGrowth() {return  lastThreeMonthGrowth; }
    public void setLastThreeMonthGrowth(BigDecimal lastThreeMonthGrowth) {this.lastThreeMonthGrowth = lastThreeMonthGrowth; }
    public  BigDecimal getLastSixMonthGrowth() {return  lastSixMonthGrowth; }
    public void setLastSixMonthGrowth(BigDecimal lastSixMonthGrowth) {this.lastSixMonthGrowth = lastSixMonthGrowth; }
    public  BigDecimal getLastYearGrowth() {return  lastYearGrowth; }
    public void setLastYearGrowth(BigDecimal lastYearGrowth) {this.lastYearGrowth = lastYearGrowth; }
    public  Double getBuyMin() {return  buyMin; }
    public void setBuyMin(Double buyMin) {this.buyMin = buyMin; }
    public  BigDecimal getBuyRate() {return  buyRate; }
    public void setBuyRate(BigDecimal buyRate) {this.buyRate = buyRate; }
}
