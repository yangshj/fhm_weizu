package com.weizu.util;

import java.util.List;

public class MemberInsertSQLUtils {

    private static String file = "D:\\租车\\上线脚本\\修复数据\\20200430批量导入会员\\会员信息.txt";

    private static String writeFile = "D:\\租车\\上线脚本\\修复数据\\20200430批量导入会员\\自动生成SQL.txt";

    public static void main(String[] args) {
        List<String> memberStrList = ReadTxt.readTxtFile(file);
        for(String temp : memberStrList){
            String[] memberArray = temp.split("\t");
            createSql(memberArray);
        }
    }

    private static void createSql(String[] memberArray){
        String name = memberArray[0];
        String mobile = memberArray[1];
        String xcardType = "13";
        String xidentitycard = memberArray[3];
        String drivinglicenceno = memberArray[4];
        String xbirthday = memberArray[5];

        String memberSQL = "INSERT INTO [member] ([xpassword], [xname], [xbirthday], [xcardtype],[xidentitycard],[xdrivinglicenceno], [xsex], [xcity], [xmobile], [xoperator_id], [xregtime], [xstatus], [xlevel], [xorigin], [xflag], [xisCanReceiveShortMessage], [xmodifytime], [xnoshow], [credit], [quick_pay_state], [is_hz], [seseame_credit_flag], [xdrivinglicencestatus],[xdrivinglicencedeadline], [xcountrytype],[country_code], [activity])"+
        "VALUES ('fea4ad480f537ec76f70c3f6442a814a2baee013', '"+name+"', "+xbirthday+ ",'"+xcardType+"', '"+xidentitycard+"','"+drivinglicenceno+"','男', 0, '"+mobile+"', 1, '2020-4-3 21:00:00', 10, 1, '12', 0, '1', '2020-4-3 21:00:00', 0, 0, 0, 0, 0, 2, '2022-04-01 21:00:00', 3, '86', 0);";


        String memberAddSQl = "INSERT INTO [T_CRM_MEMBER_ADD] ([MEMBER_ID], [VALID_DATE], [CUR_INTEGRAL], [AVAIL_INTEGRAL], [COST_INTEGRAL], [ACC_INTEGRAL], [VERSION], [OPER_TIME])"+
        "select xid, '2020-4-3 21:00:00', 0, 0, 0, 0, 0, '2020-4-3 21:00:00' from member where xmobile='"+mobile+"';";

        String memberhistorySQL = "INSERT INTO [memberhistory] ([xmember_id], [xname], [xmobile], [xstate], [xemployee_id], [xtime], [credit], [description], [ischange_password], [update_origin])"+
        "select xid, xname, xmobile, xstatus, 1, '2020-4-3 21:00:00', 0, '导入大客户会员', 0, 12 from member where xmobile='"+mobile+"';";

        String businessMemberSQL = "INSERT INTO [business_member] ([xbusiness_id], [xname], [xidentitycard], [xmobile], [xtype], [xstate], [xemployee_id], [xtime], [xmember_id]," +
                " [is_need_approve], [is_admin], [create_emp], [create_time], [modify_emp], [modify_time], [identityCardType]," +
                " [remark], [pay_type])" +
                "select 25378, xname, xidentitycard, xmobile, 1, 1, 1, '2020-4-3 21:00:00', xid, 0, 0, 1, '2020-4-3 21:00:00', 1, '2020-4-3 21:00:00', xcardtype, '导入数据', 1 from member where xmobile='"+mobile+"';";


        WriteTxt.write(writeFile, memberSQL);
        WriteTxt.write(writeFile, memberAddSQl);
        WriteTxt.write(writeFile, memberhistorySQL);
        WriteTxt.write(writeFile, businessMemberSQL);

    }
}
