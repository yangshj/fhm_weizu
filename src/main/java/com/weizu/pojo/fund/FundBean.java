package com.weizu.pojo.fund;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("fundBean")
public class FundBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;
    private String name;
    private String type;
    private String manager;
    private String fundScale;
    private String lastMonthGrowth;
    private String lastThreeMonthGrowth;
    private String lastSixMonthGrowth;
    private String lastYearGrowth;
    private String buyMin;
    private String buyRate;

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
    public String getLastMonthGrowth() {return  lastMonthGrowth; }
    public  void  setLastMonthGrowth(String lastMonthGrowth) {this.lastMonthGrowth = lastMonthGrowth; }
    public  String getLastThreeMonthGrowth() {return  lastThreeMonthGrowth; }
    public void setLastThreeMonthGrowth(String lastThreeMonthGrowth) {this.lastThreeMonthGrowth = lastThreeMonthGrowth; }
    public  String getLastSixMonthGrowth() {return  lastSixMonthGrowth; }
    public void setLastSixMonthGrowth(String lastSixMonthGrowth) {this.lastSixMonthGrowth = lastSixMonthGrowth; }
    public  String getLastYearGrowth() {return  lastYearGrowth; }
    public void setLastYearGrowth(String lastYearGrowth) {this.lastYearGrowth = lastYearGrowth; }
    public  String getBuyMin() {return  buyMin; }
    public void setBuyMin(String buyMin) {this.buyMin = buyMin; }
    public  String getBuyRate() {return  buyRate; }
    public void setBuyRate(String buyRate) {this.buyRate = buyRate; }
}
