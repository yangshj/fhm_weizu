package com.weizu.pojo.oa;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到记录
 */
@Alias("signRecordBean")
public class SignRecordBean implements Serializable {

    /** id */
    private Long id;
    /** 员工id */
    private Long employeeId;
    /** 团队id */
    private Long teamId;
    /** 维度 */
    private Double latitude;
    /** 精度 */
    private Double longitude;
    /** 位置信息 */
    private String locationInfo;
    /** 签到类型 1签到 2签退 */
    private Integer signType;
    /** 签到结果 1正常 2迟到 3早退 */
    private Integer signResult;
    /** 签到时间 */
    private Date signTime;
    /** 创建时间 */
    private Date createTime;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public Long getTeamId() {
        return teamId;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getLocationInfo() {
        return locationInfo;
    }
    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }
    public Integer getSignType() {
        return signType;
    }
    public void setSignType(Integer signType) {
        this.signType = signType;
    }
    public Integer getSignResult() {
        return signResult;
    }
    public void setSignResult(Integer signResult) {
        this.signResult = signResult;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getSignTime() {
        return signTime;
    }
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }
}
