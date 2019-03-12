package com.weizu.common.enums;

/**
 * 签到结果枚举
 */
public enum SignResultEnum {

    NORMAL(1, "正常"),
    LATE(2, "迟到"),
    LEAVE_EARLY(3, "早退");

    private Integer index;
    private String desc;

    private SignResultEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static SignResultEnum getEnumByIndex(Integer index) {
        SignResultEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            SignResultEnum itemEnum = arr[i];
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
