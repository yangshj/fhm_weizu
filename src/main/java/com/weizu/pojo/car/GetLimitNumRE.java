package com.weizu.pojo.car;

import com.weizu.pojo.oa.BaseRE;

import java.util.List;


public class GetLimitNumRE extends BaseRE{

    private List<LimitTravelVO> list;


    public List<LimitTravelVO> getList() {
        return list;
    }
    public void setList(List<LimitTravelVO> list) {
        this.list = list;
    }
}
