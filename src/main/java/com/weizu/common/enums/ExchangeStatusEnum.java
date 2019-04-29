package com.weizu.common.enums;

/**
 * 模块
 */
public enum ExchangeStatusEnum {
    
    NOT_HAVE(1, "未兑换"),
    ALREADY(2, "已兑换");

    private Integer index;
    private String desc;

    private ExchangeStatusEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static ExchangeStatusEnum getEnumByIndex(Integer index) {
        ExchangeStatusEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            ExchangeStatusEnum itemEnum = arr[i];
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
