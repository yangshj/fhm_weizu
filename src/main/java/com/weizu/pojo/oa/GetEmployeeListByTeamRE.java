package com.weizu.pojo.oa;

import java.util.List;

/**
 * 获取团队下的员工列表
 */
public class GetEmployeeListByTeamRE extends BaseRE {

    private List<EmployeeInfo> listData;

    public List<EmployeeInfo> getListData() {
        return listData;
    }
    public void setListData(List<EmployeeInfo> listData) {
        this.listData = listData;
    }
}
