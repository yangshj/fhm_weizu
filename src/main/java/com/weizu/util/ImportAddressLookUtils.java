package com.weizu.util;

import java.util.*;

public class ImportAddressLookUtils {

    private static String file = "F:\\小程序\\西玉曹通讯录.txt";

    private static String writeFile = "F:\\小程序\\西玉曹通讯录_Distinct.txt";

    /**
     * 通讯录集合
     * @return
     */
    public static List<String> getAddressLookList(){
        List<String> memberStrList = ReadTxt.readTxtFile(file,true);
        return memberStrList;
    }

    /**
     * 姓氏集合
     * @return
     */
    public static List<String> getSurNameList(){
        List<String> memberStrList = getAddressLookList();
        List<String> surnameList = new ArrayList<>(); // 姓氏集合
        for(String temp : memberStrList){
            String surname = temp.substring(0,1);
            if(!surnameList.contains(surname)){
                surnameList.add(surname);
                System.out.println(surname);
            }
        }
        return surnameList;
    }

    public static void main(String[] args) {
        List<String> memberStrList = getAddressLookList();
        for (String temp : memberStrList){
            WriteTxt.write(writeFile, temp);
        }
    }

}
