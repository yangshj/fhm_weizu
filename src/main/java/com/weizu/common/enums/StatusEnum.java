package com.weizu.common.enums;

public enum StatusEnum {

    EFFECTIVE(1, "有效"),
    INVALID(0, "无效");

    private Integer index;
    private String desc;

    private StatusEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static StatusEnum getEnumByIndex(Integer index) {
        StatusEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            StatusEnum itemEnum = arr[i];
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
