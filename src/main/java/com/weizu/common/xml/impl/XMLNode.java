package com.weizu.common.xml.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.weizu.common.xml.IXMLNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class XMLNode implements IXMLNode {
	private IXMLNode parent;
	private List<IXMLNode> childs;
	private List<IXMLNode> allChilds;
//	private String name;
	private final Node node;
	private final Document doc;
	public XMLNode(Document doc, Node node) {
		this.doc = doc;
		this.node = node;
	}
//	public XMLNode() {
//		this.node = ;
//	}
	private int index=0;
	@Override
	public boolean hasNext() {
		initAllChilds();
		return index < allChilds.size();
	}

	@Override
	public IXMLNode next() {
		initAllChilds();
		return allChilds.get(index++);
	}

	private void initAllChilds() {
		if(null == allChilds){
			allChilds = new ArrayList<IXMLNode>();
			loadChilds(getChilds());
		}
	}

	private void loadChilds(List<IXMLNode> childs) {
		for(IXMLNode node : childs){
			allChilds.add(node);
			List<IXMLNode> cs = node.getChilds();
			if(!cs.isEmpty()){
				loadChilds(cs);
			}
		}
	}
	@Override
	public void remove() {
		
	}

	@Override
	public IXMLNode getParentNode() {
		if(null == parent){
			Node p = node.getParentNode();
			if(null != p) ;
			this.parent = new XMLNode(this.doc, p);
		}
		return parent;
	}

	@Override
	public List<IXMLNode> getChilds() {
		if(null == childs){
			childs = new ArrayList<IXMLNode>();
			NodeList list = node.getChildNodes();
			for(int i=0; i<list.getLength(); i++){
				childs.add(new XMLNode(this.doc, list.item(i)));
			}
		}
		return childs;
	}

	@Override
	public List<IXMLNode> getChilds(String nodeName) {
		List<IXMLNode> list = new ArrayList<IXMLNode>();
		for(IXMLNode node : getChilds()){
			if(node.getNodeName().equals(nodeName)){
				list.add(node);
			}
		}
		return list;
	}

	@Override
	public String getText() {
		return node.getTextContent();
	}

	@Override
	public String getAttribute(String attributeName) {
		Node namedItem = node.getAttributes().getNamedItem(attributeName);
		if(null == namedItem){
			return null;
		}
		return namedItem.getNodeValue();
	}

	@Override
	public String getNodeName() {
		return node.getNodeName();
	}

	@Override
	public String getXml() {
		StringWriter w = new StringWriter();
		try{
			TransformerFactory tFactory = TransformerFactory.newInstance();
	        Transformer transformer = tFactory.newTransformer();
	        //transformer.setOutputProperty(OutputKeys.ENCODING,"GB2312");
	        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
	        transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS,"yes");
	        //设置写入文件的格式
	        transformer.setOutputProperty(OutputKeys.METHOD,"xml");
	        DOMSource source = new DOMSource(node);
			StreamResult result = new StreamResult(w);
	//        transformer.transform(paramSource, paramResult);
	        transformer.transform(source, result);
		}catch(Exception e){
			e.printStackTrace();
			//Log.getLog(getClass()).e(e);
		}
        //
		return w.getBuffer().toString();
	}
	@Override
	public String toString() {
		return getXml();
	}

	@Override
	public IXMLNode addNode(String nodeName) {
		Element e = doc.createElement(nodeName);
		node.appendChild(e);
		return new XMLNode(doc, e);
	}

	@Override
	public IXMLNode addAttribute(String name, String value) {
		if(node instanceof Element){
			((Element) node).setAttribute(name, value);
		}
		return this;
	}

	@Override
	public void setText(String text) {
		node.setNodeValue(text);
	}
}
