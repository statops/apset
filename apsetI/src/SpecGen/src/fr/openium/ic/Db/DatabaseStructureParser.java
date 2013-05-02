package fr.openium.ic.Db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class DatabaseStructureParser {

	public DatabaseStructureParser() {

	}

	public static ArrayList<DatabaseStructure> parse(InputStream is) {
		SAXParserFactory fact = SAXParserFactory.newInstance();
		ArrayList<DatabaseStructure> st = new ArrayList<DatabaseStructure>();
		try {
			SAXParser parser = fact.newSAXParser();
			DefaultHandler handler = new StringXmlHandler(is, st);
			parser.parse(is, handler);
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

	public static class StringXmlHandler extends DefaultHandler {
		private final ArrayList<DatabaseStructure> mTable;
		private int mElementLevel = 0;
		private int mValidLevel = 0;
		private final static int LEVEL_DATABASE = 0;
		private final static int LEVEL_AUTH = 1;
		private final static int LEVEL_TABLE = 2;
		private final static int LEVEL_INSIDE_TABLE = 3;

		public StringXmlHandler(File stringXmlFile,
				ArrayList<DatabaseStructure> data) {
			mTable = data;
		}

		public StringXmlHandler(InputStream stringXmlFile,
				ArrayList<DatabaseStructure> data) {
			mTable = data;
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
				case LEVEL_AUTH:
					Authtemp = null;
					break;
				case LEVEL_TABLE:
					mTable.add(temp);
					temp = null;
					break;
				case LEVEL_INSIDE_TABLE:
					temp.setAuth(Authtemp);
					if (qName.equals("uri"))
						temp.setUri(buffer.toString());
					if (qName.equals("text"))
						temp.setCol(buffer.toString());
					buffer = null;
					break;
				default:
					break;
				}

			}
		}

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
		}

		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String lecture = new String(ch, start, length);
			if (buffer != null)
				buffer.append(lecture);
		}

		private DatabaseStructure temp;
		private String Authtemp;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (mValidLevel == mElementLevel) {
				switch (mValidLevel) {
				case LEVEL_DATABASE:
					if (qName.equals("provider")) {
						mValidLevel++;
					}
					break;
				case LEVEL_AUTH:
					if (qName.equals("authorities")) {
						Authtemp = attributes.getValue("name");
						mValidLevel++;
					}
					break;
				case LEVEL_TABLE:
					temp = new DatabaseStructure();
					mValidLevel++;
				case LEVEL_INSIDE_TABLE:
					// uri ou text
					buffer = new StringBuffer();
					mValidLevel++;
					break;
				default:
					break;
				}

			}
			mElementLevel++;
		}

	}
}
