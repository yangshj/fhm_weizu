package com.weizu.helper;

import com.fh.util.ServiceHelper;
import com.weizu.core.queue.ITimeOutMap;
import com.weizu.core.queue.TimeOutForCreateDateMap;
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

    private static List<WeChatAPPBean> weChatAPPBeanList = new ArrayList<WeChatAPPBean>();
    // key=appId ，value=WeChatAPPBean
    private static ITimeOutMap<String, WeChatAPPBean> timeOutMap = new TimeOutForCreateDateMap<String, WeChatAPPBean>();

    private static WeChatAPPService weChatAPPService = null;

    static {
        timeOutMap.setTimeOut(5 * 60 * 1000); // 5分钟
        timeOutMap.setCustomTimeOutHandler(new ITimeOutMap.ITimeOutHandler<String, WeChatAPPBean>() {
            @Override
            public void timeOut(String key, WeChatAPPBean bean) {
                timeOutMap.remove(key);
            }
        });
    }


    public static WeChatAPPBean getWeChatApp(String appId){
        // 兼容处理
        if(StringUtil.isEmpty(appId)){
            if(timeOutMap.size()==0){
                reload();
            }
            return weChatAPPBeanList.get(0);
        }
        WeChatAPPBean bean = timeOutMap.get(appId);
        if(bean==null){
            reload();
        }
        bean = timeOutMap.get(appId);
        return bean;
    }

    public synchronized static WeChatAPPBean getFirst(){
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
                timeOutMap.put(bean.getAppId(), bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
