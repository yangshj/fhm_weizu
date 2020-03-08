package com.weizu.helper;

import com.fh.util.mail.SimpleMailSender;
import com.weizu.pojo.fund.FundInfo;
import com.weizu.pojo.fund.FundNetWorthBean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class FundEmailHelper {

    // 队列
    private static LinkedBlockingQueue<FundInfo> queue = new LinkedBlockingQueue<FundInfo>(100);
    private static Thread thread = null;

    // 添加至队列
    public static void addQueue(FundInfo fundInfo){
        System.out.println("加入队列："+fundInfo);
        queue.add(fundInfo);
    }

    static{
        init();
    }

    public static void init() {
        thread = new Thread(){
            @Override
            public void run() {
                while(!this.isInterrupted()){
                    try {
                        FundInfo fundInfo = queue.take();
                        System.out.println("消费队列："+fundInfo);
                        analysisFundInfo(fundInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    /**
     * 今天的净值低于一年内的最低时，发送邮件
     */
    public static void analysisFundInfo(FundInfo fundInfo){
        if(fundInfo==null|| fundInfo.getFundBean()==null || fundInfo.getItems()==null || fundInfo.getItems().size()==0){
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        Date today = new Date();
        Date lastYear = new Date(today.getTime()-360*24*60*60*1000L);
        String lastYearStr = format.format(lastYear);
        // 先排序
        Collections.sort(fundInfo.getItems(), new Comparator<FundNetWorthBean>(){
            @Override
            public int compare(FundNetWorthBean o1, FundNetWorthBean o2) {
                return o1.getWorthDate().compareTo(o2.getWorthDate());
            }
        });
        // 最后一天
        FundNetWorthBean todayNetWorthBean = fundInfo.getItems().remove(fundInfo.getItems().size()-1);
        // 最近一年的最低
        BigDecimal lowestNewWorth = null;
        // 筛选最近一年的数据
        Iterator<FundNetWorthBean> iterator = fundInfo.getItems().iterator();
        while(iterator.hasNext()){
            FundNetWorthBean item = iterator.next();
            if(item.getWorthDate().compareTo(lastYearStr)<=0){
                iterator.remove();
                continue;
            }
            if(lowestNewWorth==null){
                lowestNewWorth = item.getNetWorth();
            } else if(lowestNewWorth.compareTo(item.getNetWorth())>0) {
                lowestNewWorth = item.getNetWorth();
            }
        }
        // 最后一天的净值
        BigDecimal todayNetWorth = todayNetWorthBean.getNetWorth();
        if(todayNetWorth.compareTo(lowestNewWorth)<=0){
            String content = "基金编码："+fundInfo.getFundBean().getCode()+"\t基金名称："+fundInfo.getFundBean().getName()+"\r\n";
            content += "当前净值:"+todayNetWorth+"低于最近一年净值:"+lowestNewWorth;
            sendEmail(content);
        }
    }

    private static void sendEmail(String content) {
        String email = "shuaijun.yang@aliyun.com";
        String title = "基金变动提醒";
        //调用发送邮件函数
        String sendEmail = "18910970262@163.com";
        String sendSTMP = "smtp.163.com";
        String sendPort = "465";
        String sendPwd = "Yangshj123";
        try{
            boolean success = SimpleMailSender.sendEmail(sendSTMP, sendPort, sendEmail, sendPwd, email, title, content, "1");
            System.out.println("发送邮件:"+success);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
