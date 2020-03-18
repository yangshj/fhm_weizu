package com.weizu.task;

import com.alibaba.fastjson.JSON;
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

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private static Logger logger = Logger.getLogger(FundTask.class);
    private static FundNetWorthService fundNetWorthService = null;
    private static FundService fundService = null;
    // 定义锁对象
    private Lock lock = new ReentrantLock();

    @Scheduled(cron = "0 0 9 * * ?") // 每天9点触发
    public void taskCycle() {
        System.out.println("使用SpringMVC框架配置定时任务");
        boolean hasLock = false;
        try{
            hasLock = lock.tryLock(5, TimeUnit.SECONDS);
            if(hasLock){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("基金定时任务执行:" + format.format(new Date()));
                syncFundData(null);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(hasLock){
                lock.unlock();
            }
        }
    }

    /**
     * 同步解析基金数据
     * @param code 指定code，不指定时同步所有
     * @throws Exception
     */
    public static void syncFundData(String code) throws Exception{
        analysisFundData(code);

    }

    /**
     * 异步解析基金数据
     * @param code 指定code，不指定时同步所有
     * @throws Exception
     */
    public static void asyncFundData(final String code) throws Exception{
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    analysisFundData(code);
                }catch (Exception e){
                    logger.error("异步解析基金数据异常:"+code, e);
                }
            }

        };
        t.start();
    }


    private static void analysisFundData(String code) throws Exception{
        if(fundService==null){
            fundService = (FundService) ServiceHelper.getService("fundServiceImpl");
            fundNetWorthService = (FundNetWorthService) ServiceHelper.getService("fundNetWorthServiceImpl");
        }
        FundBean queryParam = new FundBean();
        queryParam.setCode(code);
        List<FundBean> fundList =  fundService.findFundByCondition(queryParam);
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
                    if(!isEquals(item, exitsMap.get(item.getWorthDate()))){
                        // 更新基金信息
                        item.setId(exitsMap.get(item.getWorthDate()).getId());
                        fundNetWorthService.updateFundNetWorth(item);
                        System.out.println("更新基金信息:"+ JSON.toJSONString(item));
                    }
                    continue;
                }
                item.setFundId(fundBean.getId());
                fundNetWorthService.insertFundNetWorth(item);
            }
        }
    }

    // 判断两个基金是否相等
    private static Boolean isEquals(FundNetWorthBean item, FundNetWorthBean exits){
        if(exits==null || exits==null){
            return false;
        }
        Boolean expectWorthDate = false;
        Boolean expectGrowth = false;
        if(item.getExpectWorthDate()!=null && exits.getExpectWorthDate()!=null && item.getExpectWorthDate().equals(exits.getExpectWorthDate())){
            expectWorthDate = true;
        }
        if(item.getExpectGrowth()!=null && exits.getExpectGrowth()!=null && item.getExpectWorth().equals(exits.getExpectWorth())){
            expectGrowth = true;
        }
        return expectWorthDate && expectGrowth;
    }
}
