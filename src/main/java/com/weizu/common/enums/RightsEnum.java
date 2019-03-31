package com.weizu.common.enums;

public enum RightsEnum {

    ACCESS(1, "访问权限"),
    MODIFY_OWN(2, "修改权限-自己"),
    MODIFY_ALL(3, "修改权限-所有"),
    MANAGER(4, "管理员权限");

    private Integer index;
    private String desc;

    private RightsEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static RightsEnum getEnumByIndex(Integer index) {
        RightsEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            RightsEnum itemEnum = arr[i];
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
