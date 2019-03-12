package com.weizu.common.enums;

/**
 * 签到类型枚举
 */
public enum SignTypeEnum {

    SIGN_IN(1, "签到"),
    SIGN_OUT(2, "签退");

    private Integer index;
    private String desc;

    private SignTypeEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static SignTypeEnum getEnumByIndex(Integer index) {
        SignTypeEnum[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            SignTypeEnum itemEnum = arr$[i$];
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
