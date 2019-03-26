package com.weizu.helper;

import com.alibaba.fastjson.JSON;
import com.weizu.pojo.car.LimitTravelVO;
import com.weizu.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 限行
 *
 * @since : 2019/3/26 12:38:56
 **/
public class LimitTravelHelper {

    // 存储限行数据<cityName, List<今天，明天，后天>>
    private static Map<String, List<LimitTravelVO>> limitNumMap = new HashMap<String, List<LimitTravelVO>>();
    private static String URL = "https://wx.hbgajg.com/wap/wfcx/xianxing";

    static {
        init();
    }


    public static List<LimitTravelVO> getLimitTravel(String cityName){
        if(cityName==null || !limitNumMap.containsKey(cityName)){
            return null;
        }
        try {
            List<LimitTravelVO> list = limitNumMap.get(cityName);
            // 校验是否过期
            LimitTravelVO first = list.get(0);
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
            String todayStr = dateFormat.format(new Date());
            Date today = dateFormat.parse(todayStr);
            // 已经过期
            if(today.getTime()>first.getLimitDate().getTime()){
                System.out.println("过期重新加载……");
                reload();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return limitNumMap.get(cityName);
    }


    private static synchronized void reload(){
        System.out.println("刷新限号规则……");
        limitNumMap.clear();
        init();
    }

    private static void init(){
        try {
            Map<String,String> map = new HashMap<String,String>();
            Document doc = Jsoup.connect(URL).get();
            Elements out = doc.getElementsByClass("out");
            Document outDoc = Jsoup.parse(out.toString());
            Elements xq = outDoc.getElementsByClass("xq");
            Document xqDc = Jsoup.parse(xq.toString());
            // 日期
            String dateDesc = xqDc.text().substring(0, 10);
            dateDesc = dateDesc.replace("年","-");
            dateDesc = dateDesc.replace("月","-");
            dateDesc = dateDesc.replace("日","");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateDesc);
            // 左侧表格解析
            Elements areabox = doc.getElementsByClass("areabox");
            Document areaboxDoc = Jsoup.parse(areabox.toString());
            Elements tableCityTrs = areaboxDoc.select(".xxhz");
            for(Element element : tableCityTrs){
                String cityId = element.attr("data-cityid");
                String th = element.html();
                String cityName = th.replace("<th>","").replace("</th>","");
                System.out.println("cityName: "+cityName+ " cityId:"+cityId);
                map.put(cityId, cityName);
            }
            // 右侧表格解析
            Elements xx = doc.getElementsByClass("xx");
            Document xxDoc = Jsoup.parse(xx.toString());
            Elements tableDateTrs = xxDoc.select(".xxhz");
            for(Element element : tableDateTrs){
                String cityId = element.attr("data-cityid");
                String cityName = map.get(cityId);
                if(cityName==null){
                    continue;
                }
                List<LimitTravelVO> list = new ArrayList<LimitTravelVO>();
                // 包含三个td
                Elements tds =  element.select("td");
                // 遍历 td
                for(int i=0; i<tds.size(); i++){
                    String desc = tds.get(i).html();
                    // 特殊处理 <font color="gray">不限号</font>
                    if(desc.contains("<")){
                        desc = desc.replace("<font color=\"gray\">","");
                        desc = desc.replace("</font>","");
                    }
                    LimitTravelVO vo = new LimitTravelVO();
                    vo.setCityId(cityId);
                    vo.setCityName(cityName);
                    vo.setLimitDesc(desc);
                    vo.setLimitDate(DateUtil.dayAddNum(date, i));
                    list.add(vo);
                }
                limitNumMap.put(cityName, list);
            }
            System.out.println("初始化完成: "+JSON.toJSON(limitNumMap));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        List<LimitTravelVO> list = getLimitTravel("北京");
        System.out.println("北京: "+JSON.toJSON(list));
    }
}
