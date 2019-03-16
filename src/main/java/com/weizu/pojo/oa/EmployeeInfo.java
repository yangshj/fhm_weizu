package com.weizu.pojo.oa;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 员工信息
 */
@Alias("employeeInfo")
public class EmployeeInfo implements Serializable {

    /** 员工id */
    private Long employeeId;
    /** 用户id */
    private Long userId;
    /** 微信昵称 */
    private String nickName;
    /** 邮箱 */
    private String email;
    /** 性别 */
    private Integer sex;
    /** 管理员权限 */
    private String managerRights;
    /** 员工名称 */
    private String userName;
    /** 电话 */
    private String mobile;
    /** 头像 */
    private String headImage;
    /** 备注 */
    private String remark;

    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getHeadImage() {
        return headImage;
    }
    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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
    public String getManagerRights() {
        return managerRights;
    }
    public void setManagerRights(String managerRights) {
        this.managerRights = managerRights;
    }
}
