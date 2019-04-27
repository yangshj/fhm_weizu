package com.weizu.pojo.integral;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分详情
 */
public class IntegralRecordBean implements Serializable {

    private Long id;
    /** 用户id */
    private Long integralId;
    /** 积分 */
    private Integer integral;
    /** 操作类型 1:添加，2:消费 */
    private Integer operType;
    /** 创建日期 */
    private Date createTime;
    /** 备注 */
    private String remark;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIntegralId() {
        return integralId;
    }
    public void setIntegralId(Long integralId) {
        this.integralId = integralId;
    }
    public Integer getIntegral() {
        return integral;
    }
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
    public Integer getOperType() {
        return operType;
    }
    public void setOperType(Integer operType) {
        this.operType = operType;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

}