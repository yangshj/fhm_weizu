package com.weizu.helper;

import com.fh.util.ServiceHelper;
import com.weizu.core.queue.ITimeOutMap;
import com.weizu.core.queue.TimeOutForCreateDateMap;
import com.weizu.pojo.system.WechatRightsBean;
import com.weizu.service.system.WeChatRightsService;

import java.util.List;

public class WeChatRightsHelper {

    // <appId+module,wechatRightsBean>
    private static ITimeOutMap<String, WechatRightsBean> rightsMap = new TimeOutForCreateDateMap<String, WechatRightsBean>();

    private static WeChatRightsService weChatRightsService = null;

    static {
        rightsMap.setTimeOut(10 * 60 * 1000); // 10分钟
        rightsMap.setCustomTimeOutHandler(new ITimeOutMap.ITimeOutHandler<String, WechatRightsBean>() {
            @Override
            public void timeOut(String key, WechatRightsBean UserOpenId) {
                rightsMap.remove(key);
            }
        });
    }

    public static WechatRightsBean getWeChatRights(String appId, String module){
        WechatRightsBean wechatRightsBean = rightsMap.get(appId+module);
        if(wechatRightsBean==null){
            reload();
        }
        return wechatRightsBean;
    }

    /**
     * 重新加载
     */
    private synchronized static void reload(){
        if(weChatRightsService==null){
            weChatRightsService = (WeChatRightsService) ServiceHelper.getService("weChatRightsServiceImpl");
        }
        try {
            List<WechatRightsBean> list = weChatRightsService.getAllWeChatRights();
            for(WechatRightsBean bean : list){
                rightsMap.put(bean.getAppId()+bean.getModule()+"", bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
