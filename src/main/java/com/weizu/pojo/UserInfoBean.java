package com.weizu.pojo;

import java.io.Serializable;
import java.util.Date;

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
	/** 权限 */
	private String rights;
	/** 管理员权限 */
	private String managerRights;
	/** 是否为admin 0否1是 */
	private Integer admin;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date modifyTime;
	/**  审批权限页面用--不对应数据库 */
	
	
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
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public String getManagerRights() {
		return managerRights;
	}
	public void setManagerRights(String managerRights) {
		this.managerRights = managerRights;
	}
	public Integer getAdmin() {
		return admin;
	}
	public void setAdmin(Integer admin) {
		this.admin = admin;
	}
}
