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
    /** 距离 */
    private Double distance;
    /** 位置信息 */
    private String locationInfo;
    /** 签到类型 1签到 2签退 */
    private Integer signType;
    /** 签到结果 1正常 2迟到 3早退 */
    private Integer signResult;
    /** 签到天 */
    private String signDay;
    /** 签到时间 */
    private Date signTime;
    /** 创建时间 */
    private Date createTime;
    /** 小程序Id */
    private Long appId;

    /** 查询参数不对应数据库 */
    private Date queryStartTime;
    private Date queryEndTime;

    /** 打卡时间对应页面显示  MM-dd HH:mm */
    private String signTimeStr;


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
    public String getSignDay() {
        return signDay;
    }
    public void setSignDay(String signDay) {
        this.signDay = signDay;
    }
    public Date getQueryStartTime() {
        return queryStartTime;
    }
    public void setQueryStartTime(Date queryStartTime) {
        this.queryStartTime = queryStartTime;
    }
    public Date getQueryEndTime() {
        return queryEndTime;
    }
    public void setQueryEndTime(Date queryEndTime) {
        this.queryEndTime = queryEndTime;
    }
    public String getSignTimeStr() {
        return signTimeStr;
    }
    public void setSignTimeStr(String signTimeStr) {
        this.signTimeStr = signTimeStr;
    }
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public Long getAppId() {
        return appId;
    }
    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
