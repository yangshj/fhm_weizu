package com.weizu.pojo.fund;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

@Alias("fundBean")
public class FundBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /** id */
    private Long id;
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 类型 */
    private String type;
    /** 基金经理 */
    private String manager;
    /** 基金规模 */
    private String fundScale;
    /** 最近一月 */
    private BigDecimal lastMonthGrowth;
    /** 最近三月 */
    private BigDecimal lastThreeMonthGrowth;
    /** 最近六月 */
    private BigDecimal lastSixMonthGrowth;
    /** 最近一年 */
    private BigDecimal lastYearGrowth;
    /** 最少买入 */
    private Double buyMin;
    /** 买入费率 */
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
