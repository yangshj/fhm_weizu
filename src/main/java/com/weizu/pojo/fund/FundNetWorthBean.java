package com.weizu.pojo.fund;

import org.apache.ibatis.type.Alias;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.Serializable;
import java.math.BigDecimal;

@Alias("fundNetWorthBean")
public class FundNetWorthBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private long fundId;
    private Float netWorth;
    private String worthDate;
    private BigDecimal dayGrowth;
    private String expectWorthDate;
    private BigDecimal expectWorth;
    private BigDecimal expectGrowth;

    public long getId() {return id ;}
    public void setId(Long id) {this.id = id;}
    public long getFundId() {return fundId;}
    public void setFundId(Long fundId) {this.fundId = fundId;}
    public Float getNetWorth() {return netWorth;}
    public void setNetWorth(Float netWorth) {this.netWorth = netWorth;}
    public String getWorthDate() {return worthDate;}
    public void setWorthDate(String worthDate) {this.worthDate = worthDate;}
    public BigDecimal getDayGrowth() {return dayGrowth;}
    public void setDayGrowth(BigDecimal dayGrowth) {this.dayGrowth = dayGrowth;}
    public String getExpectWorthDate() {return expectWorthDate;}
    public void setExpectWorthDate(String expectWorthDate) {this.expectWorthDate = expectWorthDate;}
    public BigDecimal getExpectWorth() {return expectWorth;}
    public void setExpectWorth(BigDecimal expectWorth) {this.expectWorth = expectWorth;}
    public BigDecimal getExpectGrowth() {return expectGrowth;}
    public void setExpectGrowth(BigDecimal expectGrowth) {this.expectGrowth = expectGrowth;}

}
