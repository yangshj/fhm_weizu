package com.weizu.pojo;

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
	/** 性别 */
	private Integer sex;
	/** 创建时间 */
	private Date createTime;
	/** 查询条件-不对应数据库 */
	private Long surnameId;
	
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
}
