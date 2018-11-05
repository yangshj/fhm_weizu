package com.weizu.common.xml;

import java.io.IOException;

import org.xml.sax.SAXException;


/**
 * xml解析接口(迭代时从根节点开始迭代，但不包含根节点)
 * @author scc
 *
 */
public interface IXMLParse{
	/**
	 * 获取根节点
	 * @return 根节点
	 * @throws SAXException 
	 * @throws IOException 
	 */
	IXMLNode getRootNode();
}
