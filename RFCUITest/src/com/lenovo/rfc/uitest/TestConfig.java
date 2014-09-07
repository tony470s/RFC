package com.lenovo.rfc.uitest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestConfig {
	private static TestConfig instance;
	private InputStream inStream;
	private Element root;

	private TestConfig() {
		try {
			inStream = new FileInputStream(
					"/data/local/tmp/rfc/packages/test_config.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inStream);
			root = document.getDocumentElement();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getValue(String key) {
		try {
			NodeList personNode = root.getElementsByTagName(key);
			String value = personNode.item(0).getTextContent();
			inStream.close();
			return value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TestConfig instance() {
		if (instance == null) {
			instance = new TestConfig();
		}
		return instance;
	}

	public boolean isPhone() {
		return getValue("is_phone").equals("true");
	}
}
