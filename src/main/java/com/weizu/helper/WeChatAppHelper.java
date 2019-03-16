package com.weizu.helper;

import com.fh.util.ServiceHelper;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.service.addressLockk.WeChatAPPService;
import com.weizu.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 工具类

 * @since : 2019/3/16 11:35:18
 **/
public class WeChatAppHelper {

    private static Map<String, WeChatAPPBean> map = new HashMap<String, WeChatAPPBean>();
    private static List<WeChatAPPBean> weChatAPPBeanList = new ArrayList<WeChatAPPBean>();

    private static WeChatAPPService weChatAPPService = null;

    public static WeChatAPPBean getWeChatApp(String appId){
        if(StringUtil.isEmpty(appId)){
            return null;
        }
        WeChatAPPBean bean = map.get(appId);
        if(bean==null){
            reload();
        }
        bean = map.get(appId);
        return bean;
    }

    public static WeChatAPPBean getFirst(){
        return weChatAPPBeanList.get(0);
    }

    /**
     * 重新加载
     */
    private synchronized static void reload(){
        if(weChatAPPService==null){
            weChatAPPService = (WeChatAPPService) ServiceHelper.getService("weChatAPPServiceImpl");
        }
        try {
            List<WeChatAPPBean> list = weChatAPPService.getAllWeChatApp();
            weChatAPPBeanList = list;
            for(WeChatAPPBean bean : list){
                map.put(bean.getAppId(), bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
