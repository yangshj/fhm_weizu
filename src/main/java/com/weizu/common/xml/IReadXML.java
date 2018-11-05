package com.weizu.common.xml;

import java.io.IOException;
import java.util.List;

/**
 * 文件查找接口(先在当前项目找，然后去jar包里找，一旦找到，返回一个inpustream，再通过xml解析类，可获得节点对象)
 * @author student
 *
 */
public interface IReadXML {
	public List<IXMLParse> readXML(String xmlName) throws IOException;
	/**
	 * 获取配置文件解析对象集合（默认名称为mod.xml）
	 * @return
	 * @throws IOException 
	 */
	public List<IXMLParse> readModXML() throws IOException;
}
