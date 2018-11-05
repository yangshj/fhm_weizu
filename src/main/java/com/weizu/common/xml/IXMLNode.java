package com.weizu.common.xml;

import java.util.Iterator;
import java.util.List;

/**
 * 节点接口类
 * @author scc
 *
 */
public interface IXMLNode extends Iterator<IXMLNode>{
	/**
	 * 获取父节点
	 * @return 父节点，若无则返回null
	 */
	IXMLNode getParentNode();
	/**
	 * 获取所有子节点
	 * @return 子节点集合
	 */
	List<IXMLNode> getChilds();
	/**
	 * 获取指定名称的子节点
	 * @param nodeName
	 * @return
	 */
	List<IXMLNode> getChilds(String nodeName);
	/**
	 * 获取该节点属性值
	 * @return 该节点属性值
	 */
	String getText();
	/**
	 * 获取该节点属性
	 * @return 该节点属性
	 */
	String getAttribute(String attributeName);
	/**
	 * 获取该节点名称
	 * @return
	 */
	String getNodeName();
//	/**
//	 * xml格式输出(不包含父节点或者子节点)
//	 * @return
//	 */
//	String getThisXml();
	/**
	 * 获得从当前节点开始下级节点全文本
	 * @return
	 */
	String getXml();
	/**
	 * 添加子节点，并设置节点名
	 * @param nodeName
	 * @return
	 */
	IXMLNode addNode(String nodeName);
	/**
	 * 添加子节点，并设置属性
	 * @param name 属性名称
	 * @param value 属性值
	 * @return
	 */
	IXMLNode addAttribute(String name, String value);
	/**
	 * 设置内容
	 * @param text 内容
	 * @return
	 */
	void setText(String text);
}
