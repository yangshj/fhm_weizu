package com.weizu.pojo.other;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 图文消息
 */
@Alias("imageTextBean")
public class ImageTextBean implements Serializable {


    /** id */
    private Long id;
    /** 员工id */
    private Long userId;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 第一张图片 */
    private String firstPath;
    /** 图片路径，以逗号分隔 */
    private String cloudPath;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date modifyTime;
    /** 小程序Id */
    private Long appId;
    /** 是否有效 */
    private Integer status;

    /** 分页 */
    private Integer startLimit;
    private Integer endLimit;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getFirstPath() {
        return firstPath;
    }
    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }
    public String getCloudPath() {
        return cloudPath;
    }
    public void setCloudPath(String cloudPath) {
        this.cloudPath = cloudPath;
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
    public Long getAppId() {
        return appId;
    }
    public void setAppId(Long appId) {
        this.appId = appId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getStartLimit() {
        return startLimit;
    }
    public void setStartLimit(Integer startLimit) {
        this.startLimit = startLimit;
    }
    public Integer getEndLimit() {
        return endLimit;
    }
    public void setEndLimit(Integer endLimit) {
        this.endLimit = endLimit;
    }


}
