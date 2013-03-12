package fr.openium.specification.specAdd;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SpecAddParser {

	public SpecAddParser() {

	}

	public static SpecAddData parse(File file) {
		SAXParserFactory fact = SAXParserFactory.newInstance();
		SpecAddData spec = new SpecAddData();
		try {
			SAXParser parser = fact.newSAXParser();
			DefaultHandler handler = new SpecAddHandler(file, spec);
			parser.parse(file, handler);
			return spec;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
