package com.weizu.common.enums;

public enum CheckedEnum {

    NO_CHECKED(0, "未选中"),
    CHECKED(1, "选中");

    private Integer index;
    private String desc;

    private CheckedEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static CheckedEnum getEnumByIndex(Integer index) {
        CheckedEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            CheckedEnum itemEnum = arr[i];
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
