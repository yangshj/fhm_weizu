package com.weizu.common.enums;

/**
 * 积分模块枚举
 */
public enum IntegralModuleEnum {

    SYSTEM(1, "系统积分"),
    SUPERMARKET(2, "超市模块");

    private Integer index;
    private String desc;

    private IntegralModuleEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static IntegralModuleEnum getEnumByIndex(Integer index) {
        IntegralModuleEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            IntegralModuleEnum itemEnum = arr[i];
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
