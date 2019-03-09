package com.weizu.dao.oa;

import com.weizu.pojo.oa.TeamBean;

import java.util.List;

public class GetAllTeamRE {

    /** success/fail */
    private String result;
    /**  团队信息列表  */
    private List<TeamBean> listData;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public List<TeamBean> getListData() {
        return listData;
    }
    public void setListData(List<TeamBean> listData) {
        this.listData = listData;
    }
}
