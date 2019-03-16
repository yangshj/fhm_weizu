package com.weizu.pojo.addressBook;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Description:
 *
 * @since : 2019/3/16 10:34:05
 **/
@Alias("weChatAPPBean")
public class WeChatAPPBean implements Serializable {

    /** 主键 */
    private Long id;
    /** 小程序名称 */
    private Long name;
    /** 小程序ID */
    private String appId;
    /** 小程序密钥 */
    private String appSecret;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getName() {
        return name;
    }
    public void setName(Long name) {
        this.name = name;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getAppSecret() {
        return appSecret;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
