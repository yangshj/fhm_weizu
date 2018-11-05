package com.weizu.common.xml.impl;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import com.weizu.common.xml.IXMLDocument;
import com.weizu.common.xml.IXMLNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;



class XMLParse implements IXMLDocument {
	private Document doc;
	private final DocumentBuilder builder;

	public XMLParse(String url) throws ParserConfigurationException, FactoryConfigurationError {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		try{
			doc = builder.parse(url);//.newDocument();
		}catch(Exception e){
			//Log.getLog(getClass()).e(e);
		}
	}
	public XMLParse(InputStream inputStream) throws ParserConfigurationException, FactoryConfigurationError {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		try{
			doc = builder.parse(inputStream);
		}catch(Exception e){
			//Log.getLog(getClass()).e(e);
		}
	}
	public XMLParse(File file) throws ParserConfigurationException, FactoryConfigurationError {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		try{
			doc = builder.parse(file);
		}catch(Exception e){
			//Log.getLog(getClass()).e(e);
		}
	}
	public XMLParse(InputSource inputSource) throws ParserConfigurationException, FactoryConfigurationError {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		try{
			doc = builder.parse(inputSource);
		}catch(Exception e){
			//Log.getLog(getClass()).e(e);
		}
	}

	@Override
	public IXMLNode getRootNode() {
		Node root = doc.getChildNodes().item(0);
		return new XMLNode(doc, root);
	}
	//

	@Override
	public IXMLNode createRootNode(String nodeName) {
		Element root = doc.createElement(nodeName);
		doc.appendChild(root);
		return new XMLNode(doc, root);
	}
}
