package com.weizu.common.enums;


/**
 * 模块
 */
public enum ModuleEnum {

    ADDRESS_BOOK(1, "通讯录"),
    SCHOOL(2, "校园一角");

    private Integer index;
    private String desc;

    private ModuleEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static ModuleEnum getEnumByIndex(Integer index) {
        ModuleEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            ModuleEnum itemEnum = arr[i];
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
