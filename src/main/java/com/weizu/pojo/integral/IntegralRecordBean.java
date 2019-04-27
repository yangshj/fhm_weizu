package com.weizu.pojo.integral;

import com.weizu.common.enums.IntegralOperTypeEnum;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

    /** 操作类型字符串 */
    private String operTypeStr;
    private String createTimeStr;

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
    public String getOperTypeStr() {
        if(operType!=null){
            return IntegralOperTypeEnum.getEnumByIndex(operType).getDesc();
        }
        return null;
    }
    public void setOperTypeStr(String operTypeStr) {
        this.operTypeStr = operTypeStr;
    }
    public String getCreateTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(createTime);
    }
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}