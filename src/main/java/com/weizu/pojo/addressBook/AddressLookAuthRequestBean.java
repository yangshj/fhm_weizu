package com.weizu.pojo.addressBook;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户请求--用于通讯录权限审批
 */
@Alias("addressLookAuthRequestBean")
public class AddressLookAuthRequestBean  implements Serializable {

    /** 用户id */
    private Long userId;
    /** 用户昵称 */
    private String nickName;
    /** 请求信息 */
    private String requestInfo;
    /** 修改前信息 */
    private String beforeInfo;
    /** 请求分配权限的姓氏 */
    private String surname;
    /** 审批状态 */
    private Integer status;
    /** 创建日期 */
    private Date createTime;
    /** 修改日期 */
    private Date modifyTime;
    /** 小程序Id */
    private Long appId;



    public Long getAppId() {
        return appId;
    }
    public void setAppId(Long appId) {
        this.appId = appId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getRequestInfo() {
        return requestInfo;
    }
    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
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
    public String getBeforeInfo() {
        return beforeInfo;
    }
    public void setBeforeInfo(String beforeInfo) {
        this.beforeInfo = beforeInfo;
    }
}
