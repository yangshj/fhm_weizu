package com.weizu.common.xml;

/**
 * XML文档类接口
 * @author scc
 *
 */
public interface IXMLDocument extends IXMLParse{
	/**
	 * 创建根节点
	 * @param nodeName
	 * @return
	 */
	IXMLNode createRootNode(String nodeName);
}
