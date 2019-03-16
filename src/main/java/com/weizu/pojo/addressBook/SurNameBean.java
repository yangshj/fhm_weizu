package com.weizu.pojo.addressBook;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("surNameBean")
public class SurNameBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Long id;
	/** 姓氏 */
	private String surname;
	/** 创建时间 */
	private Date createTime;
	/** 小程序Id */
	private Long appId;



	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
