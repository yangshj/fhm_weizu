package com.weizu.helper;

import com.weizu.core.queue.ITimeOutMap;
import com.weizu.core.queue.TimeOutForCreateDateMap;

public class TestTimeOut {

    private static ITimeOutMap<String, String> sessionOpenIdMap = new TimeOutForCreateDateMap<String, String>();

    static {
        sessionOpenIdMap.setTimeOut(3000); // 3秒
        sessionOpenIdMap.setCustomTimeOutHandler(new ITimeOutMap.ITimeOutHandler<String, String>() {
            @Override
            public void timeOut(String key, String UserOpenId) {
                sessionOpenIdMap.remove(key);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        sessionOpenIdMap.put("test","test");
        int i=0;

        while (true){
            Thread.sleep(1000);
            if(i<10){
                sessionOpenIdMap.put("test-" + i++, "test");
            }
            System.out.println(sessionOpenIdMap.size());
        }
    }
}
