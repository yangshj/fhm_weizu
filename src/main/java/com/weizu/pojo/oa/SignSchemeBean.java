package com.weizu.pojo.oa;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到方案
 */
@Alias("signSchemeBean")
public class SignSchemeBean implements Serializable {

    /** id */
    private Long id;
    /** 名称 */
    private String name;
    /** 地址 */
    private String signAddress;
    /** 维度 */
    private Double latitude;
    /** 精度 */
    private Double longitude;
    /** 团队id */
    private Long teamId;
    /** 距离限制 */
    private Integer distanceLimit;
    /** 是否选中 */
    private Integer checked;
    /** 星期一 */
    private Long monday;
    /** 星期二*/
    private Long tuesday;
    /** 星期三 */
    private Long wednesday;
    /** 星期四 */
    private Long thursday;
    /** 星期五 */
    private Long friday;
    /** 星期六 */
    private Long saturday;
    /** 星期日 */
    private Long sunday;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSignAddress() {
        return signAddress;
    }
    public void setSignAddress(String signAddress) {
        this.signAddress = signAddress;
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
    public Long getTeamId() {
        return teamId;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    public Integer getDistanceLimit() {
        return distanceLimit;
    }
    public void setDistanceLimit(Integer distanceLimit) {
        this.distanceLimit = distanceLimit;
    }
    public Long getMonday() {
        return monday;
    }
    public void setMonday(Long monday) {
        this.monday = monday;
    }
    public Long getTuesday() {
        return tuesday;
    }
    public void setTuesday(Long tuesday) {
        this.tuesday = tuesday;
    }
    public Long getWednesday() {
        return wednesday;
    }
    public void setWednesday(Long wednesday) {
        this.wednesday = wednesday;
    }
    public Long getThursday() {
        return thursday;
    }
    public void setThursday(Long thursday) {
        this.thursday = thursday;
    }
    public Long getFriday() {
        return friday;
    }
    public void setFriday(Long friday) {
        this.friday = friday;
    }
    public Long getSaturday() {
        return saturday;
    }
    public void setSaturday(Long saturday) {
        this.saturday = saturday;
    }
    public Long getSunday() {
        return sunday;
    }
    public void setSunday(Long sunday) {
        this.sunday = sunday;
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
    public Integer getChecked() {
        return checked;
    }
    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
