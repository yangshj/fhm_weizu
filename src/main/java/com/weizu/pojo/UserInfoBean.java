package com.weizu.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("userInfoBean")
public class UserInfoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Long id;
	/** 用户头像url */
	private String avatarUrl;
	/** 城市 */
	private String city;
	/** 省份 */
	private String province;
	/** 国家 */
	private String country;
	/** 性别 */
	private Integer gender;
	/** 语言 */
	private String language;
	/** 昵称 */
	private String nickName;
	/** 唯一标示 */
	private String openId;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
}
