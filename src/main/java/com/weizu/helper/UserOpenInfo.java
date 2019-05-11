package com.weizu.helper;

import com.weizu.pojo.oa.BaseRE;

import java.util.Date;

public class UserOpenInfo extends BaseRE {
	
	private String sessionId;
	private String openId;
	/** 用户id */
	private Long userId;
	/** 第一次放入的时间 */
	private Date createTime;
	/** 最后一次访问的时间 */
	private Date updateTime;
	/** 管理员 */
	private Boolean manager=false;
	/** 高级管理员 */
	private Boolean admin = false;
	/** 超级admin */
	private Boolean superAdmin = false;



	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Boolean getManager() {
		return manager;
	}
	public void setManager(Boolean manager) {
		this.manager = manager;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Boolean getSuperAdmin() {
		return superAdmin;
	}
	public void setSuperAdmin(Boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
}
