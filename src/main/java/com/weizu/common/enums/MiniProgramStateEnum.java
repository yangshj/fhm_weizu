package com.weizu.common.enums;

/**
 * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
 */
public enum  MiniProgramStateEnum {


    FORMAL(1, "正式版"),
    TRIAL(2, "体验版"),
    DEVELOPER(3, "开发版");


    private Integer index;
    private String desc;

    private MiniProgramStateEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static MiniProgramStateEnum getEnumByIndex(Integer index) {
        MiniProgramStateEnum[] arr = values();
        int len = arr.length;
        for(int i = 0; i < len; ++i) {
            MiniProgramStateEnum itemEnum = arr[i];
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
