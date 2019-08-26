package com.weizu.pojo.addressBook;

import com.weizu.pojo.oa.BaseRE;

public class GetAddressLookByIdRE extends BaseRE {

    private AddressLookBean bean;

    public AddressLookBean getBean() {
        return bean;
    }
    public void setBean(AddressLookBean bean) {
        this.bean = bean;
    }
}
