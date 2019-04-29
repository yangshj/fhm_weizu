package com.weizu.pojo.integral;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 */
@Alias("orderBean")
public class OrderBean implements Serializable {

    /** id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 订单号 */
    private String orderNo;
    /** 积分数 */
    private Integer integralNum;
    /** 商品数 */
    private Integer commodityNum;
    /** 商品id */
    private Long commodityId;
    /** 商品名称 */
    private String commodityTitle;
    /** 昵称 */
    private String nickName;
    /** 用户名 */
    private String userName;
    /** 兑换状态 1:未兑换 2:已兑换 */
    private Integer exchangeStatus;
    /** 兑换人 */
    private Long exchangeEmp;
    /** 兑换日期 */
    private Date exchangeTime;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date modifyTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Integer getIntegralNum() {
        return integralNum;
    }
    public void setIntegralNum(Integer integralNum) {
        this.integralNum = integralNum;
    }
    public Integer getCommodityNum() {
        return commodityNum;
    }
    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
    }
    public Long getCommodityId() {
        return commodityId;
    }
    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }
    public String getCommodityTitle() {
        return commodityTitle;
    }
    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getExchangeStatus() {
        return exchangeStatus;
    }
    public void setExchangeStatus(Integer exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }
    public Long getExchangeEmp() {
        return exchangeEmp;
    }
    public void setExchangeEmp(Long exchangeEmp) {
        this.exchangeEmp = exchangeEmp;
    }
    public Date getExchangeTime() {
        return exchangeTime;
    }
    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
