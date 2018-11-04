package com.weizu.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ReadTxt {
	
	/**
	 * 读取文本文件，返回List类型
	 * @param filePath 文件路径
	 * @return list<每行数据>
	 */
	public static List<String> readTxtFile(String filePath) {
		List<String> list = new ArrayList<String>();
		try {
			String encoding = "utf8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					list.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		String file = "C:/Users/lenovo/Desktop/数据分析结果.txt";
		 List<String> list = readTxtFile(file);
		 Map<String,Integer> map = new HashMap<String,Integer>();
		 for(String temp : list){
			 if(temp!=null && !temp.trim().equals("")){
				 if(!map.containsKey(temp)){
					 map.put(temp, 1);
				 } else  {
					 map.put(temp, map.get(temp)+1);
				 }
			 }
		 }
		 Iterator<Entry<String, Integer>> it = map.entrySet().iterator();
		 while(it.hasNext()){
			 Entry<String,Integer> en = it.next();
			 System.out.println(en.getKey()+"\t"+en.getValue());
		 }
	}
	
}
