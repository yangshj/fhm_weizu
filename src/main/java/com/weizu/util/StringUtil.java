package com.weizu.util;




/**
 * 字符串工具集
 */
public class StringUtil {

    public static boolean isEmpty(String str){
        if(str!=null) {
            str = str.trim();
        }
        return str==null || str.equals("");
    }

    // 处理特殊字符
    public static String handleSpecial(String str){
        if(StringUtil.isNotEmpty(str)){
            if(str.contains("?")){
                str = str.replaceAll("\\?","");
                if(str.equals("")){
                    str = "LM";
                }
            }
        }
        return str;
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static String[] splitString(String body, String reg) {
        return body.split(reg);
    }

}
