package com.weizu.task;

import com.fh.util.Logger;
import com.fh.util.ServiceHelper;
import com.weizu.helper.FundHelper;
import com.weizu.pojo.fund.FundBean;
import com.weizu.pojo.fund.FundInfo;
import com.weizu.pojo.fund.FundNetWorthBean;
import com.weizu.service.fund.FundNetWorthService;
import com.weizu.service.fund.FundService;
import com.weizu.util.StringUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基金定时任务
 */
@Component
public class FundTask {

    private Logger logger = Logger.getLogger(this.getClass());
    private FundNetWorthService fundNetWorthService = null;
    private FundService fundService = null;
    // 定义锁对象
    private Lock lock = new ReentrantLock();

    @Scheduled(cron = "0 * */1 * * *") // 间隔每1小时执行
    public void taskCycle() {
        System.out.println("使用SpringMVC框架配置定时任务");
        boolean hasLock = false;
        try{
            hasLock = lock.tryLock(5, TimeUnit.SECONDS);
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


    private void analysisData() throws Exception{
        if(fundService==null){
            fundService = (FundService) ServiceHelper.getService("fundServiceImpl");
            fundNetWorthService = (FundNetWorthService) ServiceHelper.getService("fundNetWorthServiceImpl");
        }
        List<FundBean> fundList =  fundService.findFundByCondition(null);
        if(fundList==null || fundList.size()==0){
            return;
        }
        for(FundBean fundBean : fundList){
            if(StringUtil.isEmpty(fundBean.getCode())){
                continue;
            }
            // 获取基金最新信息
            FundInfo fundInfo = FundHelper.getFundInfo(fundBean.getCode());
            if(fundInfo==null|| fundInfo.getFundBean()==null){
                continue;
            }
            // 更新基本信息
            FundBean update = fundInfo.getFundBean();
            update.setId(fundBean.getId());
            fundService.updateFund(update);

            // 查询已经存在的明细数据
            FundNetWorthBean param = new FundNetWorthBean();
            param.setFundId(fundBean.getId());
            List<FundNetWorthBean> exitsList = fundNetWorthService.findAllFundNetWorthByCondition(param);
            Map<String,FundNetWorthBean> exitsMap = new HashMap<>();
            for(FundNetWorthBean exit : exitsList){
                exitsMap.put(exit.getWorthDate(), exit);

            }
            // 插入明细
            for(FundNetWorthBean item : fundInfo.getItems()){
                if(StringUtil.isEmpty(item.getWorthDate())){
                    continue;
                }
                if(exitsMap.containsKey(item.getWorthDate())){
                    continue;
                }
                item.setFundId(fundBean.getId());
                fundNetWorthService.insertFundNetWorth(item);
            }
        }

    }
}
