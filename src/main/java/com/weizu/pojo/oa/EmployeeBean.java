package com.weizu.pojo.oa;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工
 */
@Alias("employeeBean")
public class EmployeeBean implements Serializable {

    /** 员工id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 微信昵称 */
    private String nickName;
    /** 员工名称 */
    private String name;
    /** 电话 */
    private String mobile;
    /** 邮箱 */
    private String email;
    /** 性别 */
    private Integer sex;
    /** 管理员权限 */
    private String managerRights;
    /** 备注 */
    private String remark;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date modifyTime;


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
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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
    public String getManagerRights() {
        return managerRights;
    }
    public void setManagerRights(String managerRights) {
        this.managerRights = managerRights;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
