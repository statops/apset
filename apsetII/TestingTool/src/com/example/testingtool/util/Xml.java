package com.example.testingtool.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Xml {
	private static String TESTSUITES = "testsuites";
	private static String TESTSUITE = "testsuite";
	private static String TESTCASE = "testcase";
	private static String FAILURES = "failures";
	private static String FAILURE = "failure";
	private static String ERRORS = "errors";

	public static String create(String name, String verdict, String message,
			String testsuite) throws ParserConfigurationException,
			TransformerException {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		Document document = documentBuilder.newDocument();

		Element rootElement = document.createElement(TESTSUITES);
		document.appendChild(rootElement);

		Element testSuite = document.createElement(TESTSUITE);
		testSuite.setAttribute(ERRORS, "0");
		if (verdict.equals("fail")) {
			testSuite.setAttribute(FAILURES, "1");
		} else
			testSuite.setAttribute(FAILURES, "0");

		testSuite.setAttribute("time", "");
		testSuite.setAttribute("package", testsuite);
		testSuite.setAttribute("name", getclassname(name));
		rootElement.appendChild(testSuite);

		Element testCase = document.createElement(TESTCASE);
		testCase.setAttribute("time", "");
		testCase.setAttribute("classname", getclassname(name));
		testCase.setAttribute("name", getmethodeName(name));
		testSuite.appendChild(testCase);
		if (verdict.equals("fail")) {
			Element tverdict = document.createElement(FAILURE);
			tverdict.setAttribute("type", "SystemException");
			testCase.appendChild(tverdict);
			String status = "VULNERABLE" + "\n";
			tverdict.appendChild(document.createTextNode(status + message));
		}
		Element tsystem = document.createElement("system-out");
		rootElement.appendChild(tsystem);

		Element tsystemerr = document.createElement("systeme-err");
		rootElement.appendChild(tsystemerr);

		Element property = document.createElement("properties");
		rootElement.appendChild(property);

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();

		Properties outFormat = new Properties();

		outFormat.setProperty(OutputKeys.INDENT, "yes");
		outFormat.setProperty(OutputKeys.METHOD, "xml");
		outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		outFormat.setProperty(OutputKeys.VERSION, "1.0");
		outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");

		transformer.setOutputProperties(outFormat);

		DOMSource domSource = new DOMSource(document.getDocumentElement());
		OutputStream output = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(output);

		transformer.transform(domSource, result);

		String xmlString = output.toString();

		return xmlString;

	}

	private static String getmethodeName(String name) {
		int a = name.indexOf("#");
		try {
			return name.substring(a + 1, name.length() - 1);
		} catch (StringIndexOutOfBoundsException st) {
			return "test";
		}

	}

	private static String getclassname(String name) {
		int a = name.indexOf("#");
		try {
			return name.substring(0, a);
		} catch (StringIndexOutOfBoundsException st) {
			return name;
		}
	}
}
