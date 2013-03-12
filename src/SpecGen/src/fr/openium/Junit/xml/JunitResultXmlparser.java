package fr.openium.Junit.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.openium.results.JunitResultData;

/**
 * 
 * @author Stassia
 * 
 */
public class JunitResultXmlparser {

	/**
	 * Parses the Juni Xml Result, and returns an object containing the result
	 * of the parsing.
	 * 
	 * 
	 * @param JunitResultFile
	 *            the file to parse.
	 * 
	 */

	public static JunitResultData parse(File file) {
		SAXParserFactory fact = SAXParserFactory.newInstance();
		JunitResultData result = new JunitResultData();
		try {
			SAXParser parser = fact.newSAXParser();
			DefaultHandler handler = new JunitXmlHandler(file, result);
			parser.parse(file, handler);
			return result;
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

	public static class JunitXmlHandler extends DefaultHandler {

		private int mElementLevel = 0;
		private int mValidLevel = 0;

		private final static int LEVEL_TESTSUITES = 0;
		// inside testsuites: There are just testsuite
		private final static int LEVEL_TESTSUITES_TESTSUITE = 1;
		// inside testsuite: There are just testCASE
		private final static int LEVEL_TESTCASE = 2;
		// inside TESTCASE: There IS FAILURE
		private final static int LEVEL_FAILURE = 3;
		// inside FAILURE: There ARE MESSAGE
		private final static int LEVEL_MESSAGE = 4;

		private final JunitResultData mJunitResultData;

		public JunitXmlHandler(File stringXmlFile, JunitResultData result) {
			mJunitResultData = result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
		 */
		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
		 * java.lang.String, java.lang.String)
		 */
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
				case LEVEL_FAILURE:
					mJunitResultData.setMessage(buffer.toString());
					buffer = null;
					break;
				case LEVEL_TESTSUITES:
					break;
				case LEVEL_TESTCASE:
					break;
				case LEVEL_TESTSUITES_TESTSUITE:
					break;
				default:
					break;
				}

			}
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
		 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
		 */
		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
		 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */

		private static final String TESTCASE = "testcase";
		private static final String TESTSUITE = "testsuite";
		private static final String TESTSUITES = "testsuites";
		private static final String FAILURE = "failure";
		private static final String FAILURES = "failures";
		private static final String ERROR = "errors";
		private static final String NAME = "name";
		private static final String CLASNAME = "classname";
		private static final String TIME = "time";
		private static final String PACKAGE = "package";
		private static final String VULNERABLE = "VULNERABLE";
		private static final String NOT_VULNERABLE = "NOT_VULNERABLE";
		private static final String TIMESTAMP = "timestamp";

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
				switch (mValidLevel) {
				case LEVEL_TESTSUITES:
					if (qName.equals(TESTSUITES)) {
						mValidLevel++;
					}
					break;
				case LEVEL_TESTSUITES_TESTSUITE:
					if (qName.equals(TESTSUITE)) {
						mJunitResultData.setError(getAttributeValue(attributes,
								ERROR));
						mJunitResultData.setFailure(getAttributeValue(
								attributes, FAILURES));
						mJunitResultData.setTestName(getAttributeValue(
								attributes, NAME));
						mJunitResultData.setPackage(getAttributeValue(
								attributes, PACKAGE));
						mJunitResultData.setTime(getAttributeValue(attributes,
								TIME));
					}
					mValidLevel++;
					break;
				case LEVEL_TESTCASE:
					if (qName.equals(TESTCASE)) {
						mJunitResultData.setClassname(getAttributeValue(
								attributes, CLASNAME));
						mJunitResultData.setTime(getAttributeValue(attributes,
								TIME));
					}
					mValidLevel++;
					break;

				case LEVEL_FAILURE:
					buffer = new StringBuffer();
					/*
					 * if (qName.equals(FAILURE)) {
					 * mJunitResultData.setMessage(getAttributeValue(
					 * attributes, FAILURE));
					 * 
					 * }
					 */
					mValidLevel++;
					break;
				case LEVEL_MESSAGE:
					mValidLevel++;
					break;

				}

			}
			mElementLevel++;
		}

		private String getAttributeValue(Attributes attributes,
				String attributeName) {
			int count = attributes.getLength();
			for (int i = 0; i < count; i++) {
				if (attributeName.equals(attributes.getLocalName(i))
						&& attributes.getURI(i).length() == 0) {
					return attributes.getValue(i);
				}
			}

			return null;
		}

		public JunitResultData getJunitResultData() {
			return mJunitResultData;
		}
	}

}
