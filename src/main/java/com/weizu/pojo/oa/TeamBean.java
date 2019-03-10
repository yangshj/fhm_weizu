package com.weizu.pojo.oa;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 团队
 */
@Alias("teamBean")
public class TeamBean implements Serializable {

     /** 用户id */
    private Long id;
    /** 团队名称 */
    private String name;
    /** 邀请码 */
    private String  invitationCode;
    /** 团队描述 */
    private String teamInfo;
    /** 地址 */
    private String address;
    /** 维度 */
    private Double latitude;
    /** 精度 */
    private Double longitude;
    /** 联系人 */
    private String contact;
    /** 联系电话 */
    private String contactPhone;
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
    public String getInvitationCode() {
        return invitationCode;
    }
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    public String getTeamInfo() {
        return teamInfo;
    }
    public void setTeamInfo(String teamInfo) {
        this.teamInfo = teamInfo;
    }
}
