package com.weizu.common.enums;

public enum IntegralOperTypeEnum {

    ADD(1, "添加"),
    SUB(2, "消费");

    private Integer index;
    private String desc;

    private IntegralOperTypeEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static IntegralOperTypeEnum getEnumByIndex(Integer index) {
        IntegralOperTypeEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            IntegralOperTypeEnum itemEnum = arr[i];
            if (itemEnum.getIndex().equals(index)) {
                return itemEnum;
            }
        }
        return null;
    }

    public Integer getIndex() {
        return this.index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
