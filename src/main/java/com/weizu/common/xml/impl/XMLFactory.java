package com.weizu.common.xml.impl;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import com.weizu.common.xml.IXMLDocument;
import org.xml.sax.InputSource;


public final class XMLFactory {
	private XMLFactory(){
	}
	public static IXMLDocument getByUrl(String url){
		try {
			return new XMLParse(url);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
		return null;
	}
	public static IXMLDocument getByInputStream(InputStream inputStream){
		try {
			return new XMLParse(inputStream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
		return null;
	}
	public static IXMLDocument getByText(String content){
		try {
			StringReader read = new StringReader(content);
			InputSource is = new InputSource(read);
			return new XMLParse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
		return null;
	}
}
