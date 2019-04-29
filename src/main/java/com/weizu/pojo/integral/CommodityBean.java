package com.weizu.pojo.integral;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品
 */
@Alias("commodityBean")
public class CommodityBean implements Serializable {

    /** id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 积分数 */
    private Integer integralNum;
    /** 商品数 */
    private Integer commodityNum;
    /** 图片路径，以逗号分隔 */
    private String cloudPath;
    /** 第一张图片 */
    private String firstPath;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date modifyTime;
    /** 所属模块 ModuleEnum */
    private Integer module;
    /** 小程序Id */
    private Long appId;
    /** 是否有效 */
    private Integer status;

    /** 分页 */
    private Integer startLimit;
    private Integer endLimit;
    /** 缩写 */
    private String titleAb;
    private String contentAb;


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
    public Integer getIntegralNum() {
        return integralNum;
    }
    public void setIntegralNum(Integer integralNum) {
        this.integralNum = integralNum;
    }
    public Integer getCommodityNum() {
        return commodityNum;
    }
    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
    }

    public String getCloudPath() {
        return cloudPath;
    }

    public void setCloudPath(String cloudPath) {
        this.cloudPath = cloudPath;
    }

    public String getFirstPath() {
        return firstPath;
    }
    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
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
    public String getTitleAb() {
        return titleAb;
    }
    public void setTitleAb(String titleAb) {
        this.titleAb = titleAb;
    }
    public String getContentAb() {
        return contentAb;
    }
    public void setContentAb(String contentAb) {
        this.contentAb = contentAb;
    }
}
