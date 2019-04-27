package com.weizu.pojo.integral;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分
 */
public class IntegralBean implements Serializable {

    private Long id;
    /** 昵称 */
    private String nickName;
    /** 用户id */
    private Long userId;
    /** 小程序id */
    private Long appId;
    /** 积分 */
    private Integer integral;
    /** 模块 */
    private Integer module;
    /** 创建日期 */
    private Date createTime;
    /** 修改日期 */
    private Date modifyTime;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getIntegral() {
        return integral;
    }
    public void setIntegral(Integer integral) {
        this.integral = integral;
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
    public Integer getModule() {
        return module;
    }
    public void setModule(Integer module) {
        this.module = module;
    }
    public Long getAppId() {
        return appId;
    }
    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
