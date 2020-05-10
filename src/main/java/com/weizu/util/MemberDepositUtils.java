package com.weizu.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员押金退款处理
 */
public class MemberDepositUtils {

    private static String crm_deposit = "F:/押金退款/crm_deposit.txt";
    private static String carpay2v_refund = "F:/押金退款/carpay2v_refund.txt";
    private static String carpay2v_main = "F:/押金退款/carpay2v_main.txt";

    private static String memberRefund = "F:/押金退款/memberRefund.txt";

    public static void main(String[] args) {
        // 原始数据按行读取
        List<String> crm_deposit_list = ReadTxt.readTxtFile(crm_deposit);
//        List<String> carpay2v_refund_list = ReadTxt.readTxtFile(carpay2v_refund);
        List<String> carpay2v_main_list = ReadTxt.readTxtFile(carpay2v_main);
        // 统计会员退款记录差
        // 会员表 <memberId, 退款记录总数>
        Map<String,Integer> crm_deposit_map = new HashMap<>();
        for(String temp : crm_deposit_list){
            String str[] = temp.split("\t");
            String memberId = str[1];
            if(memberId.equals("member_id")) continue;
            String refundNoArray[] = str[17].split(",");
            if (crm_deposit_map.containsKey(memberId)) {
                crm_deposit_map.put(memberId, crm_deposit_map.get(memberId) + refundNoArray.length);
            } else {
                crm_deposit_map.put(memberId, refundNoArray.length);
            }
        }
        // 支付表 <memberId, 退款记录总数>
        Map<String,Integer> carpay2v_main_map = new HashMap<>();
        for(String temp : carpay2v_main_list){
            String str[] = temp.split("\t");
            String memberId = str[4];
            if(memberId.equals("payer")) continue;
            if(carpay2v_main_map.containsKey(memberId)){
                carpay2v_main_map.put(memberId, carpay2v_main_map.get(memberId)+1);
            } else {
                carpay2v_main_map.put(memberId, 1);
            }
        }
        // 比对会员退款记录差
        for(String key : crm_deposit_map.keySet()){
            Integer crm_total = crm_deposit_map.get(key);
            Integer main_total = carpay2v_main_map.get(key);
            if(crm_total!=main_total){
                WriteTxt.write(memberRefund,key + " crm:"+crm_total+" carpay2v:"+main_total);
            }
        }
    }

}
