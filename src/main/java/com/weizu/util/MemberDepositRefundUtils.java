package com.weizu.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberDepositRefundUtils {

    private static String file1 = "E:/���ݷ���/�˿��.txt";
    private static String file2 = "E:/���ݷ���/Ѻ���˿��¼.txt";


    public static void main(String[] args) {
        // ԭʼ���ݰ��ж�ȡ
        List<String> carfi_refund_list = ReadTxt.readTxtFile(file1);
        // ԭʼ���ݰ��ж�ȡ
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
        System.out.println("�����˿�������"+carfiMap.size()+" crm��������: "+crmMap.size());
        // �Ҳ���
        for(String str : carfi_refund_list){
            String temp[] = str.split("\t");
            String request_no= temp[3];
            String amount = temp[9];
            if(request_no.equals("request_no")) continue;
            if(request_no.length()>37){
                request_no = request_no.substring(0, request_no.indexOf("_1"));
//                System.out.println("request:"+request_no);
            }
            if(!crmMap.containsKey(request_no)){
//                System.out.println("request\t"+request_no + "\tamount\t"+amount);
                System.out.println(str);
            }
        }
    }

}
