package com.weizu.pojo.addressBook;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 通讯录
 * @author yangshj
 */
@Alias("addressLookBean")
public class AddressLookBean  implements Serializable{

	private static final long serialVersionUID = 1L;
	/** id */
	private Long id;
	/** 用户名 */
	private String userName;
	/** 手机号 */
	private String mobilePhone;
	/** 办公电话 */
	private String officePhone;
	/** 头像 */
	private String headImage;
	/** 性别 */
	private Integer sex;
	/** 创建时间 */
	private Date createTime;
	/** 备注 */
	private String remark;
	/** 所属姓氏 */
	private Long surnameId;
	/** 小程序Id */
	private Long appId;

	/** 搜索关键字 */
	private String searchKeyWord;

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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getSurnameId() {
		return surnameId;
	}
	public void setSurnameId(Long surnameId) {
		this.surnameId = surnameId;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getSearchKeyWord() {
		return searchKeyWord;
	}
	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}
}
