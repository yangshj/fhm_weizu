package com.weizu.task;

import com.fh.util.Logger;
import com.weizu.service.fund.FundNetWorthService;
import com.weizu.service.fund.FundService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基金定时任务
 */
@Component
public class FundTask {

    private Logger logger = Logger.getLogger(this.getClass());
    private FundNetWorthService fundNetWorthService;
    private FundService fundService;
    // 定义锁对象
    private Lock lock = new ReentrantLock();

    @Scheduled(cron = "0 0 */1 * * *") // 间隔每1小时执行
    public void taskCycle() {
        System.out.println("使用SpringMVC框架配置定时任务");
        boolean hasLock = false;
        try{
            hasLock = lock.tryLock();
            if(hasLock){
                analysisData();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(hasLock){
                lock.unlock();
            }
        }
    }


    private void analysisData(){

    }
}
