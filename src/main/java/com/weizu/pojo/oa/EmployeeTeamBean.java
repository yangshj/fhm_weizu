package com.weizu.pojo.oa;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 员工团队关系表
 */
@Alias("employeeBean")
public class EmployeeTeamBean implements Serializable {

    /** id */
    private Long id;
    /** 员工id */
    private Long employeeId;
    /** 团队id */
    private Long teamId;
    /** 是否选中 0未选中 1选中 */
    private Integer checked;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public Long getTeamId() {
        return teamId;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    public Integer getChecked() {
        return checked;
    }
    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
