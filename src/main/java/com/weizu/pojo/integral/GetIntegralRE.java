package com.weizu.pojo.integral;

import com.weizu.pojo.oa.BaseRE;

import java.util.List;

public class GetIntegralRE extends BaseRE {

    /** 积分 */
    private IntegralBean integralBean;
    /** 积分明细 */
    private List<IntegralRecordBean> list;


    public IntegralBean getIntegralBean() {
        return integralBean;
    }
    public void setIntegralBean(IntegralBean integralBean) {
        this.integralBean = integralBean;
    }
    public List<IntegralRecordBean> getList() {
        return list;
    }
    public void setList(List<IntegralRecordBean> list) {
        this.list = list;
    }
}
