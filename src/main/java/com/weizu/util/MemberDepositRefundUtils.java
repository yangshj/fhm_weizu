package com.weizu.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberDepositRefundUtils {

    private static String file1 = "F:/数据分析/退款单表.txt";
    private static String file2 = "F:/数据分析/押金退款记录.txt";


    public static void main(String[] args) {
        // 原始数据按行读取
        List<String> carfi_refund_list = ReadTxt.readTxtFile(file1);
        // 原始数据按行读取
        List<String> crm_refund_list = ReadTxt.readTxtFile(file2);
        Map<String,String> carfiMap = new HashMap<>();
        for(String str : carfi_refund_list){
            String temp[] = str.split("\t");
            String request_no= temp[3];
            String amount = temp[9];
            if(request_no.equals("request_no")) continue;
            carfiMap.put(request_no, amount);
        }
        Map<String,String> crmMap = new HashMap<>();
        for(String str : crm_refund_list){
            String temp[] = str.split("\t");
            String request_no= temp[1];
            String amount = temp[4];
            if(request_no.equals("request_no")) continue;
            crmMap.put(request_no, amount);
        }
        System.out.println("财务退款条数："+carfiMap.size()+" crm提现条数: "+crmMap.size());
        // 找差异
        for(String str : carfi_refund_list){
            String temp[] = str.split("\t");
            String request_no= temp[3];
            String amount = temp[9];
            String status = temp[27];
            String refundNo = temp[2];
            if(request_no.equals("request_no")) continue;
            if(request_no.length()>37){
                request_no = request_no.substring(0, request_no.indexOf("_1"));
            }
            if(!status.equals("0")){
                continue;
            }
            if(!crmMap.containsKey(request_no)){
                System.out.println(str);
//                System.out.println("'"+refundNo+"',");
                WriteTxt.write("F:/数据分析/已删除退款单.txt", str);

            }
        }
    }

}
