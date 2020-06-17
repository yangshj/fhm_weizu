package com.weizu.common.enums;


/**
 * 模块
 */
public enum ModuleEnum {

    ADDRESS_BOOK(1, "通讯录"),
    SCHOOL(2, "校园一角"),
    NOTICE(3, "公告"),
    INTEGRAL_SHOP(4, "积分商城"),
    RESTAURANT(5, "餐厅"),
    ZERO_GRADE(10, "幼儿园风采"),
    ONE_GRADE(11, "一年级风采"),
    TWO_GRADE(12, "二年级风采"),
    THREE_GRADE(13, "三年级风采"),
    FOUR_GRADE(14, "四年级风采"),
    FIVE_GRADE(15, "五年级风采"),
    SIX_GRADE(16, "六年级风采"),
    SEVEN_GRADE(17, "七年级风采"),
    EIGHT_GRADE(18, "八年级风采"),
    NINE_GRADE(19, "九年级风采"),
    NOTIFY(20, "通知"),
    BROADCAST(21, "广播"),
    ADVERT(22, "广告");


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
