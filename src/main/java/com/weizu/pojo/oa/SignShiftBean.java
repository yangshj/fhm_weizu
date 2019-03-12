package com.weizu.pojo.oa;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到方案
 */
@Alias("signShiftBean")
public class SignShiftBean implements Serializable {

    /** id */
    private Long id;
    /** 名称 */
    private String name;
    /** 团队id */
    private Long teamId;
    /** 上班时间 */
    private Date startTime;
    /** 下班时间 */
    private Date endTime;
    /** 工作时长 */
    private Integer workHour;
    /** 上班打卡限制 */
    private Date startLimit;
    /** 下班打卡限制 */
    private Date endLimit;
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
    public Long getTeamId() {
        return teamId;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Integer getWorkHour() {
        return workHour;
    }
    public void setWorkHour(Integer workHour) {
        this.workHour = workHour;
    }
    public Date getStartLimit() {
        return startLimit;
    }
    public void setStartLimit(Date startLimit) {
        this.startLimit = startLimit;
    }
    public Date getEndLimit() {
        return endLimit;
    }
    public void setEndLimit(Date endLimit) {
        this.endLimit = endLimit;
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
