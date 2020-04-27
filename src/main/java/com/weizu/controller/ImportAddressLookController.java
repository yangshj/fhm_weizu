package com.weizu.controller;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.util.PageData;
import com.weizu.pojo.addressBook.AddressLookBean;
import com.weizu.pojo.addressBook.SurNameBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.service.addressLockk.AddressLookService;
import com.weizu.service.addressLockk.SurNameService;
import com.weizu.util.ImportAddressLookUtils;
import com.weizu.util.WriteTxt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/weizu/importAddressLook")
public class ImportAddressLookController extends BaseController {

    @Autowired
    private AddressLookService addressLookService;
    @Autowired
    private SurNameService surNameService;

    private static String writeFile = "F:\\小程序\\导入.txt";

    @RequestMapping(value="/testImport")
    public void testImport(PrintWriter out){
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            List<String> surnameList = ImportAddressLookUtils.getSurNameList();
            List<String> addressLookList = ImportAddressLookUtils.getAddressLookList();
            List<String> userNameMobile = getUserNameListHasMobile(addressLookList);
            WeChatAPPBean bean = new WeChatAPPBean();
            bean.setId(1L);
            List<SurNameBean> surNameBeans = surNameService.getAllSurName(bean);
            // 新增姓氏
            for(int i=0; i<surnameList.size(); i++){
                String surname = surnameList.get(i);
                SurNameBean surNameBean = getSurName(surNameBeans, surname);
                if(surNameBean==null){
                    surNameBean = new SurNameBean();
                    surNameBean.setSurname(surname+"家通信录");
                    surNameBean.setOrderNum(i+1);
                    surNameBean.setAppId(1L);
                    surNameService.insertSurName(surNameBean);
                } else {
                    surNameBean.setOrderNum(i+1);
                    surNameService.updateSurName(surNameBean);
                }
            }
            // 获取所有姓氏
            surNameBeans = surNameService.getAllSurName(bean);
            // 获取所有通讯录
            List<AddressLookBean> list = addressLookService.getAllAddressLook(bean);
            for(SurNameBean temp : surNameBeans){
                String surname = temp.getSurname().substring(0,1);
                List<String> stringList = getAddressLook(addressLookList,surname);
                for(String str : stringList){
                    String[] userInfo = str.split("\t");
                   if(exits(list, userInfo[0])){
                       continue;
                   }
                   AddressLookBean lookBean = new AddressLookBean();
                   lookBean.setUserName(userInfo[0]);
                   if(userInfo.length>1 && userInfo[1].length()==11){
                       lookBean.setMobilePhone(userInfo[1]);
                   } else if(userNameMobile.contains(userInfo[0])) {
                       continue;
                   }
                   lookBean.setAppId(1L);
                   lookBean.setSex(1);
                   lookBean.setSurnameId(temp.getId());
                   addressLookService.inserAddressLook(lookBean);
                   WriteTxt.write(writeFile, JSON.toJSONString(lookBean));
                }
            }
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }

    }

    private List<String> getUserNameListHasMobile(List<String> list){
        List<String> temp = new ArrayList<>();
        for(String str : list){
            String[] infos = str.split("\t");
            if(infos.length>1 && infos[1].length()==11){
                temp.add(infos[0]);
            }
        }
        return temp;
    }

    // 判断用户名是否已经存在
    private boolean exits(List<AddressLookBean> list, String userName){
        for(AddressLookBean bean : list){
            if(bean.getUserName().trim().equals(userName.trim())){
                return true;
            }
        }
        return false;
    }

    // 获取某个姓氏的所有待导入的信息集合
    private List<String> getAddressLook(List<String> addressLookList, String surname){
        List<String> temp = new ArrayList<>();
        for(String str : addressLookList){
            if(str.startsWith(surname)){
                temp.add(str);
            }

        }
        return temp;
    }

    // 获取某个姓氏信息
    private SurNameBean getSurName(List<SurNameBean> list, String surname){
        for(SurNameBean bean : list){
            if(bean.getSurname().startsWith(surname)){
                return bean;
            }
        }
        return null;
    }


}
