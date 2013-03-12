package fr.openium.Junit.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/***
 * 
 * @author Stassia
 * 
 * 
 *         parse the strings.xml file
 * 
 * 
 */
public class StringXmlParser {
	public StringXmlParser() {

	}

	public static ArrayList<String> parse(File file) {
		SAXParserFactory fact = SAXParserFactory.newInstance();
		ArrayList<String> st = new ArrayList<String>();
		try {
			SAXParser parser = fact.newSAXParser();
			DefaultHandler handler = new StringXmlHandler(file, st);
			parser.parse(file, handler);
			return st;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/** @author Stassia */

	public static class StringXmlHandler extends DefaultHandler {
		private final ArrayList<String> mStringXml;
		private int mElementLevel = 0;
		private int mValidLevel = 0;
		private final static int LEVEL_RESSOURCES = 0;
		private final static int LEVEL_INSIDE_RESSOURCES = 1;
		// inside RESSOURCES: There are just STRING

		private final static int LEVEL_INSIDE_STRING = 2;

		// inside comp: there are the intent and Content provider

		public StringXmlHandler(File stringXmlFile,
				ArrayList<String> stringXmlData) {
			mStringXml = stringXmlData;
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
		}

		private StringBuffer buffer;

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {

			if (mValidLevel == mElementLevel) {
				mValidLevel--;
			}
			mElementLevel--;

			if (mValidLevel == mElementLevel) {
				switch (mValidLevel) {
				case LEVEL_INSIDE_RESSOURCES:
					mStringXml.add(buffer.toString());
					buffer = null;
					break;
				case LEVEL_INSIDE_STRING:
					break;
				default:
					break;
				}

			}
		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
		}

		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String lecture = new String(ch, start, length);
			if (buffer != null)
				buffer.append(lecture);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
		 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (mValidLevel == mElementLevel) {
				String value;
				switch (mValidLevel) {
				case LEVEL_RESSOURCES:
					if (qName.equals("resources")) {
						mValidLevel++;
					}
					break;
				case LEVEL_INSIDE_RESSOURCES:
					buffer = new StringBuffer();
					mValidLevel++;
					break;
				case LEVEL_INSIDE_STRING:

					mValidLevel++;
					break;
				}

			}
			mElementLevel++;
		}

		public ArrayList<String> getStringXmlResult() {
			return mStringXml;
		}
	}
}
