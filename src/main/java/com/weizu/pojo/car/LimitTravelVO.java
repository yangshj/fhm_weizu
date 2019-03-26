package com.weizu.pojo.car;

import java.util.Date;

/**
 * Description: 限行
 *
 * @since : 2019/3/26 12:41:13
 **/
public class LimitTravelVO {

    /** 限行日期 */
    private Date limitDate;
    /** 天描述: 今天、明天、后天 */
    private String dayDesc;
    /** 限行描述 */
    private String limitDesc;
    /** 城市名称 */
    private String cityName;
    /** 城市id */
    private String cityId;

    public Date getLimitDate() {
        return limitDate;
    }
    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }
    public String getLimitDesc() {
        return limitDesc;
    }
    public void setLimitDesc(String limitDesc) {
        this.limitDesc = limitDesc;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityId() {
        return cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    public String getDayDesc() {
        return dayDesc;
    }
    public void setDayDesc(String dayDesc) {
        this.dayDesc = dayDesc;
    }
}
