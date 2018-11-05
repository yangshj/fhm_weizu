package com.weizu.common.xml.impl;

import com.weizu.common.xml.IReadXML;
import com.weizu.common.xml.IXMLParse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



/**
 * xml文件查找
 * @author scc
 *
 */
public class ReadXML implements IReadXML {
	private final static String DefaultXmlName = "mod.xml";
	private String xmlName;
	@Override
	public List<IXMLParse> readModXML() throws IOException {
		return readXML(DefaultXmlName);
	}
	@Override
	public List<IXMLParse> readXML(String xmlName) throws IOException {
		this.xmlName = xmlName;
		return getXMLFiles();
	}
	public List<IXMLParse> getXMLFiles() throws IOException{
		List<InputStream> fileList=new ArrayList<InputStream>();
		List<IXMLParse> xps=new ArrayList<IXMLParse>();

		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		File file;
		try {
			file = new File(url.toURI());
			List<InputStream> streams=getfiles(fileList, file);//file.getAbsolutePath());
			for(InputStream is:streams){
				xps.add(XMLFactory.getByInputStream(is));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return xps;
	}
	/**
	 * 递归目录查找文件
	 * @param filelList 文件对象集合
	 * @param root 根目录
	 * @return
	 * @throws IOException 
	 */
	private List<InputStream> getfiles(List<InputStream> filelList,File root) throws IOException{
//		File root = new File(root);
		File[] files = root.listFiles();
		if(null != files)
		for (File file : files) {
			if (file.isDirectory()) {
				getfiles(filelList,file);
			} else {
				if(file.getName().equals(xmlName)){
					filelList.add(new FileInputStream(file));
					System.out.println("file:"+file.getAbsolutePath());
				}else if(file.getName().endsWith(".jar")){
					getInputStream(file);
				}
			}
		}
		return filelList;
	}
	/**
	 * 转换成流的形式
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private InputStream getInputStream(File file) throws IOException {
		JarFile jf = new JarFile(file);
		InputStream is=null;
		Enumeration<JarEntry> e = jf.entries();
		while (e.hasMoreElements()) {
			JarEntry je = e.nextElement();
			if (je.getName().equals(xmlName)) {
				System.out.println("jar:"+file.getName());
				is=jf.getInputStream(je);
			}
		}
		jf.close();
		return is;
	}
}
