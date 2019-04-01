package com.weizu.pojo.system;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限
 */
@Alias("wechatRightsBean")
public class WechatRightsBean implements Serializable {

    /** id */
    private Long id;
    /** 模块 */
    private Integer module;
    /** 小程序Id */
    private Long appId;
    /** 权限 */
    private String rights;
    /** 创建时间 */
    private Date createTime;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getRights() {
        return rights;
    }
    public void setRights(String rights) {
        this.rights = rights;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
